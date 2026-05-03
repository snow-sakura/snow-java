package com.bjpowernode.mysqlmasterslave.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * ClassName:DynamicDataSource
 * Package:com.bjpowernode.masterslave.config
 * Description:
 *
 * @date:2018/10/20 9:48
 * @author:bjpowernode.com
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    public static final String MASTER="master";
    public static final String SLAVE="slave";
    @Override
    protected Object determineCurrentLookupKey() {
        return ThreadLocalDataSource.getDataSource();
    }
}
