package com.zzk.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Kun on 2017/04/25 0025.
 */
public class StringUtils {
	public static final String EMPTY = "";
	
	
    public static String[] split(String source, String delim) {
        String[] wordLists;
        if (source == null) {
            wordLists = new String[1];
            wordLists[0] = source;
            return wordLists;
        }
        if (delim == null) {
            delim = ",";
        }
        StringTokenizer st = new StringTokenizer(source, delim);
        int total = st.countTokens();
        wordLists = new String[total];
        for (int i = 0; i < total; i++) {
            wordLists[i] = st.nextToken();
        }
        return wordLists;
    }


    public static boolean isBlank(String str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }


    /**
     * 转utf8格式
     * @param str 参数
     * @return String
     * @author zhou.zhengkun
     * @date 2017/12/21 0021 16:07
     */
    public static String toUTF8(String str) {
        try {
            if (str == null){
                return null;
            }
            str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
            return str;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * String[]转int[]
     * @param arrs
     * @return
     */
    public static Integer[] stringArrToIntergerArr(String[] arrs){
        Integer[] ints = new Integer[arrs.length];
        for(int i=0;i<arrs.length;i++){
            ints[i] = Integer.parseInt(arrs[i]);
        }
        return ints;
    }

    /**
     * String[]转Set<Integer> 并去除重复元素
     * Integer[] 可有Set<Integer>.toArray()取得
     * @param arrs
     * @return
     */
    public static Set<Integer> stringArrToIntergerSet(String[] arrs){
        Set<Integer> integerSet = new HashSet<Integer>();
        for(int i=0;i<arrs.length;i++){
            integerSet.add(Integer.parseInt(arrs[i]));
        }
        return integerSet;
    }


    /**
     * 判断字符串是空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if("".equals(str)|| str==null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断字符串不是空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        if(!"".equals(str)&&str!=null){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 判断某一个字符串数组中是否含有某一字符串
     * @param str
     * @param strArr
     * @return
     */
    public static boolean existStrArr(String str,String []strArr){
        return Arrays.asList(strArr).contains(str);
    }


    public static boolean isEquals(String str1, String str2) {
        if (str1 == null || str2 == null){
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }


    /**
     * 返回去除横杠的UUID
     * @param length 返回字符串的长度
     * @return String
     * @author zhou.zhengkun
     * @date 2018/01/25 0025 10:02
     */
    public static String getRadomNumber(int length){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-","");
        if (length>0 && length<uuid.length()){
            uuid = uuid.substring(0,length-1);
        }
        return uuid;
    }

    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-","");
        return uuid;
    }
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }
    /**
     * 验证是否是有效手机号
     * @param str
     * @return
     * @throws PatternSyntaxException
     * @author John
     * @date： 2018年3月22日 上午10:11:52
     */
    public static  boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {  
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return m.matches();  
    } 
    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     * @throws PatternSyntaxException
     * @author hua
     * @date： 2018年3月22日 上午10:11:52
     */
    public static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
    }
    
    public static boolean isEmpty(Object s){

  		return s == null || s.toString().trim().length() == 0 || s.toString().trim().equalsIgnoreCase("null");

  	}
    
	public static boolean isNumeric(CharSequence cs) {
		if ((cs == null) || (cs.length() == 0)) {
			return false;
		}
		int sz = cs.length();
		for (int i = 0; i < sz; ++i) {
			if (!(Character.isDigit(cs.charAt(i)))) {
				return false;
			}
		}
		return true;
	}
	
	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	public static String join(Object[] array, String separator, int startIndex,
			int endIndex) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = EMPTY;
		}

		// endIndex - startIndex > 0: Len = NofStrings *(len(firstString) +
		// len(separator))
		// (Assuming that all Strings are roughly equally long)
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0) {
			return EMPTY;
		}

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex]
				.toString().length()) + separator.length());

		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}
}
