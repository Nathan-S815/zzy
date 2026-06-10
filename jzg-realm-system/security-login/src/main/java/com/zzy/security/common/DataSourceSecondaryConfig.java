package com.zzy.security.common;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.mysql.cj.jdbc.MysqlXADataSource;
import com.zaxxer.hikari.HikariDataSource;
import io.swagger.models.auth.In;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:pool-security.properties")
//@PropertySource("classpath:pool-security-prod.properties")
@MapperScan(basePackages = "com.zzy.security.lib.dao", sqlSessionTemplateRef  = "secondarySqlSessionTemplate")
public class DataSourceSecondaryConfig {





    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource(Environment env) throws SQLException {
        DataSource dx = getFromDruidDs(env);
        return dx;
    }

    private DruidDataSource getFromDruidDs(Environment env) {
        DruidDataSource ds = new DruidDataSource();
        ds.setPassword(env.getProperty("spring.datasource.secondary.password"));
        ds.setUsername(env.getProperty("spring.datasource.secondary.username"));
        ds.setUrl(env.getProperty("spring.datasource.secondary.url"));
        ds.setDriverClassName(env.getProperty("spring.datasource.secondary.driverClassName"));
        ds.setMaxActive(Integer.parseInt(env.getProperty("spring.datasource.secondary.maxActive")));
        ds.setInitialSize(Integer.parseInt(env.getProperty("spring.datasource.secondary.initialSize")));
        ds.setMinIdle(Integer.parseInt(env.getProperty("spring.datasource.secondary.minIdle")));
        ds.setMaxWait(Integer.parseInt(env.getProperty("spring.datasource.secondary.maxWait")));
        ds.setTimeBetweenEvictionRunsMillis(Integer.parseInt(env.getProperty("spring.datasource.secondary.timeBetweenEvictionRunsMillis")));
        ds.setMinEvictableIdleTimeMillis(Integer.parseInt(env.getProperty("spring.datasource.secondary.minEvictableIdleTimeMillis")));
        ds.setValidationQuery(env.getProperty("spring.datasource.secondary.validationQuery"));
        ds.setTestWhileIdle(true);
        ds.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("spring.datasource.secondary.testOnBorrow")));
        ds.setTestOnReturn(Boolean.parseBoolean(env.getProperty("spring.datasource.secondary.testOnReturn")));
        ds.setDefaultAutoCommit(Boolean.parseBoolean(env.getProperty("spring.datasource.secondary.defaultAutoCommit")));
        ds.setPoolPreparedStatements(Boolean.parseBoolean(env.getProperty("spring.datasource.secondary.poolPreparedStatements")));
        ds.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(env.getProperty("spring.datasource.secondary.maxPoolPreparedStatementPerConnectionSize")));
        ds.setUseGlobalDataSourceStat(Boolean.parseBoolean(env.getProperty("spring.datasource.secondary.useGlobalDataSourceStat")));
        ds.setRemoveAbandoned(Boolean.parseBoolean(env.getProperty("spring.datasource.secondary.removeAbandoned")));
        ds.setRemoveAbandonedTimeout(Integer.parseInt(env.getProperty("spring.datasource.secondary.removeAbandonedTimeout")));
        return ds;
    }



    @Bean("secondarySqlSessionFacotry")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("secondaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath*:mapper/**/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean("secondaryTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("secondaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean("secondarySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("secondarySqlSessionFacotry") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    @Bean("secondaryConnection")
    public Connection connection(@Qualifier("secondaryDataSource") DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }


}
