package com.cms.template.common.mybatis;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


public class CodeGenerator {
    private static String host = "ip";
    private static String port = "3306";
    private static String database = "cms";
    private static String USERNAME = "root";
    private static String PASSWORD = "c9$e#0p@$e#0p@";

    private static String URL = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull&autoReconnect=true";
    private static String DRIVERNAME = "com.mysql.cj.jdbc.Driver";

    private static String author = "cms";
    /*package*/
    private static String parent = "com.cms.template";
    private static String entity = "entity";
    private static String service = "service";
    private static String serviceImpl = "service.impl";

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        CodeGeneratorFactory codeGeneratorFactory = new CodeGeneratorFactory(host, port, database, USERNAME, PASSWORD, parent, projectPath);
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(codeGeneratorFactory.getGlobalConfig());
        mpg.setDataSource(codeGeneratorFactory.getDataSourceConfig());
        mpg.setPackageInfo(codeGeneratorFactory.getPackageConfig());
        mpg.setCfg(codeGeneratorFactory.getInjectionConfig());
        mpg.setTemplate(codeGeneratorFactory.getTemplateConfig());
        mpg.setStrategy(codeGeneratorFactory.getStrategyConfig());
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}

