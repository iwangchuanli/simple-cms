package com.cms.template.common.ssdb.configuration;


import com.cms.template.common.ssdb.SSDBClient;
import com.cms.template.common.ssdb.properties.SSDBProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfig {

    @Autowired
    private SSDBProperties ssdbProperties;

    @Bean
    public SSDBConfig ssdbConfig() {
        SSDBConfig ssdbConfig = new SSDBConfig();
        ssdbConfig.setMaxActive(ssdbProperties.getMaxActive());
        ssdbConfig.setTestWhileIdle(true);
        return ssdbConfig;
    }

    @Bean
    public SSDBClient ssdbClient(SSDBConfig ssdbConfig) {
        SSDBClient ssdbClient = new SSDBClient();
        ssdbClient.setHost(ssdbProperties.getHost());
        ssdbClient.setPort(ssdbProperties.getPort());
        ssdbClient.setTimeout(ssdbProperties.getTimeout());
        ssdbClient.setAuth(ssdbProperties.getPassword().getBytes());
        ssdbClient.setConfig(ssdbConfig);
        ssdbClient.init();
        return ssdbClient;
    }
}
