package com.zzk.common.log;




import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 日志的业务类型，对重要的业务日志需要在这里定义类型
 * 
 * @author cby
 */
public enum LogTypeEnum {
	DEFAULT("DEFAULT", "默认"), 
	EXCEPTION("EXCEPTION", "运行错误"),
    RPC_INTERFACE("RPC_INTERFACE","远程调用接口日志"),
	WS_INTERFACE("WS_INTERFACE","远程调用接口日志"),
	/**
	 * 拦截器日志
	 */
	SECURITY_INTERCEPTOR("SECURITY_INTERCEPTOR", "拦截器日志");

	private String key;
	private String value;
	private static final Log log = LogFactory.getLog(LogTypeEnum.class);

	LogTypeEnum() {
	}

	LogTypeEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public static String getValue(String key) {
		for (LogTypeEnum ft : LogTypeEnum.values()) {
			if (ft.getKey().equals(key)) {
				return ft.getValue();
			}
		}
		return null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Log getLog() {
		return LogHelper.getLog(this);
	}
	/**
	 * 增加日志打印calss信息
	 * @param str
	 * @return
	 */
	public static String getClassAndlin(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		long start = System.currentTimeMillis();
		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		boolean boo = false;
		for (StackTraceElement ste : stack) {
			if(StringUtils.isBlank(ste.getFileName())){
				continue;
			}
			if ((ste.getFileName().indexOf("LogTypeEnum.java")) != -1) {
				boo = true;
			} else if (boo) {
				return new StringBuffer(MarkConstant.LEFT_BRACKET).append(ste.getFileName()).append(MarkConstant.SPLIT_CODE)
						.append(ste.getLineNumber()).append(MarkConstant.RIGHT_BRACKET).append(MarkConstant.BLANK).append(str).toString();
			}
		}
		log.info("----------------"+(System.currentTimeMillis()-start));
		return str;
	}

	/**
	 * 记录DEBUG信息
	 * 
	 * @param str
	 *            日志信息，可使用占位符"{}"，占位符将会被args中的参数依次替换
	 * @param args
	 *            替换占位符的参数，如果没有该参数则不会替换
	 * @param e
	 */
	public void debug(Throwable e, String str, Object... args) {
		LogHelper.debug(this, e, getClassAndlin(str), args);
	}

	/**
	 * 记录INFO信息
	 * 
	 * @param str
	 *            日志信息，可使用占位符"{}"，占位符将会被args中的参数依次替换
	 * @param args
	 *            替换占位符的参数，如果没有该参数则不会替换
	 * @param e
	 */
	public void info(Throwable e, String str, Object... args) {
		LogHelper.info(this, e,getClassAndlin(str), args);
	}

	/**
	 * 记录warn信息
	 * 
	 * @param str
	 *            日志信息，可使用占位符"{}"，占位符将会被args中的参数依次替换
	 * @param args
	 *            替换占位符的参数，如果没有该参数则不会替换
	 */
	public void warn(Throwable e, String str, Object... args) {
		LogHelper.warn(this, e,getClassAndlin(str), args);
	}

	/**
	 * 记录error信息
	 * 
	 * @param str
	 *            日志信息，可使用占位符"{}"，占位符将会被args中的参数依次替换
	 * @param args
	 *            替换占位符的参数，如果没有该参数则不会替换
	 * @param e
	 */
	public void error(Throwable e, String str, Object... args) {
		LogHelper.error(this, e,getClassAndlin(str), args);
	}

	/**
	 * 记,getClassAndlin(str),信息
	 * 
	 * @param str
	 *            日志信息，可使用占位符"{}"，占位符将会被args中的参数依次替换
	 * @param args
	 *            替换占位符的参数，如果没有该参数则不会替换
	 * @param e
	 */
	public void fatal(Throwable e, String str, Object... args) {
		LogHelper.fatal(this, e,getClassAndlin(str), args);
	}

	/**
	 * 记录DEBUG信息
	 * 
	 * @param str
	 *            日志信息，可使用占位符"{}"，占位符将会被args中的参数依次替换
	 * @param args
	 *            替换占位符的参数，如果没有该参数则不会替换
	 */
	public void debug(String str, Object... args) {
		LogHelper.debug(this,getClassAndlin(str), args);
	}

	/**
	 * 记录INFO信息
	 * 
	 * @param str
	 *            日志信息，可使用占位符"{}"，占位符将会被args中的参数依次替换
	 * @param args
	 *            替换占位符的参数，如果没有该参数则不会替换
	 */
	public void info(String str, Object... args) {
		LogHelper.info(this,getClassAndlin(str), args);
	}

	/**
	 * 记录warn信息
	 * 
	 * @param str
	 *            日志信息，可使用占位符"{}"，占位符将会被args中的参数依次替换
	 * @param args
	 *            替换占位符的参数，如果没有该参数则不会替换
	 */
	public void warn(String str, Object... args) {
		LogHelper.warn(this,getClassAndlin(str), args);
	}

	/**
	 * 记录error信息
	 * 
	 * @param str
	 *            日志业务类型
	 * @param str
	 */
	public void error(String str, Object... args) {
		LogHelper.error(this,getClassAndlin(str), args);
	}

	/**
	 * 记录fatal信息
	 * 
	 * @param str
	 *            日志信息，可使用占位符"{}"，占位符将会被args中的参数依次替换
	 * @param args
	 *            替换占位符的参数，如果没有该参数则不会替换，如果没有该参数则不会替换
	 */
	public void fatal(String str, Object... args) {
		LogHelper.fatal(this,getClassAndlin(str), args);
	}

	/**
	 * 记录DEBUG信息
	 * 
	 * @param str
	 *            日志信息
	 *            <p/>
	 *            替换占位符的参数，如果没有该参数则不会替换
	 */
	public void debug(String str) {
		LogHelper.debug(this,getClassAndlin(str));
	}

	/**
	 * 记录INFO信息
	 * 
	 * @param str
	 *            日志信息
	 */
	public void info(String str) {
		LogHelper.info(this,getClassAndlin(str));
	}

	/**
	 * 记录warn信息
	 * 
	 * @param str
	 *            日志信息
	 */
	public void warn(String str) {
		LogHelper.warn(this,getClassAndlin(str));
	}

	/**
	 * 记录error信息
	 * 
	 * @param str
	 *            日志业务类型
	 * @param str
	 */
	public void error(String str) {
		LogHelper.error(this,getClassAndlin(str));
	}

	/**
	 * 记录fatal信息
	 * 
	 * @param str
	 *            日志信息
	 */
	public void fatal(String str) {
		LogHelper.fatal(this,getClassAndlin(str));
	}

	/**
	 * 记录运行期错误信息
	 * 
	 * @param e
	 */
	public void execption(Throwable e) {
		LogTypeEnum.EXCEPTION.error(getExceptionTrace(e));
	}

	/**
	 * 打印错误信息
	 * 
	 * @param e
	 * @return
	 */
	public String getExceptionTrace(Throwable e) {
		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString();
		} else
			return null;
	}

}
