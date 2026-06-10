

package com.nuwa.generator.zeus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.nuwa.framework.database.PageQry;
import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.framework.database.supper.SuperService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.generator.zeus.config.GeneratorStrategy;
import com.nuwa.generator.zeus.constant.GeneratorConstant;
import com.nuwa.generator.zeus.properties.GeneratorProperties;
import com.nuwa.generator.zeus.util.CodeGeneratorKit;

import java.util.Collections;

/**
 * spring-boot-plus代码生成器入口类
 *
 * @author geekidea
 * @date 2019-10-22
 **/
@SuppressWarnings(value = "All")
public class StartGenerator {

    /**
     * 生成代码
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        generatorEntityAndMapper("app_apply", "id", "cn", "other");
        //generatorService("app_apply", "id", "cn_", "other");
       // generatorPageQryCO("app_apply", "id", "cn_", "other");
       // generatorPage("app_apply", "id", "cn_", "other");
    }

    private static void generatorPageQryCO(String tableName, String pkIdName, String tablePrefix, String moduleName) {
        GeneratorProperties generatorProperties = defaultGeneratorProperties();

        // 设置基本信息
        generatorProperties
                .setMavenModuleName("zeus-client")
                .setParentPackage("com.nuwa.client.zeus.clientobject")
                .setMavenModuleName("zeus-client")
                .setParentPackage("com.nuwa.client.zeus.dto.clientobject")
                .setModuleName(moduleName)
                .setAuthor("huyonghack@163.com")
                .setFileOverride(true);

        // 设置表信息
        generatorProperties.addTable(tableName, pkIdName);

        // 设置表前缀
        generatorProperties.setTablePrefix(Collections.singletonList(tablePrefix));

        // 生成配置
        generatorProperties.getGeneratorConfig()
                .setGeneratorPageQry(true)
                .setGeneratorQueryCo(false)
                .setPageListOrder(false)
                .setGeneratorEntity(false)
                .setActiveRecord(false)
                .setGeneratorMapper(false)
                .setGeneratorController(false)
                .setGeneratorService(false)
                .setGeneratorServiceImpl(false)
                .setGeneratorMapperXml(false)
                .setGeneratorPageParam(false)
                .setGeneratorQueryVo(false)
                .setRequiresPermissions(false)
                .setPageListOrder(false)
                .setParamValidation(false)
                .setSwaggerTags(false)
                .setOperationLog(false);

        // 全局配置
        generatorProperties.getMybatisPlusGeneratorConfig().getGlobalConfig()
                .setOpen(true)
                .setSwagger2(false)
                .setIdType(IdType.AUTO)
                .setDateType(DateType.ONLY_DATE);

        // 策略配置
        generatorProperties.getMybatisPlusGeneratorConfig().getStrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true)
                .setLogicDeleteFieldName(GeneratorConstant.DELETED);

        generatorProperties.getProjectConfig()
                .setSuperPageOrderParamClass(PageQry.class.getCanonicalName())
                .setPagingClass("PageQry")
                .setEntityPackage("com.nuwa.infrastructure.zeus.database." + moduleName + StringPool.DOT + GeneratorConstant.ENTITY);

        // 生成代码
        CodeGeneratorKit codeGenerator = new CodeGeneratorKit();
        codeGenerator.generator(generatorProperties);
    }

    private static void generatorPage(String tableName, String pkIdName, String tablePrefix, String moduleName) {
        GeneratorProperties generatorProperties = defaultGeneratorProperties();

        // 设置基本信息
        generatorProperties
                .setMavenModuleName("zeus-infrastructure")
                .setParentPackage("com.nuwa.infrastructure.zeus.database")
                .setPageQryPackage("com.nuwa.client.zeus.dto.clientobject")
                .setModuleName(moduleName)
                .setAuthor("huyonghack@163.com")
                .setFileOverride(true);

        // 设置表信息
        generatorProperties.addTable(tableName, pkIdName);

        // 设置表前缀
        generatorProperties.setTablePrefix(Collections.singletonList(tablePrefix));

        // 生成配置
        generatorProperties.getGeneratorConfig()
                .setGeneratorPageParam(true)
                .setGeneratorPageQry(false)
                .setGeneratorPageQry(false)
                .setGeneratorQueryCo(false)
                .setPageListOrder(false)
                .setGeneratorEntity(false)
                .setActiveRecord(false)
                .setGeneratorMapper(false)
                .setGeneratorController(false)
                .setGeneratorService(false)
                .setGeneratorServiceImpl(false)
                .setGeneratorMapperXml(false)
                .setGeneratorQueryVo(false)
                .setRequiresPermissions(false)
                .setPageListOrder(false)
                .setParamValidation(false)
                .setSwaggerTags(false)
                .setOperationLog(false);

        // 全局配置
        generatorProperties.getMybatisPlusGeneratorConfig().getGlobalConfig()
                .setOpen(true)
                .setSwagger2(false)
                .setIdType(IdType.AUTO)
                .setDateType(DateType.ONLY_DATE);

        // 策略配置
        generatorProperties.getMybatisPlusGeneratorConfig().getStrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true)
                .setLogicDeleteFieldName(GeneratorConstant.DELETED);

        generatorProperties.getProjectConfig()
                .setSuperPageOrderParamClass(PageQry.class.getCanonicalName())
                .setPagingClass("PageParam")
                .setEntityPackage("com.nuwa.infrastructure.zeus.database." + moduleName + StringPool.DOT + GeneratorConstant.ENTITY);

        // 生成代码
        CodeGeneratorKit codeGenerator = new CodeGeneratorKit();
        codeGenerator.generator(generatorProperties);
    }

    private static void generatoCreateCO(String tableName, String pkIdName, String tablePrefix, String moduleName) {
        GeneratorProperties generatorProperties = defaultGeneratorProperties();

        // 设置基本信息
        generatorProperties
                .setMavenModuleName("nuwa-cloud-application/zeus/thor-client")
                .setParentPackage("com.nuwa.client.zeus.clientobject")
                .setMavenModuleName("nuwa-cloud-application/zeus/thor-client")
                .setParentPackage("com.nuwa.client.zeus.dto.clientobject")
                .setModuleName(moduleName)
                .setAuthor("huyonghack@163.com")
                .setFileOverride(true);

        // 设置表信息
        generatorProperties.addTable(tableName, pkIdName);

        // 设置表前缀
        generatorProperties.setTablePrefix(Collections.singletonList(tablePrefix));

        // 生成配置
        generatorProperties.getGeneratorConfig()
                .setGeneratorQueryCo(true)
                .setGeneratorPageQry(false)
                .setPageListOrder(false)
                .setGeneratorEntity(false)
                .setActiveRecord(false)
                .setGeneratorMapper(false)
                .setGeneratorController(false)
                .setGeneratorService(false)
                .setGeneratorServiceImpl(false)
                .setGeneratorMapperXml(false)
                .setGeneratorPageParam(false)
                .setGeneratorQueryVo(false)
                .setRequiresPermissions(false)
                .setPageListOrder(false)
                .setParamValidation(false)
                .setSwaggerTags(false)
                .setOperationLog(false);

        // 全局配置
        generatorProperties.getMybatisPlusGeneratorConfig().getGlobalConfig()
                .setOpen(true)
                .setSwagger2(false)
                .setIdType(IdType.AUTO)
                .setDateType(DateType.ONLY_DATE);

        // 策略配置
        generatorProperties.getMybatisPlusGeneratorConfig().getStrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true)
                .setLogicDeleteFieldName(GeneratorConstant.DELETED);

        generatorProperties.getProjectConfig()
                .setSuperPageOrderParamClass(PageQry.class.getCanonicalName())
                .setPagingClass("PageQry")
                .setEntityPackage("com.nuwa.infrastructure.zeus.database." + moduleName + StringPool.DOT + GeneratorConstant.ENTITY);

        // 生成代码
        CodeGeneratorKit codeGenerator = new CodeGeneratorKit();
        codeGenerator.generator(generatorProperties);
    }

    private static void generatorQryCmdExe(String tableName, String pkIdName, String tablePrefix, String moduleName) {
        GeneratorProperties generatorProperties = defaultGeneratorProperties();

        // 设置基本信息
        generatorProperties
                .setMavenModuleName("nuwa-cloud-application/zeus/sample-app")
                .setParentPackage("com.nuwa.app.zeus.command.query")
                .setMavenModuleName("nuwa-cloud-application/zeus/zeus-app")
                .setParentPackage("com.nuwa.app.zeus.command.query")
                .setModuleName(moduleName)
                .setAuthor("huyonghack@163.com")
                .setFileOverride(true);

        // 设置表信息
        generatorProperties.addTable(tableName, pkIdName);

        // 设置表前缀
        generatorProperties.setTablePrefix(Collections.singletonList(tablePrefix));

        // 生成配置
        generatorProperties.getGeneratorConfig()
                .setGeneratorPageQry(true)
                .setPageListOrder(true)
                .setGeneratorEntity(false)
                .setActiveRecord(false)
                .setGeneratorMapper(false)
                .setGeneratorController(false)
                .setGeneratorService(false)
                .setGeneratorServiceImpl(false)
                .setGeneratorMapperXml(false)
                .setGeneratorPageParam(false)
                .setGeneratorQueryVo(false)
                .setRequiresPermissions(false)
                .setPageListOrder(false)
                .setParamValidation(false)
                .setSwaggerTags(false)
                .setOperationLog(false);

        // 全局配置
        generatorProperties.getMybatisPlusGeneratorConfig().getGlobalConfig()
                .setOpen(true)
                .setSwagger2(false)
                .setIdType(IdType.AUTO)
                .setDateType(DateType.ONLY_DATE);

        // 策略配置
        generatorProperties.getMybatisPlusGeneratorConfig().getStrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true)
                .setLogicDeleteFieldName(GeneratorConstant.DELETED);

        generatorProperties.getProjectConfig()
                .setSuperPageOrderParamClass(PageQry.class.getCanonicalName())
                .setPagingClass("PageQry")
                .setEntityPackage("com.nuwa.infrastructure.zeus.database." + moduleName + StringPool.DOT + GeneratorConstant.ENTITY);

        // 生成代码
        CodeGeneratorKit codeGenerator = new CodeGeneratorKit();
        codeGenerator.generator(generatorProperties);
    }

    private static void generatorEntityAndMapper(String tableName, String pkIdName, String tablePrefix, String moduleName) {
        GeneratorProperties generatorProperties = defaultGeneratorProperties();

        // 设置基本信息
        generatorProperties
                .setMavenModuleName("zeus-infrastructure")
                .setParentPackage("com.nuwa.infrastructure.zeus.database")
                .setModuleName(moduleName)
                .setAuthor("huyonghack@163.com")
                .setFileOverride(true);

        // 设置表信息
        generatorProperties.addTable(tableName, pkIdName);

        // 设置表前缀
        generatorProperties.setTablePrefix(Collections.singletonList(tablePrefix));

        // 生成配置
        generatorProperties.getGeneratorConfig()
                .setGeneratorStrategy(GeneratorStrategy.SIMPLE)
                .setGeneratorEntity(true)
                .setActiveRecord(true)
                .setGeneratorMapper(true)
                .setGeneratorMapperXml(true)
                .setGeneratorController(false)
                .setGeneratorService(false)
                .setGeneratorServiceImpl(false)
                .setGeneratorPageParam(false)
                .setGeneratorQueryVo(false)
                .setRequiresPermissions(false)
                .setPageListOrder(false)
                .setParamValidation(false)
                .setSwaggerTags(true)
                .setOperationLog(false);

        // 全局配置
        generatorProperties.getMybatisPlusGeneratorConfig().getGlobalConfig()
                .setOpen(true)
                .setSwagger2(false)
                .setIdType(IdType.AUTO)
                .setDateType(DateType.ONLY_DATE);

        // 策略配置
        generatorProperties.getMybatisPlusGeneratorConfig().getStrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true)
                .setVersionFieldName(GeneratorConstant.VERSION)
                //.setSuperEntityClass(SuperEntity.class) //基类
                .setSuperMapperClass(SuperMapper.class.getCanonicalName())
                .setLogicDeleteFieldName(GeneratorConstant.DELETED);

        // 生成代码
        CodeGeneratorKit codeGenerator = new CodeGeneratorKit();
        codeGenerator.generator(generatorProperties);
    }


    private static void generatorService(String tableName, String pkIdName, String tablePrefix, String moduleName) {
        GeneratorProperties generatorProperties = defaultGeneratorProperties();

        // 设置基本信息
        generatorProperties
                .setMavenModuleName("zeus-infrastructure")
                .setParentPackage("com.nuwa.infrastructure.zeus.database")
                .setModuleName(moduleName)
                .setAuthor("huyonghack@163.com")
                .setFileOverride(true);

        // 设置表信息
        generatorProperties.addTable(tableName, pkIdName);

        // 设置表前缀
        generatorProperties.setTablePrefix(Collections.singletonList(tablePrefix));

        // 生成配置
        generatorProperties.getGeneratorConfig()
                .setGeneratorStrategy(GeneratorStrategy.SIMPLE)
                .setGeneratorService(true)
                .setGeneratorServiceImpl(true)
                .setGeneratorEntity(false)
                .setActiveRecord(false)
                .setGeneratorMapper(false)
                .setGeneratorMapperXml(false)
                .setGeneratorController(false)
                .setGeneratorPageParam(false)
                .setGeneratorQueryVo(false)
                .setRequiresPermissions(false)
                .setPageListOrder(false)
                .setParamValidation(false)
                .setSwaggerTags(false)
                .setOperationLog(false);

        // 全局配置
        generatorProperties.getMybatisPlusGeneratorConfig().getGlobalConfig()
                .setOpen(true)
                .setSwagger2(false)
                .setIdType(IdType.AUTO)
                .setDateType(DateType.ONLY_DATE);

        // 策略配置
        generatorProperties.getMybatisPlusGeneratorConfig().getStrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true)
                .setVersionFieldName(GeneratorConstant.VERSION)
                //.setSuperEntityClass(SuperEntity.class) //基类
                .setSuperMapperClass(SuperMapper.class.getCanonicalName())
                .setSuperServiceClass(SuperService.class.getCanonicalName())
                .setSuperServiceImplClass(SuperServiceImpl.class.getCanonicalName())
                .setLogicDeleteFieldName(GeneratorConstant.DELETED);

        // 生成代码
        CodeGeneratorKit codeGenerator = new CodeGeneratorKit();
        codeGenerator.generator(generatorProperties);
    }


    private static GeneratorProperties defaultGeneratorProperties() {
        GeneratorProperties generatorProperties = new GeneratorProperties();
        // 数据源配置
        generatorProperties.getDataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setUsername("zzysaas")
                .setPassword("<REDACTED>")
                .setDriverName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://<HOST>/<DATABASE>?<PARAMS>");
        return generatorProperties;
    }
}
