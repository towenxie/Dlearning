/*
 * Copyright (c) 2015 Forte Tradebook, Inc. All rights reserved.
 */
package org.towen.deeplearning.platform.redis.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:app.properties")
public class RedisConfig {

    @Value("${redis.host:127.0.0.1}")
    private String host;
    
    @Value("${redis.port:6379}")
    private int port;

    @Value("${redis.password:123}")
    private String password;

    @Value("${redis.dbIndex:0}")
    private int dbIndex;

    @Value("${redis.poolConnectionTimeout:3000}")
    private int poolConnectionTimeout = 3000;

    @Value("${redis.poolMaxTotal:1000}")
    private int poolMaxTotal = 1000;

    @Value("${redis.poolMaxIdle:30}")
    private int poolMaxIdle = 30;

    @Value("${redis.poolMinIdle:5}")
    private int poolMinIdle = 5;

    @Value("${redis.poolMaxWait:3000}")
    private int poolMaxWait = 3000;

    @Value("${redis.poolTestOnBorrow:true}")
    private boolean poolTestOnBorrow = true;

    @Value("${redis.poolTestOnReturn:false}")
    private boolean poolTestOnReturn = false;

    @Value("${redis.poolTestWhileIdle:true}")
    private boolean poolTestWhileIdle = true;

    @Value("${redis.poolMinEvictableIdleTimeMillis:60000}")
    private int poolMinEvictableIdleTimeMillis = 60000;

    @Value("${redis.poolTimeBetweenEvictionRunsMillis:30000}")
    private int poolTimeBetweenEvictionRunsMillis = 30000;

    @Value("${redis.poolNumTestsPerEvictionRun:1}")
    private int poolNumTestsPerEvictionRun = 1;

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getPassword() {
		return password;
	}

	public int getDbIndex() {
		return dbIndex;
	}

	public int getPoolConnectionTimeout() {
		return poolConnectionTimeout;
	}

	public int getPoolMaxTotal() {
		return poolMaxTotal;
	}

	public int getPoolMaxIdle() {
		return poolMaxIdle;
	}

	public int getPoolMinIdle() {
		return poolMinIdle;
	}

	public int getPoolMaxWait() {
		return poolMaxWait;
	}

	public boolean isPoolTestOnBorrow() {
		return poolTestOnBorrow;
	}

	public boolean isPoolTestOnReturn() {
		return poolTestOnReturn;
	}

	public boolean isPoolTestWhileIdle() {
		return poolTestWhileIdle;
	}

	public int getPoolMinEvictableIdleTimeMillis() {
		return poolMinEvictableIdleTimeMillis;
	}

	public int getPoolTimeBetweenEvictionRunsMillis() {
		return poolTimeBetweenEvictionRunsMillis;
	}

	public int getPoolNumTestsPerEvictionRun() {
		return poolNumTestsPerEvictionRun;
	}

}
