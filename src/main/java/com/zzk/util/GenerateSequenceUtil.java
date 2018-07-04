package com.zzk.util;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;


public class GenerateSequenceUtil {
	private static final Logger logger = Logger.getLogger("log");  
	 /** The FieldPosition. */  
	private static final FieldPosition HELPER_POSITION = new FieldPosition(0);  
	/**This Format for format the data to special format. */  
//	private final static Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmssS");  
	private final static Format DATEFORMT = new SimpleDateFormat("YYYYMMddssS");  
	 /** This Format for format the number tospecial format. */  
	private final static NumberFormat NUMBERFORMT = new DecimalFormat("0000");  
	/**This int is the sequence number ,the default value is 0. */  
	 private static int seq = 0;  
	 private static final int MAX = 9999;  
	 /** 
	 * 时间格式生成序列 
	 * @return String 
	 */  
	 public static synchronized String generateSequenceNo() {  
	 Calendar rightNow = Calendar.getInstance();  
	 StringBuffer sb = new StringBuffer();  
	 DATEFORMT.format(rightNow.getTime(), sb,HELPER_POSITION);  
	 NUMBERFORMT.format(seq, sb, HELPER_POSITION);  
	 if (seq == MAX) {  
	 seq = 0;  
	}else {  
	 seq++;  
	 }  
	   
	 logger.info("THE SQUENCE IS :" +sb.toString());  
	   
	 return sb.toString();  
	 }  
	 /** 
	  * 时间格式生成序列 
	  * @return String 
	  */  
	 public static synchronized String generateBillNo() {  
		 Calendar rightNow = Calendar.getInstance();  
		 StringBuffer sb = new StringBuffer();  
		 DATEFORMT.format(rightNow.getTime(), sb,HELPER_POSITION);	
		 logger.info("THE SQUENCE IS :" +sb.toString());  
		 return sb.toString();  
	 }  
	}  
