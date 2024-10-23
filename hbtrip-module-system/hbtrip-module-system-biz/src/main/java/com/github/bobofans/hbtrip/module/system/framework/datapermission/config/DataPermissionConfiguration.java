package com.github.bobofans.hbtrip.module.system.framework.datapermission.config;

import com.github.bobofans.hbtrip.module.system.dal.dataobject.dept.DeptDO;
import com.github.bobofans.hbtrip.module.system.dal.dataobject.user.AdminUserDO;
import com.github.bobofans.hbtrip.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的数据权限 Configuration
 *
 * @author 芋道源码
 */
@Configuration(proxyBeanMethods = false)
public class DataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer sysDeptDataPermissionRuleCustomizer() {
        return rule -> {
            // dept
            rule.addDeptColumn(AdminUserDO.class);
            rule.addDeptColumn(DeptDO.class, "id");
            // user
            rule.addUserColumn(AdminUserDO.class, "id");
        };
    }

}
