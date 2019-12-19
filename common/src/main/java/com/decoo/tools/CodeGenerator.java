package com.decoo.tools;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/** 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 * @author ghd
 */
public class CodeGenerator {
    private static final String BIT = "bit";
    private static final String DATE_TIME = "datetime";
    private static final String TIME_STAMP = "timestamp";
    private static final String DATE_CONSTANT = "date";

    /**
     *         (~/(?i)bigint/)                   : "Long",
     *         (~/(?i)int|tinyint/)              : "Integer",
     *         (~/(?i)float|double|decimal|real/): "java.math.BigDecimal",
     *         (~/(?i)datetime|timestamp/)       : "java.sql.Timestamp",
     *         (~/(?i)date/)                     : "java.sql.Date",
     *         (~/(?i)bit/)                      : "Boolean",
     *         (~/(?i)time/)                     : "java.sql.Timestamp",
     *         (~/(?i)/)                         : "String"
     * @param args
     */
    public static void main(String[] args) {
        String entitySuffix = "PO";
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        gc.setOutputDir(projectPath + "/vpc-common/src/main/java");
        gc.setAuthor("system");
        gc.setOpen(false);
        //是否支持AR模式
        gc.setActiveRecord(true);
        //文件覆盖(全新文件)
        gc.setFileOverride(true);
        //实体属性 Swagger2 注解
         gc.setSwagger2(true);
        //SQL 映射文件
        gc.setBaseResultMap(true);
        //SQL 片段 // 主键策略 //设置实体后缀
        gc.setBaseColumnList(true)
                .setIdType(IdType.INPUT)
                .setEntityName("%s" + entitySuffix);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = getDataSourceConfig();
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.vcredit.vpc.common");
        //设置实体包名
        pc.setEntity("po");

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
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/vpc-common/src/main/resources/mapper"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
        List<TableFill> tableFillList = new ArrayList<>();
        TableFill createField = new TableFill("created_time", FieldFill.INSERT);
        TableFill modifiedField = new TableFill("updated_time", FieldFill.INSERT_UPDATE);
        tableFillList.add(createField);
        tableFillList.add(modifiedField);
        // 策略配置
        StrategyConfig strategy = getStrategyConfig(tableFillList);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    private static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://10.138.61.43:3306/vpc?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2b8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("wx2019");

        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                System.out.println("转换类型：" + fieldType);
                //bit 转换成 Boolean
                if (fieldType.toLowerCase().contains(BIT)) {
                    return DbColumnType.BOOLEAN;
                }
                //将数据库中 datetime ， timestamp转换成 java.sql.Timestamp
                if (fieldType.toLowerCase().contains(DATE_TIME) || fieldType.toLowerCase().contains(TIME_STAMP)) {
                    return DbColumnType.TIMESTAMP;
                }
                //date 转换成 java.sql.Date
                if (fieldType.toLowerCase().contains(DATE_CONSTANT)) {
                    return DbColumnType.DATE_SQL;
                }
                return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
            }
        });
        return dsc;
    }

    private static StrategyConfig getStrategyConfig(List<TableFill> tableFillList) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setVersionFieldName("version");
        strategy.setTableFillList(tableFillList);

        String[] tableNames = {"t_deduct_instruction"};
        //指定生成某个表
        strategy.setInclude(tableNames);
        //设置超级超级列
        strategy.setSuperEntityColumns("id");
        //设置controller映射联字符
        strategy.setControllerMappingHyphenStyle(true);
        //表的前缀
        strategy.setTablePrefix("t_");
        return strategy;
    }

}
