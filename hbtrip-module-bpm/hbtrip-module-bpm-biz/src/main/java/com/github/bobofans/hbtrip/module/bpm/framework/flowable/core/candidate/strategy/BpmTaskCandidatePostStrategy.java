package com.github.bobofans.hbtrip.module.bpm.framework.flowable.core.candidate.strategy;

import com.github.bobofans.hbtrip.framework.common.util.string.StrUtils;
import com.github.bobofans.hbtrip.module.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import com.github.bobofans.hbtrip.module.bpm.framework.flowable.core.enums.BpmTaskCandidateStrategyEnum;
import com.github.bobofans.hbtrip.module.system.api.dept.PostApi;
import com.github.bobofans.hbtrip.module.system.api.user.AdminUserApi;
import com.github.bobofans.hbtrip.module.system.api.user.dto.AdminUserRespDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static com.github.bobofans.hbtrip.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * 岗位 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @author kyle
 */
@Component
public class BpmTaskCandidatePostStrategy extends BpmTaskCandidateAbstractStrategy {

    private final PostApi postApi;

    public BpmTaskCandidatePostStrategy(AdminUserApi adminUserApi, PostApi postApi) {
        super(adminUserApi);
        this.postApi = postApi;
    }

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.POST;
    }

    @Override
    public void validateParam(String param) {
        Set<Long> postIds = StrUtils.splitToLongSet(param);
        postApi.validPostList(postIds);
    }

    @Override
    public Set<Long> calculateUsers(String param) {
        Set<Long> postIds = StrUtils.splitToLongSet(param);
        List<AdminUserRespDTO> users = adminUserApi.getUserListByPostIds(postIds).getCheckedData();
        return convertSet(users, AdminUserRespDTO::getId);
    }

}