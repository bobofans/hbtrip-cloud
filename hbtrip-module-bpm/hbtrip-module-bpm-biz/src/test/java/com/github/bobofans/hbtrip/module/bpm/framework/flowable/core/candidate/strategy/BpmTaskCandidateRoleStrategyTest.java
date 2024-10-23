package com.github.bobofans.hbtrip.module.bpm.framework.flowable.core.candidate.strategy;

import com.github.bobofans.hbtrip.framework.test.core.ut.BaseMockitoUnitTest;
import com.github.bobofans.hbtrip.module.system.api.permission.PermissionApi;
import com.github.bobofans.hbtrip.module.system.api.permission.RoleApi;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Set;

import static com.github.bobofans.hbtrip.framework.common.pojo.CommonResult.success;
import static com.github.bobofans.hbtrip.framework.common.util.collection.SetUtils.asSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Disabled // TODO 芋艿：临时注释
public class BpmTaskCandidateRoleStrategyTest extends BaseMockitoUnitTest {

    @InjectMocks
    private BpmTaskCandidateRoleStrategy strategy;

    @Mock
    private RoleApi roleApi;
    @Mock
    private PermissionApi permissionApi;

    @Test
    public void testCalculateUsers() {
        // 准备参数
        String param = "1,2";
        // mock 方法
        when(permissionApi.getUserRoleIdListByRoleIds(eq(asSet(1L, 2L))))
            .thenReturn(success(asSet(11L, 22L)));

        // 调用
        Set<Long> results = strategy.calculateUsers(null, param);
        // 断言
        assertEquals(asSet(11L, 22L), results);
    }

}
