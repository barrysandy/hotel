/**   
* @Description:  
* @author sty   
* @date 2017年4月6日 下午5:35:18 
* @version V1.0   
*/
package com.zzk.util;

import java.security.MessageDigest;

public class PasswordEncoder {
	
	 private final static String[] HEXDIGITS = { "0", "1", "2", "3", "4", "5",
	   "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	 
	 private Object salt;
	 private String algorithm;
	 
	 public PasswordEncoder(Object salt, String algorithm) {
	  this.salt = salt;
	  this.algorithm = algorithm;
	 }

	 public String encode(String rawPass) {
	  String result = null;
	  try {
	   MessageDigest md = MessageDigest.getInstance(algorithm);
	   //加密后的字符串  
	   result = byteArrayToHexString(md.digest(mergePasswordAndSalt(rawPass).getBytes("utf-8")));
	  } catch (Exception ex) {
	  }
	  return result;
	 }
	 
	    public boolean isPasswordValid(String encPass, String rawPass) {
	        String pass1 = "" + encPass;
	        String pass2 = rawPass;

	        return pass1.equals(pass2);
	    }
	    
	    private String mergePasswordAndSalt(String password) {
	        if (password == null) {
	            password = "";
	        }

	        if ((salt == null) || "".equals(salt)) {
	            return password;
	        } else {
	            return password + "_" + salt.toString();
	        }
	    }

	 /**
	  * 转换字节数组为16进制字串
	  * @param b 字节数组
	  * @return 16进制字串
	  */
	 private static String byteArrayToHexString(byte[] b) {
	  StringBuffer resultSb = new StringBuffer();
	  for (int i = 0; i < b.length; i++) {
	   resultSb.append(byteToHexString(b[i]));
	  }
	  return resultSb.toString();
	 }

	 private static String byteToHexString(byte b) {
	  int n = b;
	  if(n < 0){
		   n = 256 + n;
		  }
	  int d1 = n / 16;
	  int d2 = n % 16;
	  return HEXDIGITS[d1] + HEXDIGITS[d2];
	 }
}