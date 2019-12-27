package com.ming.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolWriper {
    /** redis 连接对象 */
    private JedisPool jedisPoolWriper;

    public JedisPoolWriper(final JedisPoolConfig jedisPoolConfig, final String host, final int port) {
        try {
            jedisPoolWriper = new JedisPool(jedisPoolConfig, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 jedis 连接对象
     *
     * @return
     */
    public JedisPool getJedisPoolWriper() {
        return jedisPoolWriper;
    }

    /**
     * 注入 jedis 连接对象
     *
     * @param jedisPoolWriper
     */
    public void setJedisPoolWriper(JedisPool jedisPoolWriper) {
        this.jedisPoolWriper = jedisPoolWriper;
    }
}
