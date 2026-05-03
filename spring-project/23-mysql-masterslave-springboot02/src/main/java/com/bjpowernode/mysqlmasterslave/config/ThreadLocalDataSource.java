package com.bjpowernode.mysqlmasterslave.config;

/**
 * ClassName:ThreadLocalDataSource
 * Package:com.bjpowernode.masterslave.config
 * Description:
 *
 * @date:2018/10/20 9:50
 * @author:bjpowernode.com
 */
public class ThreadLocalDataSource {
    private static ThreadLocal<String> local=new ThreadLocal<String>();
    public static void setDataSource(String dataSource){
        local.set(dataSource);
    }

    public static String  getDataSource(){
        return local.get();
    }
}
