//package mq.utils;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//
//public class DateUtil {
//	public static final String DAY_PATTERN = "yyyy-MM-dd";
//	public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
//	public static final String DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
//
//	/**
//	 * 根据一个当前日期和需要添加的月数得到一个卡的有效期（为当前日期加上月后的日期的月的最后一天）
//	 *
//	 * @param date
//	 * @param monthCount
//	 * @return
//	 */
//	public static Date countCardValidate(Date date, int monthCount) {
//		Calendar maxDate = Calendar.getInstance();
//		maxDate.set(Calendar.YEAR, 2099);
//		maxDate.set(Calendar.MONTH, 12);
//		maxDate.set(Calendar.DATE, 31);
//		long maxTime = maxDate.getTimeInMillis();
//		GregorianCalendar gregorianCalendar = new GregorianCalendar();
//		gregorianCalendar.setTime(date);
//		gregorianCalendar.add(GregorianCalendar.MONDAY, monthCount+1);
//		Date lastDate = gregorianCalendar.getTime();
//		if(maxTime>=gregorianCalendar.getTime().getTime()){
//			date = setMonthLastDay(lastDate);
//		}else{
//			date = setMonthLastDay(maxDate.getTime());
//		}
//		return date;
//	}
//
//	/**
//	 * 根据一个DATE取该日期月的最后一天
//	 *
//	 * @param date
//	 * @return
//	 */
//	public static Date setMonthLastDay(Date date) {
//		GregorianCalendar gregorianCalendar = new GregorianCalendar();
//		gregorianCalendar.setTime(date);
//		gregorianCalendar.set(GregorianCalendar.DATE, 1);
//		gregorianCalendar.add(GregorianCalendar.DATE, -1);
//		date = gregorianCalendar.getTime();
//		return date;
//	}
//	/**
//	 * 根据传入的字符串时间格式成yyyy-MM-dd
//	 * @param date
//	 * @return
//	 */
//	public static String formatStringDate(String date){
//		if(date==null) return null;
//		return date.substring(0, 4)+"-"+date.substring(4, 6)+"-"+date.substring(6, 8);
//	}
//	/**
//	 * 根据传入的字符串时间格式成yyyy-MM-dd
//	 * @param date
//	 * @return
//	 */
//	public static String formatStringTime(String time){
//		if(time==null) return null;
//		return time.substring(0, 2)+":"+time.substring(2, 4)+":"+time.substring(4, 6);
//	}
//	/**
//	 * 根据传入的字符串时间格式成yyyyMMdd
//	 * @param date
//	 * @return
//	 */
//	public static String StringDate(String date){
//		if(date==null) return null;
//		return date.substring(0, 4)+date.substring(5,7)+date.substring(8, 10);
//	}
//	/**
//	 * 根据传入的字符串时间格式成yyyyMMdd
//	 * @param date
//	 * @return
//	 */
//	public static String StringTime(String date){
//		if(date==null) return null;
//		return date.substring(11, 13)+date.substring(14,16)+date.substring(17, 19);
//	}
//	/**
//	 * 获取当前时间
//	 */
//	public static String getCurrentTime(){
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//		return simpleDateFormat.format(new Date());
//	}
//
//	/**
//	 * 获取当前时间 24
//	 * @return
//	 */
//	public static String getCurrentTime24(){
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//		return simpleDateFormat.format(new Date());
//	}
//
//	/**
//	 * 获取当前时间 24
//	 * @return
//	 */
//	public static String getCurrentTime24SSS(){
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		return simpleDateFormat.format(new Date());
//	}
//
//	/**
//	 * Date型的时间转换成String型的格式为：yyyyMMdd
//	 * @return
//	 */
//	public static String getCurrentDateStr(){
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//		return simpleDateFormat.format(new Date());
//	}
//
//	/**
//	 * 返回指定日期格式的字符串 如：yyyyMMdd,yyyyMMddhhmmss
//	 * @param format
//	 * @return
//	 */
//	public static String getCurrentDateFormatStr(String format){
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
//		return simpleDateFormat.format(new Date());
//	}
//
//    /**
//     * Date型的时间转换成String型的格式为：yyyy-MM-dd
//     * @return
//     */
//
//	public static String getStringDate(){
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		return simpleDateFormat.format(new Date());
//	}
//
//	/**
//	 * 获取当前日期
//	 */
//	public static Date getCurrentDate(){
//
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//		String curDate=simpleDateFormat.format(new Date());
//
//		try {
//			return simpleDateFormat.parse(curDate);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	/**
//	 * 获得当前日期和时间
//	 *
//	 * @return
//	 */
//	public static Date getCurrentDateAndTime(){
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
//		String curDate=simpleDateFormat.format(new Date());
//
//		try {
//			return simpleDateFormat.parse(curDate);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 获取格式后的数据 ，返回yyyyMMdd
//	 */
//	public static String getFormatTime(String Date){
//		if(null ==Date){
//			return Date;
//		}
//		if(Date.equals("")){
//			return "";
//		}
//		StringBuffer strBuf = new StringBuffer();
//		strBuf.append(Date.substring(0,4));
//		strBuf.append(Date.substring(5,7));
//		strBuf.append(Date.substring(8,10));
//	    return strBuf.toString();
//	}
//
//
//
//
//	public static String dbFormatToDateFormat(String dbFormat){
//		if(dbFormat!=null&&!"".equals(dbFormat)&&dbFormat.trim().length()>=8){
//			StringBuffer strBuf = new StringBuffer(dbFormat.substring(0, 4));
//			strBuf.append("-");
//			strBuf.append(dbFormat.substring(4, 6));
//			strBuf.append("-");
//			strBuf.append(dbFormat.substring(6,8));
//			strBuf.append(dbFormat.substring(8));
//			return strBuf.toString();
//		}
//		return dbFormat;
//	}
//
//	/**
//     * Date日期转换成String
//     *
//     */
//    public static final String date2String(Date date) {
//        if (date == null) {
//            return "";
//        }
//        DateFormat df = new SimpleDateFormat("yyyyMMdd");
//        return df.format(date);
//    }
//
//    /***
//     * Date日期转成带-String
//     */
//    public static final String dateToSting_(Date date) {
//        if (date == null) {
//            return "";
//        }
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        return df.format(date);
//    }
//
//
//    /**
//     * String日期转换成Date
//     *
//     */
//    public static final Date string2date(String dateStr) {
//    	if (dateStr == null || dateStr.length() == 0)
//			return null;
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			return df.parse(dateStr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//			return null;
//		}
//    }
//
//    /**
//     * String日期转换成Date
//     *
//     */
//    public static Date string2date(String dateStr, String format) {
//        if (dateStr == null || dateStr.length() == 0)
//            return null;
//        DateFormat df = new SimpleDateFormat(format);
//        try {
//            return df.parse(dateStr);
//        } catch (ParseException e) {
//            return null;
//        }
//    }
//    /**
//     * String日期转换成Date(yyyymmdd)
//     *
//     */
//    public static final Date string2Dateyyyymmdd(String dateStr) {
//    	if (dateStr == null || dateStr.length() == 0)
//			return null;
//		DateFormat df = new SimpleDateFormat("yyyyMMdd");
//		try {
//			return df.parse(dateStr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//			return null;
//		}
//    }
//    /**
//     * String日期转换成Date(yyyymmddhhmmss)
//     *
//     */
//    public static final Date string2Dateyyyymmddhhmmss(String dateStr) {
//    	if (dateStr == null || dateStr.length() == 0)
//			return null;
//		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//		try {
//			return df.parse(dateStr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//			return null;
//		}
//    }
//
//	/**
//     * Date日期转换成String
//     *
//     */
//    public static final String date2Stringyyyymmddhhmmss(Date date) {
//        if (date == null) {
//            return "";
//        }
//        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//        return df.format(date);
//    }
//
//    public static final String date2StringyyyymmddhhmmssSSS(Date date) {
//        if (date == null) {
//            return "";
//        }
//        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        return df.format(date);
//    }
//
//    /**
//     * 按日加,指定日期
//     *
//     * @param date
//     * @param value
//     * @return
//     */
//    public static final Date addYear(Date date, int value) {
//        Calendar now = Calendar.getInstance();
//        now.setTime(date);
//        now.add(Calendar.YEAR, value);
//        return now.getTime();
//    }
//
//    /**
//     * 按日加,指定日期
//     *
//     * @param date
//     * @param value
//     * @return
//     */
//    public static final Date addDay(Date date, int value) {
//        Calendar now = Calendar.getInstance();
//        now.setTime(date);
//        now.add(Calendar.DAY_OF_YEAR, value);
//        return now.getTime();
//    }
//
//    /**
//     * 按月加
//     *
//     * @param value
//     * @return
//     */
//    public static final Date addMonth(Date date, int value) {
//        Calendar now = Calendar.getInstance();
//        now.setTime(date);
////        now.add(Calendar.MONTH, 1);
////        now.set(Calendar.DATE, value);
////        return now.getTime();
//        now.add(Calendar.MONTH, value);
//        return now.getTime();
//    }
//
//    /**
//     * 按秒加
//     *
//     * @param value
//     * @return
//     */
//    public static final Date addSecond(Date date, int value) {
//        Calendar now = Calendar.getInstance();
//        now.setTime(date);
//        now.add(Calendar.SECOND, value);
//        return now.getTime();
//    }
//
//    /**
//     * 按秒加
//     * yyyyMMddHHmmss
//     * @param value
//     * @return
//     */
//    public static final String addSecondyyyyMMddHHmmss(String dateStr, int value) {
//    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//    	Date date = null;
//		try {
//			date = simpleDateFormat.parse(dateStr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//        Calendar now = Calendar.getInstance();
//        now.setTime(date);
//        now.add(Calendar.SECOND, value);
//
//        return simpleDateFormat.format(now.getTime());
//    }
//
//    /**
//     * 按秒加
//     * yyyyMMddHHmmssSSS
//     * @param value
//     * @return
//     */
//    public static final String addSecondyyyyMMddHHmmssSSS(String dateStr, int value) {
//    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//    	Date date = null;
//		try {
//			date = simpleDateFormat.parse(dateStr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//        Calendar now = Calendar.getInstance();
//        now.setTime(date);
//        now.add(Calendar.SECOND, value);
//
//        return simpleDateFormat.format(now.getTime());
//    }
//    /**
//     * 补年
//     * @param dateStr
//     * @return
//     */
//    public static final String convMMddToyyyyMMdd(String dateStr) {
//    	String nowDate = getCurrentDateStr();
//    	int year = Integer.parseInt(nowDate.substring(0, 4));
//    	if(dateStr.substring(0,2).equals("12")&&nowDate.substring(4,6).equals("01")){
//    		year--;
//    	}else if(dateStr.substring(0,2).equals("01")&&nowDate.substring(4,6).equals("12")){
//    		year++;
//    	}
//    	return year+dateStr;
//    }
//
//	/**
//     * 按小时加
//     * yyyyMMddHHmmssSSS
//     * @param value
//     * @return
//     */
//    public static final String addHouryyyyMMddHHmmssSSS(String dateStr, int value) {
//    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//    	Date date = null;
//		try {
//			date = simpleDateFormat.parse(dateStr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//        Calendar now = Calendar.getInstance();
//        now.setTime(date);
//        now.add(Calendar.HOUR, value);
//
//        return simpleDateFormat.format(now.getTime());
//    }
//
//    /**
//     * 按小时加
//     * yyyyMMddHHmmssSSS
//     * @param value
//     * @return
//     */
//    public static final String addHouryyyyMMddHHmmssSSS(String dateStr, String value) {
//    	return addHouryyyyMMddHHmmssSSS(dateStr,Integer.parseInt(value));
//     }
//
//    /***
//     * 按分钟减
//     * @param dateStr
//     * @param value
//     * @return
//     */
//    public static final String subMinyyyyMMddHHmmssSSS(String dateStr, int value){
//    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//    	Date date = null;
//		try {
//			date = simpleDateFormat.parse(dateStr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//        Calendar now = Calendar.getInstance();
//        now.setTime(date);
//        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) - value);
//        return simpleDateFormat.format(now.getTime());
//    }
//    /***
//     * 按分钟减
//     * @param dateStr
//     * @param value
//     * @return
//     */
//    public static final String subMinyyyyMMddHHmmssSSS(String dateStr, String value){
//    	return subMinyyyyMMddHHmmssSSS(dateStr,Integer.parseInt(value));
//    }
//    /***
//     * 按秒减
//     * @param dateStr
//     * @param value
//     * @return
//     */
//    public static final String subSecondyyyyMMddHHmmssSSS(String dateStr, int value){
//    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//    	Date date = null;
//		try {
//			date = simpleDateFormat.parse(dateStr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//        Calendar now = Calendar.getInstance();
//        now.setTime(date);
//        now.set(Calendar.SECOND, now.get(Calendar.SECOND) - value);
//        return simpleDateFormat.format(now.getTime());
//    }
//    /***
//     * 比较日期大小
//     * yyyyMMddHHmmssSSS
//     * 0 ： 相等
//     * 1 ： dateStr2 早于 dateStr1
//     * -1：dateStr1 早于 dateStr2
//     * @param dateStr1
//     * @param dateStr2
//     * @return
//     */
//    public static int compareDateyyyyMMddHHmmssSSS(String dateStr1, String dateStr2){
//    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//    	Date date1 = null;
//    	Date date2 = null;
//		try {
//			date1 = simpleDateFormat.parse(dateStr1);
//			date2 = simpleDateFormat.parse(dateStr2);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return compareDateyyyyMMddHHmmssSSS(date1, date2);
//    }
//    /***
//     * 比较日期大小
//     * yyyyMMddHHmmssSSS
//     * 0 ： 相等
//     * 1 ： date2 早于 date1
//     * -1：date1 早于 date2
//     * @param date1
//     * @param date2
//     * @return
//     */
//    public static int compareDateyyyyMMddHHmmssSSS(Date date1, Date date2){
//		return date1.compareTo(date2);
//    }
//
//    /**
//	 * 通过日期格式化返回日期数组
//	 *
//	 * @param formatStr
//	 *            例如：MMdd HHmmss或者YYMMdd HHmmss
//	 * @param years
//	 *            要增加或者减去的年份，如果为正数则加上相应的年份，负数则减去相应的年份
//	 * @return 通过格式中间的空格分成数组返回
//	 */
//	public static String[] getDateArray(String formatStr, int years) {
//		Calendar c = Calendar.getInstance();
//		c.add(Calendar.YEAR, years);
//		SimpleDateFormat simpleFormat = new SimpleDateFormat(formatStr);
//		String dateNowStr = simpleFormat.format(c.getTime());
//		String[] dateArray = dateNowStr.split(" ");
//		return dateArray;
//	}
//
//	public static String getDateStr(String formatStr){
//		SimpleDateFormat df = new SimpleDateFormat(formatStr);
//		Date date = new Date();
//		return df.format(date);
//	}
//
//    public static String formatByDateTimePattern(Date date) {
//        return DateFormatUtils.format(date, DATETIME_PATTERN);
//    }
//
//    public static Date parseByDayPattern(String str) {
//        return parseDate(str, DAY_PATTERN);
//    }
//
//    public static Date parseDate(String str, String pattern) {
//        try {
//            return DateUtils.parseDate(str, pattern);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
