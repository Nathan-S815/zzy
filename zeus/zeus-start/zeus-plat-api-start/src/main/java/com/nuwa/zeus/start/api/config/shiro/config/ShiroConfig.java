package com.nuwa.zeus.start.api.config.shiro.config;



import com.nuwa.zeus.start.api.config.shiro.JWTFilter;
import com.nuwa.zeus.start.api.config.shiro.JWTShiroRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description  : Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
 * 既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 */

@Configuration
@Order(1)
public class ShiroConfig {
    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter() {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("token", new JWTFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/main");
        shiroFilterFactoryBean.setUnauthorizedUrl("/login");


        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        filterChainDefinitionMap.put("/static/**", "anon");
        //anon 可以理解为不拦截
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/isSingleUserName", "anon");
        filterChainDefinitionMap.put("/material/upload/image", "anon");
        filterChainDefinitionMap.put("/dictData/**", "anon");
        filterChainDefinitionMap.put("/plat/kaptcha","anon");
        filterChainDefinitionMap.put("/plat/login","anon");
        filterChainDefinitionMap.put("/login/byUserName","anon");
        filterChainDefinitionMap.put("/merchant/kaptcha","anon");
        filterChainDefinitionMap.put("/merchant/login","anon");
        filterChainDefinitionMap.put("/problem/excelProblemList","anon");

        filterChainDefinitionMap.put("/kaptcha/registCode", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/doc.html", "anon");

        filterChainDefinitionMap.put("/v2/api-docs","anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/ui","anon");
        filterChainDefinitionMap.put("/swagger-resources","anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/security","anon");
        filterChainDefinitionMap.put("/swagger-ui.html","anon");
        filterChainDefinitionMap.put("/webjars/**","anon");

        // 访问401和404页面不通过我们的Filter
        filterChainDefinitionMap.put("/401", "anon");
        filterChainDefinitionMap.put("/404", "anon");
        filterChainDefinitionMap.put("/**", "token");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer1() {
        return new WebMvcConfigurer() {
            @Override
            //重写父类提供的跨域请求处理的接口
            public void addCorsMappings(CorsRegistry registry) {
                //添加映射路径
                //添加映射路径
                registry.addMapping("/**")
                        //放行哪些原始域
                        .allowedOrigins("*", "Authorization")
                        //是否发送Cookie信息
                        .allowCredentials(true)
                        //放行哪些原始域(请求方式)
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        //放行哪些原始域(头部信息)
                        .allowedHeaders("*", "Authorization")
                        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                        .exposedHeaders("content-type", "application/json", "Authorization");
            }
        };
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myShiroRealm());
        //注入缓存管理器
       // securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     */
    @Bean
    public JWTShiroRealm myShiroRealm() {
        JWTShiroRealm JWTShiroRealm = new JWTShiroRealm();
        return JWTShiroRealm;
    }


   /* @Bean(name = "shiroAuthcRealm")
    public ShiroRealm myShiroRealm() {
        ShiroRealm realm = new ShiroRealm();
        realm.setCacheManager(ehCacheManager());
        return realm;
    }*/

    /**
     * 开启shiro aop注解支持. 使用代理方式; 所以需要开启代码支持;
     *
     * @param securityManager 安全管理器
     * @return 授权Advisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        System.out.println("开启了Shiro注解支持");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     *
     * @return
     */
  /*  @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }*/


    /**
     * shiro thymeleaf配置。
     *
     * @return
     */
 /*   @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }*/
}
