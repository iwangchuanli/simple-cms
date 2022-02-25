package com.cms.template.common.mybatis;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGeneratorFactory {
    private String host = "localhost";
    private String port = "3306";
    private String database = "database";
    private String USERNAME = "root";
    private String PASSWORD = "123456789";
    /*package*/
    private String parent = "com.demo";

    private String URL = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull&autoReconnect=true";

    private String DRIVERNAME = "com.mysql.cj.jdbc.Driver";

    private String author = "author";

    private String entity = "entity";
    private String service = "service";
    private String serviceImpl = "service.impl";
    private String ModuleName = null;
    private String projectPath = System.getProperty("user.dir");

    public CodeGeneratorFactory(String host, String port, String database, String username, String password, String parent, String projectPath) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.USERNAME = username;
        this.PASSWORD = password;
        this.parent = parent;
        this.projectPath = projectPath;
        this.URL = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull&autoReconnect=true";
    }


    /**
     * <p>
     * read input value
     * </p>
     */
    public String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("please input" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("please input right" + tip + "!");
    }

    public GlobalConfig getGlobalConfig() {
        // global config
        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(author);
        gc.setOpen(false);
        // service nameType
        gc.setServiceName("%sService");
        // service impl nameType
        gc.setServiceImplName("%sServiceImpl");
        // nameType  %s
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setDateType(DateType.SQL_PACK);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML Second Cache
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        gc.setSwagger2(false); //Swagger2 ann
        return gc;
    }

    public DataSourceConfig getDataSourceConfig() {
        // dataSource
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(URL);
        // dsc.setSchemaName("public");
        dsc.setDriverName(DRIVERNAME);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        return dsc;
    }

    public PackageConfig getPackageConfig() {
        // package config
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName(scanner("package"));
        pc.setParent(parent);
        pc.setEntity(entity);
        pc.setService(service);
        pc.setServiceImpl(serviceImpl);
        return pc;
    }

    public InjectionConfig getInjectionConfig() {
        // customer config
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // template freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // template velocity
        // String templatePath = "/templates/mapper.xml.vm";
        // FileOutConfig
        List<FileOutConfig> focList = new ArrayList<>();
        // FileOutConfig
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String moduleName = ModuleName == null ? "" : ModuleName;
                return projectPath + "/src/main/resources/mapper/" + moduleName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                checkDir("createdDir");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    public TemplateConfig getTemplateConfig() {
// templateConfig
        TemplateConfig templateConfig = new TemplateConfig();
        // not .ftl/.vm
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
//        templateConfig.setController("");
//        templateConfig.setController("templates/controller.java");
        templateConfig.setXml(null);
        return templateConfig;
    }

    public StrategyConfig getStrategyConfig() {
        // strategy config
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("cn.com.bluemoon.demo.entity");
        strategy.setEntityLombokModel(true);
//        strategy.setRestControllerStyle(true);
        // public
//        strategy.setSuperControllerClass("cn.com.bluemoon.demo.controller");
        //strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("table name,user ',' to split").split(","));
//        strategy.setInclude("t_coupon_info");
//        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("t_");//remove table prefix
        return strategy;
    }


    public static void run(GlobalConfig gc, DataSourceConfig dsc, PackageConfig pc, InjectionConfig cfg, TemplateConfig templateConfig, StrategyConfig strategy) {
        // AutoGenerator
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(gc);
        mpg.setDataSource(dsc);
        mpg.setPackageInfo(pc);
        mpg.setCfg(cfg);
        mpg.setTemplate(templateConfig);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}

