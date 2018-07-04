package com.zzk.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Kun on 2017/04/25 0025.
 */
public class DateUtils {

    public static final String CHINESE_DATE_FORAMT_YMD = "yyyy年MM月dd日";
    public static final String CHINAESE_DATETIME_FORMAT_YMDHMS = "yyyy年MM月dd日 HH:mm:ss";
    public static final String CHINAESE_DATETIME_FORMAT_YMDHM = "yyyy年MM月dd日HH时mm分";
    public static final String CHINAESE_WEEK_FORMAT_YW = "xxxx年xx周";
    public static final String CHINAESE_WEEK_FORMAT = "xxxxxx";
    public static final String DEFAULT_DATE_FORMAT_YMD = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_FORMAT_YM = "yyyy-MM";
    public static final String DEFAULT_DATE_FORMAT_MD = "MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT_YMDH = "yyyy-MM-dd HH";
    public static final String DEFAULT_DATETIME_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATETIME_FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_DATETIME_FORMAT_MDH = "MM-dd HH";
    public static final String NOSYMBOL_DATETIME_FORMAT_YMDHMS = "yyyyMMddHHmmss";
    public static final String NOSYMBOL_DATETIME_FORMAT_YMDH = "yyyyMMddHH";
    public static final String NOSYMBOL_DATETIME_FORMAT_YMD = "yyyyMMdd";
    public static final String NOSYMBOL_TIME_FORMAT_HMS = "HH:mm:ss";
    public static final String SPRIT_DATETIME_FORMAT_YMDHMS = "yyyy/MM/dd HH:mm:ss";
    public static final String SPRIT_DATETIME_FORMAT_YMDHMSS = "yyyy/MM/dd HH:mm:ss.S";
    public static final String SPRIT_DATETIME_FORMAT_YMDHM = "yyyy/MM/dd HH:mm";
    public static final String SPRIT_DATETIME_FORMAT_YMD = "yyyy/MM/dd";
    public static final String SPRIT_DATETIME_FORMAT_YMDH = "YYYY/MM/dd HH";
    public static final String FLASH_DATETIME_FORMAT_YMDH = "yyyy,MM,dd,HH,mm,ss";
    public static final String DATE_FORMAT_HOUR = "HH:00";
    public static final String DATE_SIMPLE_YMDHMS = "YMDHMS";

    private static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

    /**
     * 根据传入的日期转换成字符形式的日期
     * @param date
     * @return 如：2005-12-25 08:25:36
     */
    public static String changeDateToStr(Date date,String format) {
        if (date==null){
            return "";
        }
        if (StringUtils.isBlank(format)){
            //为空就默认以这个格式进行转换
            format = "yyyy-MM-dd HH:mm:ss";
        }
        //yyyy-MM-dd HH:mm:ss 等等格式
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateStr = formatter.format(date);
        return dateStr;
    }

    /**
     * 通过出生日期,获得年龄
     * @Param birthday
     * @return age
     */
    public static int getAge(Date birthDay){

        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("出生日期不能在未来!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            }else{
                age--;
            }
        }
        return age;
    }

    /**
     * 把字符串转换成时间，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @return
     */
    public static Date changeStrToDate(String dateStr) {
        Date temp1 = null;
        if (dateStr == null){
            return null;
        }
        if (StringUtils.isEquals(dateStr,"")){
            return null;
        }
        SimpleDateFormat formatter = null;
        try {
            if (dateStr.indexOf(" ") != -1) {
                String[] aa = StringUtils.split(dateStr, ":");
                if (aa.length == 3){
                    formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }
                else if (aa.length == 2){
                    formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                }
                else{
                    formatter = new SimpleDateFormat("yyyy-MM-dd HH");
                }
            } else {
                formatter = new SimpleDateFormat("yyyy-MM-dd");
            }
            temp1 = formatter.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp1;
    }

    /**
     * 计算一个日期的前/后几天
     * @param date
     * @param num 数值,正数为当前日期后面几天,负数为前面几天
     * @return
     * @author kun
     * @date 10:12 2018/4/24
     */
    public static Date getBeforeOrAfterDate(Date date,Integer num){
        if (date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE,num);
            return calendar.getTime();
        }
    }
    /**
     * 计算一个日期的前/后几小时
     * @param date
     * @param num 数值,正数为当前日期后面几天,负数为前面几天
     * @return
     * @author kun
     * @date 10:12 2018/4/24
     */
    public static Date getBeforeOrAfterDateByHour(Date date,Integer num){
    	if (date == null) {
    		return null;
    	} else {
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTime(date);
    		calendar.add(Calendar.HOUR_OF_DAY,num);
    		return calendar.getTime();
    	}
    }
    
    /**
     * 计算一个日期的前/后几分钟
     * @param date
     * @param num 数值,正数为当前日期后面几分钟,负数为前面几分钟
     * @return
     * @author kun
     * @date 10:12 2018/4/24
     */
    public static Date getBeforeOrAfterDateByMin(Date date,Integer num){
    	if (date == null) {
    		return null;
    	} else {
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTime(date);
    		calendar.add(Calendar.MINUTE,num);
    		return calendar.getTime();
    	}
    }

    /**
     * 获取当前时间
     * @return
     */
    public static Date getCurrentDate(){
    	return new Date();
    }

    /**
     * long转 date
     * @param time
     * @return
     */
    public static Date longToDate(long time){
    	try{
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTimeInMillis(time);
    		return calendar.getTime();
    	}catch(Exception e){
    		return null;
    	}
    }
    
    public static Date getCurrentMonthFirstDay(){
        //获取当前日期
    	 Calendar   cal_1=Calendar.getInstance();
    	 cal_1.add(Calendar.MONTH, 0);
        //设置为1号,当前日期既为本月第一天
    	 cal_1.set(Calendar.DAY_OF_MONTH,1);
         return cal_1.getTime();
    }
    public static Date getCurrentMonthLastDay(){
    	 Calendar cale = Calendar.getInstance();   
    	 cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
         return cale.getTime();
    }

	public static String getAfterDay(String date, int aday, String pattern)
			throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		java.util.Date d = dateFormat.parse(date);
		long addTime = 86400000L * aday;
		return dateFormat.format(new java.util.Date(d.getTime() + addTime));
	}
	
	public static int compareDate(java.util.Date d1, java.util.Date d2) {
		try {
			return d1.compareTo(d2);
		} catch (Exception ex) {
		}
		return -1;
	}
	
	public static int daysBetween(java.util.Date begin, java.util.Date end) {
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(begin);
		beginCalendar.set(11, 12);
		beginCalendar.set(12, 0);
		beginCalendar.set(13, 0);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		endCalendar.set(11, 12);
		endCalendar.set(12, 0);
		endCalendar.set(13, 0);
		return (int) ((endCalendar.getTimeInMillis() - beginCalendar
				.getTimeInMillis()) / 86400000L);
	}
	
	public static Date parseDate(String string, String format) {
		Date date = null;
		try {
			if (string != null) {
				SimpleDateFormat formatter = new SimpleDateFormat(format);
				date = formatter.parse(string);
			}
		} catch (Exception ex) {
		}
		return date;
	}
	public static Date getDate(){
		return new Date();
	}

	/**
	 * 把字符串转换成时间，格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date getDate(String dateStr) {
		Date temp1 = null;
		if (dateStr == null)
			return null;
		if (dateStr.equals(""))
			return null;
		SimpleDateFormat formatter = null;
		try {
			if (dateStr.indexOf(" ") != -1) {
				String[] aa = StringUtils.split(dateStr, ":");
				if (aa.length == 3)
					formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				else if (aa.length == 2)
					formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				else
					formatter = new SimpleDateFormat("yyyy-MM-dd HH");
			} else {
				formatter = new SimpleDateFormat("yyyy-MM-dd");
			}
			temp1 = formatter.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp1;
	}
	/**
     * 根据传入的日期转换成字符形式的日期
     *
     * @param date 日期型
     * @return 如：2005-12-25 08:25:36
     */
	public static String getDateTime(java.util.Date date) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String NDate = formatter.format(date);
		return NDate;
	}

	private static DateFormat getDateFormat(String dateFormat,
			ThreadLocal<DateFormat> threadLocalP, Locale locale) {
		DateFormat df = threadLocalP.get();
		if (df == null) {
			if (locale != null) {
				df = new SimpleDateFormat(dateFormat, locale);
			} else {
				df = new SimpleDateFormat(dateFormat);
			}

			threadLocalP.set(df);
		}
		return df;
	}
	public static String formatTime(Date date) throws ParseException {
		return getDateFormat(YMDHMS,threadLocal,null).format(date);
	}
	/**
     * 取得 addLong 毫秒以前（以后）的时间
     *
     * @param olddate
     * @param addLong
     * @return
     */
    public static Date getAddDate(Date olddate, long addLong) {
        long temp = olddate.getTime();
        temp += addLong;
        return new Date(temp);
    }
    /**
	 *
	 * 获取当日0点时间
	 * @return
	 * @author huashuwen
	 * @date 2018年1月9日
	 */
   public static Date getStartTime() {  
       Calendar todayStart = Calendar.getInstance();  
       todayStart.set(Calendar.HOUR_OF_DAY, 0);  
       todayStart.set(Calendar.MINUTE, 0);  
       todayStart.set(Calendar.SECOND, 0);  
       todayStart.set(Calendar.MILLISECOND, 0);  
       return todayStart.getTime();  
   } 
   
   public static Date getThisWeekMonday(Date date) {  
       Calendar cal = Calendar.getInstance();  
       cal.setTime(date);  
       // 获得当前日期是一个星期的第几天  
       int dayWeek = cal.get(Calendar.DAY_OF_WEEK);  
       if (1 == dayWeek) {  
           cal.add(Calendar.DAY_OF_MONTH, -1);  
       }  
       // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
       cal.setFirstDayOfWeek(Calendar.MONDAY);  
       // 获得当前日期是一个星期的第几天  
       int day = cal.get(Calendar.DAY_OF_WEEK);  
       // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
       cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);  
       return cal.getTime();  
   }  
 
   public static Date getNextWeekMonday(Date date) {  
       Calendar cal = Calendar.getInstance();  
       cal.setTime(getThisWeekMonday(date));  
       cal.add(Calendar.DATE, 14);  
       return cal.getTime();  
   } 
   
}
