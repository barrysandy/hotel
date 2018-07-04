package com.zzk.util;

import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class base64Utils {

    @Autowired
    private HttpServletRequest request;

    public static boolean generateImage(String imgStr,String url) {
        //对字节数组字符串进行Base64解码并生成图片

        if (imgStr == null) {
            //图像数据为空
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                //调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            //新生成的图片
            OutputStream out = new FileOutputStream(url);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
