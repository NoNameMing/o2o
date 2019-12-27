package com.ming.o2o.service;

public interface CacheService {
    /**
     * 根据 key 前缀删除该匹配模式下所有的 key-value
     *
     * @param keyPrefix
     */
    void removeFromCache(String keyPrefix);
}
