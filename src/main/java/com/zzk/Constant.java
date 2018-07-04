package com.zzk;

/**
 * Created by Hasee on 2018/3/7.
 */
public class Constant {

    public static String LINE_ID = "10f0dfb8a6864003a66d4f83b9664e2c";
    public static Integer PAGE_SIZE = 10;
    public static Integer PAGE_NUMBER = 1;
    //未支付超时时间 (秒)
    public static int EXPTIME = 3600;
    //预占库存存在时间(秒)
    public static int PREPARE_STOCK_TIME = 120;
    
    //商户状态 1正常 2关店 3暂停 4审核中 5审核不通过
    public static int BUSIN_OPPEN = 1;
    public static int BUSIN_CLOSE = 2;
    public static int BUSIN_PAUSE = 3;
    public static int BUSIN_EXAMINE = 4;
    public static int BUSIN_NO= 5;
}
