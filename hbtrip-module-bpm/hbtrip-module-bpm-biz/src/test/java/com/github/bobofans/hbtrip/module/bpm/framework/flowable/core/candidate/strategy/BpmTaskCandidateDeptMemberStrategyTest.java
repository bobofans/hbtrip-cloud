package com.github.bobofans.hbtrip.module.bpm.framework.flowable.core.candidate.strategy;

import com.github.bobofans.hbtrip.framework.test.core.ut.BaseMockitoUnitTest;
import com.github.bobofans.hbtrip.module.system.api.user.AdminUserApi;
import com.github.bobofans.hbtrip.module.system.api.user.dto.AdminUserRespDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Set;

import static com.github.bobofans.hbtrip.framework.common.pojo.CommonResult.success;
import static com.github.bobofans.hbtrip.framework.common.util.collection.CollectionUtils.convertList;
import static com.github.bobofans.hbtrip.framework.common.util.collection.SetUtils.asSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Disabled // TODO 芋艿：临时注释
public class BpmTaskCandidateDeptMemberStrategyTest extends BaseMockitoUnitTest {

    @InjectMocks
    private BpmTaskCandidateDeptMemberStrategy strategy;

    @Mock
    private AdminUserApi adminUserApi;

    @Test
    public void testCalculateUsers() {
        // 准备参数
        String param = "11,22";
        // mock 方法
        List<AdminUserRespDTO> users = convertList(asSet(11L, 22L),
                id -> new AdminUserRespDTO().setId(id));
        when(adminUserApi.getUserListByDeptIds(eq(asSet(11L, 22L)))).thenReturn(success(users));

        // 调用
        Set<Long> results = strategy.calculateUsers(null, param);
        // 断言
        assertEquals(asSet(11L, 22L), results);
    }

}
