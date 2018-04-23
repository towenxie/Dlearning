/*
 * Copyright (c) 2016 Augmentum, Inc. All rights reserved.
 */
package org.towen.deeplearning.platform.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.towen.deeplearning.platform.redis.conf.RedisConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
@Scope("prototype")
public class RedisInstance {

    @Autowired
    private RedisConfig redisConfig;

    private JedisPool jedisPool;

    public Jedis getJedis() {
        if (jedisPool == null) {
            initJedisPool();
        }
        Jedis jedis = jedisPool.getResource();

        if (redisConfig.getDbIndex() > 0) {
            jedis.select(redisConfig.getDbIndex());
        }

        return jedis;
    }

    public void returnResource(Jedis jedis) {
        jedis.close();
    }

    private void initJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        jedisPoolConfig.setMinIdle(redisConfig.getPoolMinIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        jedisPoolConfig.setTestOnBorrow(redisConfig.isPoolTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(redisConfig.isPoolTestOnReturn());
        jedisPoolConfig.setTestWhileIdle(redisConfig.isPoolTestWhileIdle());
        jedisPoolConfig.setNumTestsPerEvictionRun(redisConfig.getPoolNumTestsPerEvictionRun());
        jedisPoolConfig.setMinEvictableIdleTimeMillis(redisConfig.getPoolMinEvictableIdleTimeMillis());
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(redisConfig.getPoolTimeBetweenEvictionRunsMillis());

        if (StringUtils.isBlank(redisConfig.getPassword())) {
            jedisPool =
                    new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(),
                            redisConfig.getPoolConnectionTimeout());
        } else {
            jedisPool =
                    new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(),
                            redisConfig.getPoolConnectionTimeout(), redisConfig.getPassword());
        }
    }

    public RedisConfig getRedisConfig() {
        return redisConfig;
    }

}
