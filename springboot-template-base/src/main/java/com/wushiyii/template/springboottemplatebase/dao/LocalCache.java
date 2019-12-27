package com.wushiyii.template.springboottemplatebase.dao;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 使用 guava cache 创建的本地缓存
 */
@Repository
public class LocalCache {

    private Cache<String, String> localCache = null;

    @PostConstruct
    public void init() {
        if (null == localCache) {
            CacheBuilder<Object, Object> localCacheBuilder = CacheBuilder.newBuilder().maximumSize(1000).initialCapacity(1000);
            localCacheBuilder.expireAfterWrite(2, TimeUnit.HOURS);
            localCache = localCacheBuilder.build();
        }
    }

    public String queryByKey(String key) {
        return localCache.getIfPresent(key);
    }

    public void put(String key, String value) {
        localCache.put(key, value);
    }

    public void deleteToken(String token) {
        localCache.invalidate(token);
    }

    public void clean() {
        localCache.invalidateAll();
    }
}
