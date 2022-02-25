package com.cms.template.common.ssdb;

import org.nutz.ssdb4j.spi.Response;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("ssdbLock")
public class SSDBLock {
    @Resource(name = "ssdbClient")
    private SSDBClient ssdbClient;

    private String lockKey = "ssdb:lock";
    private int ttl = 60;

    /**
     * 锁操作，默认key和过期时间
     *
     * @throws Exception
     */
    public void lock() throws Exception {
        lock(lockKey, ttl);
    }

    /**
     * 锁操作，指定key和过期时间
     *
     * @param lockKey
     * @param ttl
     * @throws Exception
     */
    public void lock(String lockKey, int ttl) throws Exception {
        boolean flag = getLock(lockKey, ttl);
        while (!flag) {
            Thread.sleep(1000);
            flag = getLock(lockKey, ttl);
        }
    }

    /**
     * 获取锁
     *
     * @param lockKey
     * @param ttl
     * @return
     * @throws Exception
     */
    private boolean getLock(String lockKey, int ttl) throws Exception {
        boolean lock = false;
        Response result = ssdbClient.getConnection().incr(lockKey, 1);
        if (result.ok()) {
            lock = true;
            ssdbClient.getConnection().expire(lockKey, ttl);
        }
        return lock;
    }

    /**
     * 释放锁，默认key
     */
    public void unlock() {
        try {
            ssdbClient.getConnection().del(lockKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放锁，指定key
     *
     * @param lockKey
     */
    public void unlock(String lockKey) {
        try {
            ssdbClient.getConnection().del(lockKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}