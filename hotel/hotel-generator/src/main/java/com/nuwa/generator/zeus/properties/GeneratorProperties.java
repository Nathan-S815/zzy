

package com.nuwa.generator.zeus.properties;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.nuwa.generator.zeus.config.GeneratorConfig;
import com.nuwa.generator.zeus.config.MybatisPlusGeneratorConfig;
import com.nuwa.generator.zeus.config.ProjectConfig;
import com.nuwa.generator.zeus.config.TableConfig;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * spring-boot-plus代码生成配置属性
 *
 * @author geekidea
 * @date 2020/3/11
 **/
@Data
@Accessors(chain = true)
public class GeneratorProperties {

    /**
     * Maven模块名称
     */
    private String mavenModuleName;

    /**
     * 业务模块名称
     */
    private String moduleName;

    /**
     * 生成的父包全路径名称
     */
    private String parentPackage;

    /**
     * pageQry包路径
     */
    private String pageQryPackage = "";


    /**
     * 生成的类文件路径
     */
    private String parentPackagePath;

    /**
     * 开发人员名称
     */
    private String author;

    /**
     * 生成文件的输出目录,root: 表示当前项目根目录
     */
    private String outputDir;

    /**
     * 是否覆盖已有文件
     */
    private boolean fileOverride = false;

    /**
     * 表前缀
     */
    private List<String> tablePrefix;

    /**
     * 表信息配置
     */
    private List<TableConfig> tableConfig = new ArrayList<>();

    /**
     * 数据库连接信息配置
     */
    public DataSourceConfig dataSourceConfig = new DataSourceConfig();

    /**
     * 代码生成配置
     */
    private GeneratorConfig generatorConfig = new GeneratorConfig();

    /**
     * mybatisplus相关配置
     */
    private MybatisPlusGeneratorConfig mybatisPlusGeneratorConfig = new MybatisPlusGeneratorConfig();

    /**
     * 项目信息配置
     */
    private ProjectConfig projectConfig = new ProjectConfig();


    public GeneratorProperties addTable(String tableName) {
        this.addTable(tableName, null);
        return this;
    }

    public GeneratorProperties addTable(String tableName, String pkIdName) {
        this.getTableConfig().add(new TableConfig().setTableName(tableName).setPkIdName(pkIdName));
        return this;
    }

    public GeneratorProperties setTables(String... tableNames) {
        if (tableNames == null) {
            return this;
        }
        for (String tableName : tableNames) {
            this.addTable(tableName);
        }
        return this;
    }
}
