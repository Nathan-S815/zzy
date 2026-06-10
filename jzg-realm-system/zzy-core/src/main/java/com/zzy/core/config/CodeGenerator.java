package com.zzy.core.config;


import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.dialect.PropsUtil;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;


public class CodeGenerator {

    private static String url;
    private static String user;
    private static String pwd;
    private static String driver;
    private static String parentPackagePath;
    private static String entityPath;
    private static String daoPath;
    private static String servicePath;
    private static String serviceImplPath;
    private static String controllerPath;
    private static String tableNames;
    private static String xmlPath;
    private static String controllerProjectPath;
    private static String serviceProjectPath;
    private static String daoProjectPath;




    public static void prop() throws IOException {

        Props properties = PropsUtil.get("jdbc.properties");
//        Properties properties = new Properties();
//        InputStream inputStream = CodeGenerator.class.getResourceAsStream("/jdbc.properties");
        properties.load();
        url = properties.get("db.url").toString();
        user = properties.get("db.username").toString();
        pwd = properties.get("db.password").toString();
        driver = properties.get("db.driverClassName").toString();
        parentPackagePath = properties.get("parent.package.path").toString();
        entityPath = properties.get("entity.package.path").toString();
        daoPath = properties.get("dao.package.path").toString();
        servicePath = properties.get("service.package.path").toString();
        serviceImplPath = properties.get("serviceImpl.package.path").toString();
        controllerPath = properties.get("controller.package.path").toString();
        tableNames = properties.get("table.name").toString();
        xmlPath = properties.get("xml.path").toString();
        controllerProjectPath = properties.get("controller.project.path").toString();
        serviceProjectPath = properties.get("service.project.path").toString();
        daoProjectPath = properties.get("dao.project.path").toString();
    }


    public static void main(String[] args) throws IOException {
        prop();
        CodeGenerator cg = new CodeGenerator();
        cg.init();
    }

    public void init(){
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        System.out.println(tableNames);
        String[] tablesName = tableNames.replace(" ", "").split(",");
        genDaoEntity(tablesName,projectPath+"\\"+daoProjectPath); //只生成entityDaoXml
        genService(tablesName,projectPath+"\\"+serviceProjectPath); //只生成service&Impl
//        genController(tablesName,projectPath+"\\"+controllerProjectPath); //只生成controller
    }






    public void genService(String[] tables, String projectPath){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("zzy");
        gc.setOpen(false);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName(null);
        gc.setXmlName(null);
        gc.setControllerName(null);
//        gc.setServiceImplName(null);
//        gc.setServiceName(null);
        gc.setFileOverride(true);
//        gc.setSwagger2(true); // 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(driver);
        dsc.setUsername(user);
        dsc.setPassword(pwd);
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parentPackagePath).setService(servicePath).setServiceImpl(serviceImplPath);
        pc.setEntity(entityPath).setMapper(daoPath);
        mpg.setPackageInfo(pc);
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController(null);
        templateConfig.setXml(null);
        templateConfig.setEntity(null);
        templateConfig.setMapper(null);
        mpg.setTemplate(templateConfig);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(tables);
//        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    public void genController(String[] tables,String projectPath){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("zzy");
        gc.setOpen(false);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName(null);
        gc.setXmlName(null);
        gc.setControllerName(null);
//        gc.setServiceImplName(null);
//        gc.setServiceName(null);
        gc.setFileOverride(true);
//        gc.setSwagger2(true); // 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(driver);
        dsc.setUsername(user);
        dsc.setPassword(pwd);
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parentPackagePath).setController(controllerPath);
        mpg.setPackageInfo(pc);
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        templateConfig.setEntity(null);
        templateConfig.setMapper(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        mpg.setTemplate(templateConfig);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(tables);
//        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    public void genDaoEntity(String[] tables,String projectPath){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("zzy");
        gc.setOpen(false);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setControllerName(null);
        gc.setServiceImplName(null);
        gc.setServiceName(null);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
//        gc.setSwagger2(true); // 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(driver);
        dsc.setUsername(user);
        dsc.setPassword(pwd);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parentPackagePath);
        pc.setEntity(entityPath).setMapper(daoPath);
        mpg.setPackageInfo(pc);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/"+xmlPath+"/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setService(null);
        templateConfig.setController(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(tables);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }



}

