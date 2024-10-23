package com.github.bobofans.hbtrip.module.ai.controller.admin.knowledge.vo.document;

import com.github.bobofans.hbtrip.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - AI 知识库文档的分页 Request VO")
@Data
public class AiKnowledgeDocumentPageReqVO extends PageParam {

    @Schema(description = "文档名称", example = "Java 开发手册")
    private String name;

}
