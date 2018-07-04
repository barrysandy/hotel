package com.zzk.common.log;



import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * 给每个线程增加UUid标示，用来打印日志输出
 * @author cby
 *
 */
public class LogBusinessId {
    private static final ThreadLocal<String> businessLocal = new ThreadLocal<String>();
    
    /**
     * 返回当前线程对应的uuid
     * @return
     */
    public static String getCurBusinessId(){
        String uuid = businessLocal.get();
        if(StringUtils.isEmpty(uuid)){
            uuid = UUID.randomUUID().toString().replace(MarkConstant.SPLIT_CODE, "");
            businessLocal.set(uuid);
        }
        return uuid;
    }
   
    
    /**
     * 给当前线程重新设置
     */
    public static void clear(){
        businessLocal.set(null);
    }
}
