package com.nuwa.hotel.start.api.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * mybatis配置类
 * 
 * @author hy
 * @date 2020/10/13 19:38
 * @since 1.0.0
 */
@Configuration
@MapperScan("com.nuwa.infrastructure.hotel.database.*.mapper")
public class MybatisPlusConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(ResourceLoader resourceLoader, @Autowired DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath*:/mapper/**/*.xml"));

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactory.setConfiguration(configuration);

        sqlSessionFactory.setPlugins(
                //paginationInterceptor,
                // new PerformanceInterceptor (),
                new PaginationInterceptor());

        return sqlSessionFactory.getObject();
    }

    //事务管理
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
    /*PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    return paginationInterceptor;*/

        //单表分页插件
        return new PaginationInterceptor();
    }

    //逻辑删除
    @Bean
    public ISqlInjector iSqlInjector(){
        return new DefaultSqlInjector();
    }

}
