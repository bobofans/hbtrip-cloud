package com.github.bobofans.hbtrip.module.bpm.framework.flowable.core.candidate.strategy;

import cn.hutool.core.collection.CollUtil;
import com.github.bobofans.hbtrip.framework.common.enums.CommonStatusEnum;
import com.github.bobofans.hbtrip.module.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import com.github.bobofans.hbtrip.module.system.api.user.AdminUserApi;
import com.github.bobofans.hbtrip.module.system.api.user.dto.AdminUserRespDTO;

import java.util.Map;
import java.util.Set;

/**
 * {@link BpmTaskCandidateStrategy} 抽象类
 *
 * @author jason
 */
public abstract class BpmTaskCandidateAbstractStrategy implements BpmTaskCandidateStrategy {

    protected AdminUserApi adminUserApi;

    public BpmTaskCandidateAbstractStrategy(AdminUserApi adminUserApi) {
        this.adminUserApi = adminUserApi;
    }

    @Override
    public void removeDisableUsers(Set<Long> users) {
        if (CollUtil.isEmpty(users)) {
            return;
        }
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(users);
        users.removeIf(id -> {
            AdminUserRespDTO user = userMap.get(id);
            return user == null || !CommonStatusEnum.ENABLE.getStatus().equals(user.getStatus());
        });
    }

}