package com.cms.template.common.ssdb;

import org.nutz.ssdb4j.spi.Response;
import org.nutz.ssdb4j.spi.SSDB;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class SSDBUtils {
    private String lockKey = "ssdb:lock";
    //  锁默认过期时间,单位为秒
    private int lock_expire_ttl = 60;

    @Resource
    private SSDBClient ssdbClient;

    public SSDB getConnection() {
        return ssdbClient.getConnection();
    }

    /**
     * k-v
     */
    public boolean set(String key, String value) {
        return getConnection().set(key, value).ok();
    }

    public boolean setx(String key, String value, int ttl) {
        return getConnection().setx(key, value, ttl).ok();
    }


    /**
     * 锁操作，默认key和过期时间
     *
     * @throws Exception
     */
    public void lock() throws Exception {
        lock(lockKey, lock_expire_ttl);
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
