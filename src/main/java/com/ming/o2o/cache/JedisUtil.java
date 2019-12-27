package com.ming.o2o.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.util.SafeEncoder;

import java.util.Set;

public class JedisUtil {
    /** 操作 Key 的方法 */
    public Keys KEYS;
    /** 对存储对象为 String 的操作 */
    public Strings STRINGS;
    /** Redis 连接对象 */
    private JedisPool jedisPool;

    /**
     * 获取 redis 连接池
     *
     * @return
     */
    public JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * 设置 redis 连接池
     *
     * @param jedisPoolWriper
     */
    public void setJedisPool(JedisPoolWriper jedisPoolWriper) {
        this.jedisPool = jedisPoolWriper.getJedisPoolWriper();
    }

    /**
     * 从 jedis 连接池中获取 jedis 对象
     *
     * @return
     */
    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    // ******************************** 内部类 keys ********************************
    public class Keys {

        public Keys(JedisUtil jedisUtil) {
        }

        /**
         * 清空所有的 key
         *
         * @return
         */
        public String flushAll() {
            Jedis jedis = getJedis();
            String stata = jedis.flushAll();
            jedis.close();
            return stata;
        }

        /**
         * 删除 keys 对应的记录，可以是多个 key
         *
         * @param keys
         * @return
         */
        public long del(String... keys) {
            Jedis jedis = getJedis();
            long count = jedis.del(keys);
            jedis.close();
            return count;
        }

        /**
         * 判断 key 是否存在
         *
         * @param key
         * @return
         */
        public boolean exists(String key) {
            Jedis sjedis = getJedis();
            boolean exis = sjedis.exists(key);
            sjedis.close();
            return exis;
        }

        /**
         * 查询所有匹配给定的模式的键
         *
         * @param pattern
         * @return
         */
        public Set<String> keys(String pattern) {
            Jedis jedis = getJedis();
            Set<String> set = jedis.keys(pattern);
            jedis.close();
            return set;
        }
    }

        //******************************** 内部类 Strings ********************************


        public class Strings{

            public Strings(JedisUtil jedisUtil) {
            }

            /**
             * 根据 key 获取记录值
             *
             * @param key
             * @return
             */
            public String get(String key) {
                Jedis sjedis = getJedis();
                String value = sjedis.get(key);
                sjedis.close();
                return value;
            }

            /**
             * 添加记录，如果记录已存在将覆盖原有 value
             *
             * @param key
             * @param value
             * @return
             */
            public String set(String key, String value) {
                return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
            }

            /**
             * 添加记录，如果记录已存在将覆盖原有 value
             *
             * @param key
             * @param value
             * @return
             */
            public String set(byte[] key, byte[] value) {
                Jedis jedis = getJedis();
                String status = jedis.set(key, value);
                jedis.close();
                return status;
            }
        }
}
