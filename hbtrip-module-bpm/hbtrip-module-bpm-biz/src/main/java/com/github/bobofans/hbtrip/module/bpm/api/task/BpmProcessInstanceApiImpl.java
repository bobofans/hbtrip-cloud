package com.github.bobofans.hbtrip.module.bpm.api.task;

import com.github.bobofans.hbtrip.framework.common.pojo.CommonResult;
import com.github.bobofans.hbtrip.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import com.github.bobofans.hbtrip.module.bpm.service.task.BpmProcessInstanceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import static com.github.bobofans.hbtrip.framework.common.pojo.CommonResult.success;

/**
 * Flowable 流程实例 Api 实现类
 *
 * @author 芋道源码
 * @author jason
 */
@RestController
@Validated
public class BpmProcessInstanceApiImpl implements BpmProcessInstanceApi {

    @Resource
    private BpmProcessInstanceService processInstanceService;

    @Override
    public CommonResult<String> createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqDTO reqDTO) {
        return success(processInstanceService.createProcessInstance(userId, reqDTO));
    }

}
