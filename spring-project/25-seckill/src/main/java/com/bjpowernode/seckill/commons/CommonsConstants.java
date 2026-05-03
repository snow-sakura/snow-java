package com.bjpowernode.seckill.commons;

/**
 * ClassName:CommonsConstants
 * Package:com.bjpowernode.seckill.commons
 * Description:
 *
 * @date:2018/10/25 11:15
 * @author:bjpowernode.com
 */
public class CommonsConstants {

        public static final String ZERO = "0";

        public static final String ONE = "1";

        /**库存的redis key**/
        public static final String SECKILL_STORE = "SECKILL_STORE:";

        /**已经抢购过*/
        public static final String HANDLE_USER = "HANDLE_USER:";

        /**限流的列表key*/
        public static final String LIMITING_LIST = "LIMITING_LIST";

        /**限流为商品库存的倍数，100倍**/
        public static final Integer LIMIT_GOODS_MULTIPLE = 100;

        /**订单的List**/
        public static final String ORDER_LIST = "ORDER_LIST";

        /***秒杀订单结果key*/
        public static final String ORDER_RESULT = "ORDER_RESULT:";


}
