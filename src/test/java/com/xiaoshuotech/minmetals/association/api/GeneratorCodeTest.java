package com.xiaoshuotech.minmetals.association.api;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * 代码生成器
 *
 * @author heng.yang
 * @date 2020/12/10
 */
public class GeneratorCodeTest {

    @Test
    public void test() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("wing");
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setFileOverride(true);
        gc.setOpen(false);
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.1.16:3306/minmetals_association?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT");
        // dsc.setSchemaName("public")
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("Xiaoshuo123@");
        dsc.setTypeConvert(new MySqlTypeConvert());

        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("");
        pc.setParent("com.xiaoshuotech.minmetals.association.api");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

//        // 自定义输出配置
        List<FileOutConfig> focList = Lists.newArrayList();
//        //实体dto ftl模板路径
        String templatePath = "/templates/entityDto.java.ftl";
        //定义实体DTO文件输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java"  + "/com/xiaoshuotech/minmetals/association/api/dto/" + tableInfo.getEntityName().toLowerCase()+ StringPool.SLASH + tableInfo.getEntityName() + "DTO" + StringPool.DOT_JAVA;
            }
        });

        //列表请求request ftl模板路径
        templatePath = "/templates/listRequest.java.ftl";
        //定义列表请求request文件输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java"  + "/com/xiaoshuotech/minmetals/association/api/dto/" + tableInfo.getEntityName().toLowerCase()+ StringPool.SLASH + tableInfo.getEntityName() + "ListRequest" + StringPool.DOT_JAVA;
            }
        });

        //详情请求request ftl模板路径
        templatePath = "/templates/detailRequest.java.ftl";
        //定义详情请求request文件输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java"  + "/com/xiaoshuotech/minmetals/association/api/dto/" + tableInfo.getEntityName().toLowerCase()+ StringPool.SLASH + tableInfo.getEntityName() + "DetailRequest" + StringPool.DOT_JAVA;
            }
        });

        //新增请求request ftl模板路径
        templatePath = "/templates/addRequest.java.ftl";
        //定义新增请求request文件输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java"  + "/com/xiaoshuotech/minmetals/association/api/dto/" + tableInfo.getEntityName().toLowerCase()+ StringPool.SLASH + "Add" + tableInfo.getEntityName() + "Request" + StringPool.DOT_JAVA;
            }
        });

        //编辑请求request ftl模板路径
        templatePath = "/templates/editRequest.java.ftl";
        //定义编辑请求request文件输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java"  + "/com/xiaoshuotech/minmetals/association/api/dto/" + tableInfo.getEntityName().toLowerCase()+ StringPool.SLASH + "Edit" + tableInfo.getEntityName() + "Request" + StringPool.DOT_JAVA;
            }
        });

        //mapper.xml ftl模板路径
        templatePath = "/templates/mapper.xml.ftl";
        //定义mapper.xml文件输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/"+ pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        TemplateConfig templateConfig = new TemplateConfig();
//        templateConfig.setController("templates/controller1.java");
//        templateConfig.setService();
//        templateConfig.setServiceImpl();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.xiaoshuotech.cloud.mybatis.entity.BaseModel");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns(new String[]{"id","addedBy","addedByName","addedTime","lastModifiedBy","lastModifiedByName","lastModifiedTime","lastModifiedIp","valid"});
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        strategy.setSuperControllerClass("com.xiaoshuotech.minmetals.association.api.controller.BaseController");

        // 需要生成的表
        strategy.setInclude("t_customer_company");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("t_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
