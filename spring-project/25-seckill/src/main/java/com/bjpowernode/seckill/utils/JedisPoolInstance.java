package com.bjpowernode.seckill.utils;

/**
 * ClassName:JedisPoolInstance
 * Package:com.bjpowernode.seckill.utils
 * Description:
 *
 * @date:2018/10/25 11:18
 * @author:bjpowernode.com
 */

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接池示例
 * （单例模式）
 *
 */
public class JedisPoolInstance {

    //redis服务器的ip地址
    private static final String HOST = "192.168.31.128";

    private static final String PASSWORD = "123456";

    //redis服务器的端口
    private static final int PORT = 6379;

    //redis连接池对象
    private static JedisPool jedisPool = null;

    //私有构造方法
    private JedisPoolInstance() {
    }

    /**
     * 获取线程池实例对象
     *
     * @return
     */
    public static JedisPool getJedisPoolInstance() {
        if (null == jedisPool) {
            synchronized (JedisPoolInstance.class) {
                if (null == jedisPool) {
                    //对连接池的参数进行配置，根据项目的实际情况配置这些参数
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxTotal(1000);//最大连接数
                    poolConfig.setMaxIdle(32);//最大空闲连接数
                    poolConfig.setMaxWaitMillis(90*1000);//获取连接时的最大等待毫秒数
                    poolConfig.setTestOnBorrow(true);//在获取连接的时候检查连接有效性
                    jedisPool = new JedisPool(poolConfig, HOST, PORT, 15000, PASSWORD);
                }
            }
        }
        return jedisPool;
    }
}