package com.github.bobofans.hbtrip.module.system.controller.admin.sms;

import com.github.bobofans.hbtrip.framework.apilog.core.annotation.ApiAccessLog;
import com.github.bobofans.hbtrip.framework.common.pojo.CommonResult;
import com.github.bobofans.hbtrip.framework.common.pojo.PageParam;
import com.github.bobofans.hbtrip.framework.common.pojo.PageResult;
import com.github.bobofans.hbtrip.framework.common.util.object.BeanUtils;
import com.github.bobofans.hbtrip.framework.excel.core.util.ExcelUtils;
import com.github.bobofans.hbtrip.module.system.controller.admin.sms.vo.log.SmsLogPageReqVO;
import com.github.bobofans.hbtrip.module.system.controller.admin.sms.vo.log.SmsLogRespVO;
import com.github.bobofans.hbtrip.module.system.dal.dataobject.sms.SmsLogDO;
import com.github.bobofans.hbtrip.module.system.service.sms.SmsLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static com.github.bobofans.hbtrip.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.github.bobofans.hbtrip.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 短信日志")
@RestController
@RequestMapping("/system/sms-log")
@Validated
public class SmsLogController {

    @Resource
    private SmsLogService smsLogService;

    @GetMapping("/page")
    @Operation(summary = "获得短信日志分页")
    @PreAuthorize("@ss.hasPermission('system:sms-log:query')")
    public CommonResult<PageResult<SmsLogRespVO>> getSmsLogPage(@Valid SmsLogPageReqVO pageReqVO) {
        PageResult<SmsLogDO> pageResult = smsLogService.getSmsLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SmsLogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出短信日志 Excel")
    @PreAuthorize("@ss.hasPermission('system:sms-log:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSmsLogExcel(@Valid SmsLogPageReqVO exportReqVO,
                                  HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SmsLogDO> list = smsLogService.getSmsLogPage(exportReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "短信日志.xls", "数据", SmsLogRespVO.class,
                BeanUtils.toBean(list, SmsLogRespVO.class));
    }

}
