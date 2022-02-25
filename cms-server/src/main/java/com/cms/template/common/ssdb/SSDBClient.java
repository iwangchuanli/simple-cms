package com.cms.template.common.ssdb;


import com.cms.template.common.ssdb.configuration.SSDBConfig;
import lombok.Data;
import org.nutz.ssdb4j.SSDBs;
import org.nutz.ssdb4j.spi.SSDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.io.IOException;


@Data
public class SSDBClient {

    private Logger logger = LoggerFactory.getLogger(SSDBClient.class);

    private String host;

    private int port;

    private int timeout;

    private byte[] auth;

    private SSDB ssdb;

    private SSDBConfig config;

    public void init() {

        ssdb = SSDBs.pool(host, port, timeout,config,auth);
    }

    public SSDB getConnection() {
        return ssdb;
    }

    @PreDestroy
    private void destructor() {
        try {
            ssdb.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("", e);
        }
    }


}
