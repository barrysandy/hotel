package com.zzk.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * 
* 字符串处理类
* @author lishiqiang
* @date 2017-3-15
* modify history
 */
public class StringUtil {
	private static String string_gbk = "GBK";
	private static final char[] digits = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z' };

	public static final String getGBString(String str) {
		String str2 = "";
		try {
			str2 = new String(str.getBytes("iso-8859-1"), string_gbk);
		} catch (Exception e) {
			e.printStackTrace();
			str2 = "";
		}
		return str2;
	}

	public static String getNullString(HashMap hm, String hname)
			throws Exception {
		if (hm == null)
			throw new Exception("Input HashMap is null");

		if ((hm.get(hname) == null) || (" ".equals(hm.get(hname))))
			return "";

		return ((String) hm.get(hname.toUpperCase()));
	}

	public static String getNullString(Object o, String replace) {
		return getNullString(o, " ", replace);
	}

	public static String getNullString(Object o, String nullString,
			String replace) {
		if (o == null)
			return replace;
		if (("".equals(o)) || (nullString.equals(o)))
			return replace;
		return o.toString();
	}

	public static String getNullString(Object str, int len) {
		return getNullString(str, len, "");
	}

	public static String getHtmlShow(Object str, int len, String replacestr) {
		if (str == null)
			return replacestr;

		String str1 = (String) str;
		int strlen = str1.length();
		if (strlen >= len)
			return str1.substring(0, len) + "...";

		return str1;
	}

	public static String getNullString(Object str, int len, String replacestr) {
		if (str == null)
			return replacestr;

		String str1 = (String) str;
		int strlen = str1.length();
		if (strlen >= len)
			return str1.substring(0, len);

		return str1;
	}

	public static int getNullInt(Object str, int defaultint) {
		int a;
		try {
			a = Integer.parseInt((String) str);
			return a;
		} catch (NumberFormatException e) {
		}
		return defaultint;
	}

	public static String getRight(String str, int len) {
		if (str == null)
			return null;
		int strlen = str.length();
		if (len < strlen)
			return str.substring(strlen - len, strlen);

		return str;
	}

	public static String getRight(String str, int len, String rep)
			throws Exception {
		if (str == null)
			return null;
		int strlen = str.length();
		if (len < strlen)
			return str.substring(strlen - len, strlen);

		if (rep == null)
			throw new Exception("SU001: replace string is null !!");
		StringBuffer str1 = new StringBuffer();
		for (; strlen < len; ++strlen)
			str1.append(rep);

		str1.append(str);
		return str1.toString();
	}

	public static String repSQLChar(String s) {
		if (s == null)
			return "";
		return s.replace('\'', ' ');
	}

	public static String getCurrClassPath() {
		String str = "";
		try {
			str = StringUtil.class.getProtectionDomain().getCodeSource()
					.toString();
			str = str.substring(str.indexOf("/"), str.lastIndexOf("/"));
		} catch (Exception ex) {
			return null;
		}
		return str;
	}

	public static String getClearCommaStr(String str) {
		if (str == null) {
			return "";
		}

		if (str.lastIndexOf(",") != -1)
			str = str.substring(0, str.lastIndexOf(","));

		return str;
	}

	public static String SQLnullString(String sv) {
		if (sv == null)
			return " ";

		sv.replaceAll("'", "  ");
		return sv;
	}

	public static String StringBlank(String blank) {
		char c;
		if (blank == null)
			return " ";

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < blank.length(); ++i) {
			c = blank.charAt(i);
			switch (c) {
			case ' ':
				sb.append(' ');
				break;
			default:
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String StringBlankAll(String blank) {
		if (blank == null)
			return "";

		blank = StringBlank(blank);
		return blank.trim();
	}

	public static String SQLnullInt(String sv) {
		if (sv == null)
			return "0";

		return sv;
	}

	public static String SQLnullDate(String sv) {
		if (sv == null)
			return "1900-1-1";

		return sv;
	}

	public static boolean isNull(String str) {
		return ((str == null) || (str.length() == 0));
	}

	public static boolean isNotNull(String str) {
		return (!(isNull(str)));
	}

	public static boolean isEquals(String str1, String str2) {
		return str1.equalsIgnoreCase(str2);
	}

	public static String replace(String mainString, String oldString,
			String newString) {
		if (mainString == null)
			return null;

		if ((oldString == null) || (oldString.length() == 0))
			return mainString;

		if (newString == null) {
			newString = "";
		}

		int i = mainString.lastIndexOf(oldString);

		if (i < 0) {
			return mainString;
		}

		StringBuffer mainSb = new StringBuffer(mainString);

		while (i >= 0) {
			mainSb.replace(i, i + oldString.length(), newString);
			i = mainString.lastIndexOf(oldString, i - 1);
		}
		return mainSb.toString();
	}

	public static String replaceFirst(String mainString, String oldString,
			String newString) {
		if (mainString == null)
			return null;

		if ((oldString == null) || (oldString.length() == 0))
			return mainString;

		if (newString == null) {
			newString = "";
		}

		int i = mainString.lastIndexOf(oldString);

		if (i < 0) {
			return mainString;
		}

		StringBuffer mainSb = new StringBuffer(mainString);

		if (i >= 0)
			mainSb.replace(i, i + oldString.length(), newString);

		return mainSb.toString();
	}

	public static String join(List list, String delim) {
		if ((list == null) || (list.size() < 1))
			return null;

		StringBuffer buf = new StringBuffer();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			buf.append((String) i.next());
			if (i.hasNext())
				buf.append(delim);
		}

		return buf.toString();
	}

	public static List splitList(String str, String delim) {
		List splitList = null;
		StringTokenizer st = null;

		if (str == null) {
			return splitList;
		}

		if (delim != null)
			st = new StringTokenizer(str, delim);
		else
			st = new StringTokenizer(str);

		if ((st != null) && (st.hasMoreTokens())) {
			splitList = new ArrayList();

			while (st.hasMoreTokens())
				splitList.add(st.nextToken());
		}

		return splitList;
	}

	public static String[] split(String source, char delim) {
		return split(source, String.valueOf(delim));
	}

	public static String[] split(String source) {
		return split(source, ",");
	}

	public static int[] splitInt(String source) {
		return splitInt(source, ",");
	}

	public static int[] splitInt(String source, String type) {
		String[] s = split(source, type);
		return getInt(s);
	}

	public static int[] getInt(String[] s) {
		int[] i = new int[s.length];
		for (int m = 0; m < s.length; ++m)
			i[m] = getInt(s[m], 0);

		return i;
	}

	public static String ChangeToInsqlType(String source, String delim) {
		String s = "";
		String[] l = split(source, delim);
		for (int k = 0; k < l.length; ++k) {
			s = s + "'" + l[k] + "'";
			if (k != l.length - 1)
				s = s + ",";
		}
		return s;
	}

	public static Map strToMap(String str) {
		if (str == null)
			return null;

		Map decodedMap = new HashMap();
		List elements = splitList(str, "?");
		Iterator i = elements.iterator();

		while (i.hasNext()) {
			String s = (String) i.next();
			List e = splitList(s, "=");
			if (e.size() != 2)
				continue;

			String name = (String) e.get(0);
			String value = (String) e.get(1);

			decodedMap.put(URLDecoder.decode(name), URLDecoder.decode(value));
		}
		return decodedMap;
	}

	public static String mapToStr(Map map) {
		if (map == null)
			return null;

		StringBuffer buf = new StringBuffer();
		Set keySet = map.keySet();
		Iterator i = keySet.iterator();
		boolean first = true;

		while (i.hasNext()) {
			Object key = i.next();
			Object value = map.get(key);

			if (key instanceof String) {
				if (!(value instanceof String))
					continue;

				String encodedName = URLEncoder.encode((String) key);
				String encodedValue = URLEncoder.encode((String) value);

				if (first)
					first = false;
				else
					buf.append("?");

				buf.append(encodedName);
				buf.append("=");
				buf.append(encodedValue);
			}
		}
		return buf.toString();
	}

	public static String removeSpaces(String str) {
		StringBuffer newString = new StringBuffer();
		for (int i = 0; i < str.length(); ++i)
			if (str.charAt(i) != ' ')
				newString.append(str.charAt(i));

		return newString.toString();
	}

	public static boolean contains(String MainString, String containedString) {
		int i = MainString.indexOf(containedString);

		return (i != -1);
	}

	public static int contains(String[] mainStrings, String containedString,
			boolean caseSensitive) {
		for (int i = 0; i < mainStrings.length; ++i) {
			if (caseSensitive) {
				if (!(mainStrings[i].equals(containedString)))
					continue;
				return i;
			}

			if (mainStrings[i].equalsIgnoreCase(containedString))
				return i;

		}

		return -1;
	}

	public static String getString(String srcString, String key, int length,
			String postfix) {
		if ((srcString == null) || (srcString.trim().length() == 0))
			return "";
		String returnStr = "";
		int index = srcString.indexOf(key);
		if (index > 0) {
			returnStr = srcString.substring(0, index) + postfix;
		} else if (srcString.length() > length)
			returnStr = srcString.substring(0, length) + postfix;
		else {
			returnStr = srcString;
		}

		returnStr = returnStr.replaceAll("<P>", "");
		returnStr = returnStr.replaceAll("<p>", "");
		returnStr = returnStr.replaceAll("</P>", "");
		returnStr = returnStr.replaceAll("</p>", "");
		returnStr = returnStr.replaceAll("<BR>", "");
		returnStr = returnStr.replaceAll("<br>", "");
		return returnStr;
	}

	public static int contains(String[] mainStrings, String containedString) {
		return contains(mainStrings, containedString, true);
	}

	public static int containsIgnoreCase(String[] mainStrings,
			String containedString) {
		return contains(mainStrings, containedString, false);
	}

	public static String getRandomString(int length) {
		String strRnd = "";
		for (int i = 0; i < length; ++i) {
			int temp = new Double(Math.random() * 997.0D).intValue()
					% digits.length;
			strRnd = strRnd + String.valueOf(digits[temp]);
		}
		return strRnd;
	}

	public static String toChinese(String str) {
		try {
			if (str == null)
				return null;

			str = new String(str.getBytes("ISO8859_1"), "GBK");
			return str;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public static String toUTF(String str) {
		try {
			if (str == null)
				return null;

			str = new String(str.getBytes("GB2312"), "utf-8");
			return str;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			if ((c >= 0) && (c <= 255)) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; ++j) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public static String UnicodeToGB(String strIn) {
		String strOut = null;
		if ((strIn == null) || (strIn.trim().equals("")))
			return strIn;
		try {
			byte[] b = strIn.getBytes("GB2312");
			strOut = new String(b, "ISO8859_1");
		} catch (Exception e) {
			System.out.println("unicodeToGB exception : " + e.getMessage()
					+ "\n");
		}
		return strOut;
	}

	public static String GBToUnicode(String strIn) {
		String strOut = null;
		if ((strIn == null) || (strIn.trim().equals("")))
			return strIn;
		try {
			byte[] b = strIn.getBytes("ISO8859_1");
			strOut = new String(b, "GB2312");
		} catch (Exception e) {
			System.out.println("GBToUnicode exception : " + e.getMessage()
					+ "\n");
		}
		return strOut;
	}

	public static String transCode(String value, String enc) {
		try {
			if (value == null)
				return null;

			value = value.trim();
			value = new String(value.getBytes("ISO8859_1"), enc);
			return value;
		} catch (Exception e) {
		}
		return null;
	}

	public static String toChineseNumber(String number) {
		String[] num = { };
		String[] unt = {  };
		String value = "";
		int i = 0;
		for (int j = number.length(); i < number.length();) {
			value = value
					+ num[Integer.parseInt(String.valueOf(number.charAt(i)))]
					+ unt[j];

			--j;
			++i;
		}

		value = value + number.substring(number.length());
		if ((value.substring(0, 1) == "һ") && (value.length() == 3))
			value = value.substring(1, value.length());

		return value;
	}

	public static String toChineseNumber(int number) {
		return toChineseNumber(String.valueOf(number));
	}

	public static void printStrings(String[] strings, String delim,
			OutputStream out) {
		try {
			if (strings != null) {
				int length = strings.length - 1;
				for (int i = 0; i < length; i++) {
					if (strings[i] != null) {
						if (strings[i].indexOf(delim) > -1) {
							out.write(("\"" + strings[i] + "\"" + delim)
									.getBytes());
						} else {
							out.write((strings[i] + delim).getBytes());
						}
					} else {
						out.write("null".getBytes());
					}
				}
				if (strings[length] != null) {
					if (strings[length].indexOf(delim) > -1) {
						out.write(("\"" + strings[length] + "\"").getBytes());
					} else {
						out.write(strings[length].getBytes());
					}
				} else {
					out.write("null".getBytes());
				}
			} else {
				out.write("null".getBytes());
			}
		} catch (IOException e) {

		}
	}

	public static void printStrings(String[] strings, String delim) {
		printStrings(strings, delim, System.out);
	}

	public static void printStrings(String[] strings, OutputStream out) {
		printStrings(strings, ",", out);
	}

	public static void printStrings(String[] strings) {
		printStrings(strings, ",", System.out);
	}

	public static int getStringLength(String str) {
		return str.length();
	}

	public static String getString(String str) {
		if (str == null)
			return "";

		return str;
	}

	public static String getString(String str, String defValue) {
		if ((str == null) || ("".equals(str)) || ("null".equals(str))
				|| ("NULL".equals(str)))
			return defValue;

		return str;
	}

	public static String getString(Map m, String para) {
		return getString(m.get(para.toUpperCase()), "");
	}

	public static String getString(Map m, String para, String def) {
		return getString(m.get(para.toUpperCase()), def);
	}

	public static String getString1(Map m, String para, String def) {
		if (m == null)
			return def;
		return getString(m.get(para), def);
	}

	public static String getString(Object str, String defValue) {
		return getString(String.valueOf(str), defValue);
	}

	public static String substring(String str, int count1) {
		int count = count1 / 2;

		if (str == null)
			return "";
		if (str.length() <= count)
			return str;

		return str.substring(0, count) + "..";
	}

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

	public static String substringWithAuthor(String str, int count1,
			String author) {
		int count = count1 / 2;
		if (str == null)
			return "";
		String s = str + "(" + author + ")";
		if (s.length() <= count)
			return str + "(" + author + ")";

		return str.substring(0, count) + "(" + author + ")..";
	}

	public static String substringstr(String str, int count1) {
		int count = count1 / 2;
		if (str == null)
			return "";
		if (str.length() <= count)
			return str;

		return str.substring(0, count);
	}

	public static int getInt(String str, int defaultValue) {
		int returnInt = defaultValue;
		try {
			returnInt = Integer.parseInt(str);
		} catch (Exception e) {
			returnInt = defaultValue;
		}
		return returnInt;
	}

	public static long getLong(String str, long defaultValue) {
		long returnInt = defaultValue;
		try {
			returnInt = Long.parseLong(str);
		} catch (Exception e) {
			returnInt = defaultValue;
		}
		return returnInt;
	}

	public static double getDouble(String str, double defaultValue) {
		double returnInt = defaultValue;
		try {
			returnInt = Double.parseDouble(str);
		} catch (Exception e) {
			returnInt = defaultValue;
		}
		return returnInt;
	}

	public static boolean isBeginWith(String sourceString, String keyString) {
		int index = sourceString.indexOf(keyString);

		return (index == 0);
	}

	public static boolean isEndWith(String sourceString, String keyString) {
		int index = sourceString.lastIndexOf(keyString);

		return (index == sourceString.length() - keyString.length());
	}

	public static String getFileSize(int fileSize) {
		return getFileSize(fileSize);
	}

	public static String getFileSize(long fileSize) {
		String reStr = "";
		long Imb = 7005603615582715904L;
		long temp = 7005603615582715904L;
		long Ikb = 7005603615582715904L;
		long Ib = 7005603615582715904L;
		if (fileSize > 1073741824L) {
			long Igb = fileSize / 1073741824L;
			temp = fileSize % 1073741824L * 100L / 1073741824L;
			reStr = Igb + "." + temp + "GB";
		} else if (fileSize > 1048576L) {
			Imb = fileSize / 1048576L;
			temp = fileSize % 1048576L * 100L / 1048576L;
			reStr = Imb + "." + temp + "MB";
		} else if (fileSize > 1024L) {
			Ikb = fileSize / 1024L;
			reStr = Ikb + "KB";
		} else {
			Ib = fileSize;
			reStr = Ib + " ";
		}
		return reStr;
	}

	public static String getLastIndexOfString(String source, String key) {
		int k = source.lastIndexOf(key);
		return source.substring(k + 1, source.length());
	}

	public static String AppendStr(String str1, String str2, int count,
			boolean right) {
		String newStr = "";
		for (int i = 0; i < count - str1.length(); ++i)
			newStr = newStr + str2;
		if (right)
			return str1 + newStr;

		return newStr + str1;
	}

	public static String getReplaceString(String source, String[] values) {
		return getReplaceString("%", source, values);
	}

	public static String getReplaceString(String prefix, String source,
			String[] values) {
		String result = source;
		if ((source == null) || (values == null) || (values.length < 1))
			return source;

		if (prefix == null) {
			prefix = "%";
		}

		for (int i = 0; i < values.length; ++i) {
			String argument = prefix + Integer.toString(i + 1);
			int index = result.indexOf(argument);
			if (index != -1) {
				String temp = result.substring(0, index);
				if (i < values.length)
					temp = temp + values[i];
				else
					temp = temp + values[(values.length - 1)];

				temp = temp + result.substring(index + 2);
				result = temp;
			}
		}
		return result;
	}

	public static String filterStackTrace(Throwable e, String[] filterPackages)
			throws IOException {
		String s1;
		if (e == null)
			return "No exception founded";
		StringWriter expout = new StringWriter();
		StringBuffer out = new StringBuffer();
		e.printStackTrace(new PrintWriter(expout));
		BufferedReader br = new BufferedReader(new StringReader(expout
				.toString()));

		while ((s1 = br.readLine()) != null) {
			boolean f = false;
			for (int i = 0; i < filterPackages.length; ++i) {
				if (s1.indexOf(filterPackages[i]) == -1)
					continue;
				f = true;
				break;
			}
			if (!(f)) {
				out.append(s1);
				out.append("\n");
			}
		}
		return out.toString();
	}

	public static String toString(String[] main, String s) {
		if ((main == null) || (main.length == 0))
			return "";
		String returnStr = s;
		for (int i = 0; i < main.length; ++i)
			returnStr = returnStr + main[i] + s;

		return returnStr;
	}

	public static String trim(String s) {
		if (s == null)
			s = "";
		return s.trim();
	}

	public static String trimBeginEnd(String str, String ts) {
		if (str == null)
			return "";
		do
			str = str.substring(ts.length(), str.length());
		while (str.startsWith(ts));
		for (; str.endsWith(ts); str = str.substring(0, str.length()
				- ts.length()))
			;
		return str;
	}

	public static String trimBeginEndBlank(String s) {
		if (s == null)
			return "";
		s = s.trim();
		s = trimBeginEnd(s, " ");
		s = trimBeginEnd(s, "  ");
		return s;
	}

	public static String convertEncoding(String source, String sencoding,
			String dencoding) {
		if (sencoding == null)
			sencoding = "gb2312";

		if (sencoding.equals(dencoding))
			return source;
		try {
			return new String(source.getBytes(sencoding), dencoding);
		} catch (UnsupportedEncodingException ex) {
		}
		return source;
	}

	public static String CLobToStr(Clob clob) throws Exception {
		String strContent = "";
		if (clob != null) {
			Reader is = clob.getCharacterStream();
			BufferedReader br = new BufferedReader(is);
			for (String s = br.readLine(); s != null; s = br.readLine())
				strContent = strContent + s + "\r\n";
		}

		return strContent;
	}

	public static String CLobToString(Clob clob) throws Exception {
		String strContent = "";
		if (clob != null) {
			Reader is = clob.getCharacterStream();
			BufferedReader br = new BufferedReader(is);
			for (String s = br.readLine(); s != null; s = br.readLine())
				strContent = strContent + s;
		}

		return strContent;
	}
	

    /**
     * 把输入的金额转换为汉语中人民币的大写
     * 
     * @param numberOfMoney
     *            输入的金额
     * @return 对应的汉语大写
     */
    public static String number2CNMontrayUnit(BigDecimal numberOfMoney) {
    	String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆",
                "伍", "陆", "柒", "捌", "玖" };
    	 /**
         * 汉语中货币单位大写，这样的设计类似于占位符
         */
        String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元",
                "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
                "佰", "仟" };
        /**
         * 特殊字符：整
         */
       String CN_FULL = "整";
        /**
         * 特殊字符：负
         */
       String CN_NEGATIVE = "负";
        /**
         * 金额的精度，默认值为2
         */
       int MONEY_PRECISION = 2;
        /**
         * 特殊字符：零元整
         */
        String CN_ZEOR_FULL = "零元" + CN_FULL;
        
        StringBuffer sb = new StringBuffer();
        // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
        // positive.
        int signum = numberOfMoney.signum();
        // 零元整的情况
        if (signum == 0) {
            return CN_ZEOR_FULL;
        }
        //这里会进行金额的四舍五入
        long number = numberOfMoney.movePointRight(MONEY_PRECISION)
                .setScale(0, 4).abs().longValue();
        // 得到小数点后两位值
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            // 每次获取到最后一个数
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // 让number每次都去掉最后一个数
            number = number / 10;
            ++numIndex;
        }
        // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
        if (!(scale > 0)) {
            sb.append(CN_FULL);
        }
        return sb.toString();
	}
	    
    
    
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String EMPTY = "";
    public static final String UNDER_LINE = "_";
    public static final String ENDL = "\n";
    public static final String SLASH = "/";
    public static final String BLANK = " ";
    public static final String DOT = ".";
    public static final String FILE_SEPARATOR = File.separator;

    public static String encodePassword(String paramString)
    {
      String str = null;
      try
      {
        str = encodePassword(paramString, "MD5");
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        localNoSuchAlgorithmException.printStackTrace();
      }
      return str;
    }

    public static String changeList2String(List<String> paramList)
    {
      String str = paramList.toString();
      return str.substring(1, str.length() - 1);
    }

    public static boolean isBlank(String paramString)
    {
      int i = 0;
      if ((paramString == null) || ("".equals(paramString.trim())))
        i = 1;
      if(i==1)
      {
      	return true;
      }else return false;
    }

    public static boolean isNotBlank(String paramString)
    {
      int i = 0;
      if ((paramString != null) && (!("".equals(paramString))))
        i = 1;
      if(i==1)
      {
      	return true;
      }else return false;
    }

    public static String encodePassword(String paramString1, String paramString2)
      throws NoSuchAlgorithmException
    {
      byte[] arrayOfByte1 = paramString1.getBytes();
      MessageDigest localMessageDigest = null;
      localMessageDigest = MessageDigest.getInstance(paramString2);
      localMessageDigest.reset();
      localMessageDigest.update(arrayOfByte1);
      byte[] arrayOfByte2 = localMessageDigest.digest();
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 0; i < arrayOfByte2.length; ++i)
      {
        if ((arrayOfByte2[i] & 0xFF) < 16)
          localStringBuffer.append("0");
        localStringBuffer.append(Long.toString(arrayOfByte2[i] & 0xFF, 16));
      }
      return localStringBuffer.toString();
    }

      public static boolean isEmpty(Object s){

  		return s == null || s.toString().trim().length() == 0 || s.toString().trim().equalsIgnoreCase("null");

  	}

    public static String base64Encode(String paramString)
    {
      BASE64Encoder localBASE64Encoder = new BASE64Encoder();
      return localBASE64Encoder.encodeBuffer(paramString.getBytes()).trim();
    }

    public static String base64Decode(String paramString)
    {
      BASE64Decoder localBASE64Decoder = new BASE64Decoder();
      try
      {
        return new String(localBASE64Decoder.decodeBuffer(paramString));
      }
      catch (IOException localIOException)
      {
        throw new RuntimeException(localIOException.getMessage(), localIOException.getCause());
      }
    }


    public static String ljustZero(String paramString, int paramInt)
    {
      String str = "";
      for (int i = 0; i < paramInt - paramString.length(); ++i)
        str = str + "0";
      str = str + paramString;
      return str;
    }

    public static int getWordLength(String paramString)
    {
      int i = 0;
      if (isBlank(paramString))
        return i;
      char[] arrayOfChar = paramString.toCharArray();
      for (int j = 0; j < arrayOfChar.length; ++j)
        if (isChinese(arrayOfChar[j]))
          i += 2;
        else
          i += 1;
      return i;
    }

    public static String getWord(String paramString, int paramInt)
    {
      char[] arrayOfChar = paramString.toCharArray();
      StringBuffer localStringBuffer = new StringBuffer();
      int i = 0;
      for (int j = 0; j < arrayOfChar.length; ++j)
      {
        if (isChinese(arrayOfChar[j]))
          i += 2;
        else
          i += 1;
        if (i > paramInt)
          break;
        localStringBuffer.append(arrayOfChar[j]);
      }
      return localStringBuffer.toString();
    }

    public static boolean hasChinese(String paramString)
    {
      if (paramString == null)
        return true;
      char[] arrayOfChar = paramString.toCharArray();
      for (int i = 0; i < arrayOfChar.length; ++i)
        if (isChinese(arrayOfChar[i]))
          return true;
      return false;
    }

    public static boolean isChinese(char paramChar)
    {
      Character.UnicodeBlock localUnicodeBlock = Character.UnicodeBlock.of(paramChar);
      return ((localUnicodeBlock != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) && (localUnicodeBlock != Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) && (localUnicodeBlock != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) && (localUnicodeBlock != Character.UnicodeBlock.GENERAL_PUNCTUATION) && (localUnicodeBlock != Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) && (localUnicodeBlock != Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS));
    }

    public static String[] splitIntoArr(String paramString)
    {
      if (isEmpty(paramString))
        return new String[0];
      return paramString.split(",");
    }

    public static String join(String[] paramArrayOfString)
    {
      return join(paramArrayOfString, ",", "'");
    }

    public static String join(String[] paramArrayOfString, String paramString)
    {
      return join(paramArrayOfString, ",", "");
    }

    public static String join(String[] paramArrayOfString, String paramString1, String paramString2)
    {
      String str = "";
      for (int i = 0; i < paramArrayOfString.length; ++i)
      {
        str = str + paramString2 + paramArrayOfString[i] + paramString2;
        if (i == paramArrayOfString.length - 1)
          continue;
        str = str + paramString1;
      }
      return str;
    }

    public static String convertCode(String paramString1, String paramString2, String paramString3)
    {
      try
      {
        if (paramString1 == null)
          return null;
        if (paramString1.equals(""))
          return "";
        return new String(paramString1.getBytes(paramString2), paramString3);
      }
      catch (Exception localException)
      {
      	return localException.toString();
      }
      
    }

    public static boolean isUpperCase(String paramString)
    {
      int i = 1;
      for (int k = 0; k < paramString.length(); ++k)
      {
        int j = paramString.charAt(k);
        if ((j >= 65) && (j <= 90))
          continue;
        i = 0;
      }
      if(i==1)
      {
      	return true;
      }else return false;
    }

    public static boolean isLowerCase(String paramString)
    {
      int i = 1;
      for (int k = 0; k < paramString.length(); ++k)
      {
        int j = paramString.charAt(k);
        if ((j >= 97) && (j <= 122))
          continue;
        i = 0;
      }
      if(i==1)
      {
      	return true;
      }else return false;
    }

    	public static String null2String(Object s)
  	{
  		return s == null ? "" : s.toString();
  	}
      public static Integer null2Integer0(Object o)
  	{
          if(o!=null){
              String str=o.toString().trim();
              return ("null".equalsIgnoreCase(str)||str.length()==0) ? Integer.valueOf(0) : Integer.valueOf(str);
          } else return Integer.valueOf(0) ;
  	}
      public static Long null2Long0(Object o)
  	{
          if(o!=null){
              String str=o.toString().trim();
              return ("null".equalsIgnoreCase(str)||str.length()==0) ? Long.valueOf(0) : Long.valueOf(str);
          } else return Long.valueOf(0) ;
  	}
      
      public static double null2Double0(Object o)
  	{
          if(o!=null){
              String str=o.toString().trim();
              return ("null".equalsIgnoreCase(str)||str.length()==0) ? Double.valueOf(0) : Double.valueOf(str);
          } else return Long.valueOf(0) ;
  	}
      
      public static double round(double v,int scale)
      {
      	if(scale<0){
      	throw new IllegalArgumentException("The scale must be a positive integer or zero");
      }

      	BigDecimal b = new BigDecimal(Double.toString(v));
      	BigDecimal one = new BigDecimal("1");
      	return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
      }
      
      public static final String replaceAll(String src, String fnd, String rep) {
  		if (src == null || src.equals("")) {
  			return "";
  		}

  		String dst = src;

  		int idx = dst.indexOf(fnd);

  		while (idx >= 0) {
  			dst = dst.substring(0, idx) + rep
  					+ dst.substring(idx + fnd.length(), dst.length());
  			idx = dst.indexOf(fnd, idx + rep.length());
  		}

  		return dst;
  	}
      
      /**
  	 * 文本转html
  	 * 
  	 * @param txt
  	 * @return
  	 */
  	public static String txt2htm(String txt) {
  		if (org.apache.commons.lang.StringUtils.isBlank(txt)) {
  			return txt;
  		}
  		StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
  		char c;
  		for (int i = 0; i < txt.length(); i++) {
  			c = txt.charAt(i);
  			switch (c) {
  			case '&':
  				sb.append("&amp;");
  				break;
  			case '<':
  				sb.append("&lt;");
  				break;
  			case '>':
  				sb.append("&gt;");
  				break;
  			case '"':
  				sb.append("&quot;");
  				break;
  			case ' ':
  				sb.append("&nbsp;");
  				break;
  			case '\n':
  				sb.append("<br/>");
  				break;
  			default:
  				sb.append(c);
  				break;
  			}
  		}
  		return sb.toString();
  	}

  	/**
  	 * html转文本
  	 * 
  	 * @param htm
  	 * @return
  	 */
  	public static String htm2txt(String htm) {
  		if (htm == null) {
  			return htm;
  		}
  		htm = htm.replace("&amp;", "&");
  		htm = htm.replace("&lt;", "<");
  		htm = htm.replace("&gt;", ">");
  		htm = htm.replace("&quot;", "\"");
  		htm = htm.replace("&nbsp;", " ");
  		htm = htm.replace("<br/>", "\n");
  		return htm;
  	}

  public static String removeSqlComment(String sqlText){
      Pattern p = Pattern.compile("(?ms)('(?:''|[^'])*')|--.*?$|/\\*.*?\\*/");
      String presult = p.matcher(sqlText).replaceAll("$1");
      return presult;
  }
  /**
   * 去回车 空格 制表符等
   * <p>description:</p>
   * @param 
   * @return String
   * @author Wen Yugang
   * @date 2017-6-12下午4:37:52
   */
  public static String replaceBlank(String str) {
	String dest = "";
	if (str!=null) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		dest = m.replaceAll("");
	}
	return dest;
}
}