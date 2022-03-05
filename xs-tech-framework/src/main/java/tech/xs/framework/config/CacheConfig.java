package tech.xs.framework.config;


import tech.xs.framework.core.cache.Cache;
import tech.xs.framework.core.cache.RedisCache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author 沈家文
 * @date 2021/2/23 11:14
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Resource
    private RedisCache redisCache;

    @Bean
    public Cache cache() {
        return redisCache;
    }

}
