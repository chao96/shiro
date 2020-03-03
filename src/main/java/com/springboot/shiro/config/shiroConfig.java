package com.springboot.shiro.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 */
@Configuration
public class shiroConfig {
    /**
     * 创建ShiroFilterFactoryBean
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置shiro内置过滤器
        /**
         * shiro内置过滤器，可以实现权限相关的拦截器
         * 常用的过滤器：
         * anon：无需认证（登录）可以访问
         * authc：必须认证才可以登录访问
         * user：如果我们使用了rememberMe的功能可以直接访问
         * perms：必须得到资源权限才可以访问
         * role：必须得到角色权限才可以访问
         */
        Map<String,String> filterMap = new LinkedHashMap<String,String>();

        // 方法一：拦截指定页面认证
        /*
        filterMap.put("/user/add","authc");
        filterMap.put("/user/update","authc");
        */
        // 方法二：指定几个页面无需认证，其余全部拦截
        filterMap.put("/index","anon");
        filterMap.put("/user/login","anon");

        //添加授权过滤
        //注意：当授权拦截后，shiro会自动跳转到未授权页面
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");
        filterMap.put("/**","authc");

        //拦截后跳转到登录页面认证
        shiroFilterFactoryBean.setLoginUrl("/user/tologin");
        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/unAuth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建SecurityManager
     * 4.3、使用shiro内置过滤器实现页面拦截
     * 这是后比如说我们有两个用户的页面，一个添加，一个修改
     * 创建两个页面，进行测试
     * 创建test跳转这两个页面
     *
     * @param userRealm
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }
}