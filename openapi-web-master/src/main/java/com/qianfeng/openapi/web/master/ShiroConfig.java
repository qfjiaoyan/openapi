package com.qianfeng.openapi.web.master;

import com.qianfeng.openapi.web.master.dao.MenuDAO;
import com.qianfeng.openapi.web.master.shiro.MyShiroPermsFilter;
import com.qianfeng.openapi.web.master.shiro.MyShiroRoleFilter;
import com.qianfeng.openapi.web.master.shiro.MyShiroFilterFactoryBean;
import com.qianfeng.openapi.web.master.shiro.MyShiroRealm;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean
    public MyShiroRealm myShiroRealm() {//自定义realm
        return new MyShiroRealm();
    }


    @Bean
    public MemoryConstrainedCacheManager cacheManager() {//缓存，不要每次都去获取用户权限
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(MyShiroRealm myShiroRealm, MemoryConstrainedCacheManager cacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm);
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    @Bean
    public MyShiroFilterFactoryBean shiroFilter(MenuDAO menuDAO, SecurityManager securityManager) {
        MyShiroFilterFactoryBean factoryBean = new MyShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        factoryBean.setLoginUrl("/login.html");
        factoryBean.setSuccessUrl("/index.html");
        factoryBean.setUnauthorizedUrl("/error.html");
        factoryBean.setMenuDAO(menuDAO);
        factoryBean.setFilterChainDefinitions("/login.html = anon\n/dologin = anon\n/public/** = anon");
        Map<String, Filter> map = new HashMap<>();
        //角色过滤器，解决一个菜单必须同时拥有多个角色才能访问的问题，只要有其中一个角色就可以
        map.put("roles", new MyShiroRoleFilter());
        //权限过滤器，重写规则同角色过滤器
        map.put("perms", new MyShiroPermsFilter());

        factoryBean.setFilters(map);
        return factoryBean;
    }

    /**
     * 让权限注解生效
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
