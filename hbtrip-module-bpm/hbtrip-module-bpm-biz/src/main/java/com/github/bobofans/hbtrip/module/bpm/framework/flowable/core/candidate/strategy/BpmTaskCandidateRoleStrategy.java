package com.github.bobofans.hbtrip.module.bpm.framework.flowable.core.candidate.strategy;

import com.github.bobofans.hbtrip.framework.common.util.string.StrUtils;
import com.github.bobofans.hbtrip.module.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import com.github.bobofans.hbtrip.module.bpm.framework.flowable.core.enums.BpmTaskCandidateStrategyEnum;
import com.github.bobofans.hbtrip.module.system.api.permission.PermissionApi;
import com.github.bobofans.hbtrip.module.system.api.permission.RoleApi;
import com.github.bobofans.hbtrip.module.system.api.user.AdminUserApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 角色 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @author kyle
 */
@Component
public class BpmTaskCandidateRoleStrategy extends BpmTaskCandidateAbstractStrategy {

    @Resource
    private RoleApi roleApi;
    @Resource
    private PermissionApi permissionApi;

    public BpmTaskCandidateRoleStrategy(AdminUserApi adminUserApi) {
        super(adminUserApi);
    }

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.ROLE;
    }

    @Override
    public void validateParam(String param) {
        Set<Long> roleIds = StrUtils.splitToLongSet(param);
        roleApi.validRoleList(roleIds);
    }

    @Override
    public Set<Long> calculateUsers(String param) {
        Set<Long> roleIds = StrUtils.splitToLongSet(param);
        return permissionApi.getUserRoleIdListByRoleIds(roleIds).getCheckedData();
    }

}