package com.nuwa.discovery.start.api.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.nuwa.framework.base.UserAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfiguration {

    private static final String splitter = ";";

    private static final String[] controllerPath = {"com.nuwa.discovery.start.api.controller"};


    /**
     * 跨域勿动
     *
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            //重写父类提供的跨域请求处理的接口
            public void addCorsMappings(CorsRegistry registry) {
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
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(apiInfo())
                .ignoredParameterTypes(UserAware.class)
                .select()
                .apis(basePackage("com.nuwa.discovery.start.api.controller"))
                .paths(PathSelectors.any())
                .build().groupName("接口")
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
        return docket;
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("Spring Boot Swagger2 API document")
                //版本号
                .version("1.0")
                //描述
                .description("API接口Header里需要有Authorization")
                .build();
    }


    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            for (String strPackage : basePackage.split(splitter)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }


}
