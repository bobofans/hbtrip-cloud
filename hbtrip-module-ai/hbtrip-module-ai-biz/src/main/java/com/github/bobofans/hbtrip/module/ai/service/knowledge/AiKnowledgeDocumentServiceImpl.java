package com.github.bobofans.hbtrip.module.ai.service.knowledge;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpUtil;
import com.github.bobofans.hbtrip.framework.common.enums.CommonStatusEnum;
import com.github.bobofans.hbtrip.framework.common.pojo.PageResult;
import com.github.bobofans.hbtrip.framework.common.util.collection.CollectionUtils;
import com.github.bobofans.hbtrip.framework.common.util.object.BeanUtils;
import com.github.bobofans.hbtrip.module.ai.controller.admin.knowledge.vo.document.AiKnowledgeDocumentPageReqVO;
import com.github.bobofans.hbtrip.module.ai.controller.admin.knowledge.vo.document.AiKnowledgeDocumentUpdateReqVO;
import com.github.bobofans.hbtrip.module.ai.controller.admin.knowledge.vo.knowledge.AiKnowledgeDocumentCreateReqVO;
import com.github.bobofans.hbtrip.module.ai.dal.dataobject.knowledge.AiKnowledgeDocumentDO;
import com.github.bobofans.hbtrip.module.ai.dal.dataobject.knowledge.AiKnowledgeSegmentDO;
import com.github.bobofans.hbtrip.module.ai.dal.mysql.knowledge.AiKnowledgeDocumentMapper;
import com.github.bobofans.hbtrip.module.ai.dal.mysql.knowledge.AiKnowledgeSegmentMapper;
import com.github.bobofans.hbtrip.module.ai.enums.knowledge.AiKnowledgeDocumentStatusEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.tokenizer.TokenCountEstimator;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.github.bobofans.hbtrip.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.github.bobofans.hbtrip.module.ai.enums.ErrorCodeConstants.KNOWLEDGE_DOCUMENT_NOT_EXISTS;

/**
 * AI 知识库文档 Service 实现类
 *
 * @author xiaoxin
 */
@Service
@Slf4j
public class AiKnowledgeDocumentServiceImpl implements AiKnowledgeDocumentService {

    @Resource
    private AiKnowledgeDocumentMapper documentMapper;
    @Resource
    private AiKnowledgeSegmentMapper segmentMapper;

    @Resource
    private TokenCountEstimator tokenCountEstimator;
    @Resource
    private AiKnowledgeService knowledgeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createKnowledgeDocument(AiKnowledgeDocumentCreateReqVO createReqVO) {
        // 0. 校验并获取向量存储实例
        VectorStore vectorStore = knowledgeService.getVectorStoreById(createReqVO.getKnowledgeId());

        // 1.1 下载文档
        TikaDocumentReader loader = new TikaDocumentReader(downloadFile(createReqVO.getUrl()));
        List<Document> documents = loader.get();
        Document document = CollUtil.getFirst(documents);
        // 1.2 文档记录入库
        String content = document.getContent();
        AiKnowledgeDocumentDO documentDO = BeanUtils.toBean(createReqVO, AiKnowledgeDocumentDO.class)
                .setTokens(tokenCountEstimator.estimate(content)).setWordCount(content.length())
                .setStatus(CommonStatusEnum.ENABLE.getStatus()).setSliceStatus(AiKnowledgeDocumentStatusEnum.SUCCESS.getStatus());
        documentMapper.insert(documentDO);
        Long documentId = documentDO.getId();
        if (CollUtil.isEmpty(documents)) {
            return documentId;
        }

        // 2 构造文本分段器
        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter(createReqVO.getDefaultSegmentTokens(), createReqVO.getMinSegmentWordCount(), createReqVO.getMinChunkLengthToEmbed(),
                createReqVO.getMaxNumSegments(), createReqVO.getKeepSeparator());
        // 2.1 文档分段
        List<Document> segments = tokenTextSplitter.apply(documents);
        // 2.2 分段内容入库
        List<AiKnowledgeSegmentDO> segmentDOList = CollectionUtils.convertList(segments,
                segment -> new AiKnowledgeSegmentDO().setContent(segment.getContent()).setDocumentId(documentId)
                        .setKnowledgeId(createReqVO.getKnowledgeId()).setVectorId(segment.getId())
                        .setTokens(tokenCountEstimator.estimate(segment.getContent())).setWordCount(segment.getContent().length())
                        .setStatus(CommonStatusEnum.ENABLE.getStatus()));
        segmentMapper.insertBatch(segmentDOList);

        // 3. 向量化并存储
        segments.forEach(segment -> segment.getMetadata().put(AiKnowledgeSegmentDO.FIELD_KNOWLEDGE_ID, createReqVO.getKnowledgeId()));
        vectorStore.add(segments);
        return documentId;
    }

    @Override
    public PageResult<AiKnowledgeDocumentDO> getKnowledgeDocumentPage(AiKnowledgeDocumentPageReqVO pageReqVO) {
        return documentMapper.selectPage(pageReqVO);
    }

    @Override
    public void updateKnowledgeDocument(AiKnowledgeDocumentUpdateReqVO reqVO) {
        // 1. 校验文档是否存在
        validateKnowledgeDocumentExists(reqVO.getId());
        // 2. 更新文档
        AiKnowledgeDocumentDO document = BeanUtils.toBean(reqVO, AiKnowledgeDocumentDO.class);
        documentMapper.updateById(document);
    }

    /**
     * 校验文档是否存在
     *
     * @param id 文档编号
     * @return 文档信息
     */
    private AiKnowledgeDocumentDO validateKnowledgeDocumentExists(Long id) {
        AiKnowledgeDocumentDO knowledgeDocument = documentMapper.selectById(id);
        if (knowledgeDocument == null) {
            throw exception(KNOWLEDGE_DOCUMENT_NOT_EXISTS);
        }
        return knowledgeDocument;
    }

    private org.springframework.core.io.Resource downloadFile(String url) {
        try {
            byte[] bytes = HttpUtil.downloadBytes(url);
            return new ByteArrayResource(bytes);
        } catch (Exception e) {
            log.error("[downloadFile][url({}) 下载失败]", url, e);
            throw new RuntimeException(e);
        }
    }

}