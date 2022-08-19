package com.kim.study.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * @author HUANGJIN
 * @version v1.0
 * @date 2021/11/11  14:06
 */
@Slf4j
public class DateUtils {

    public static final int FIRST_DAY_OF_WEEK = Calendar.MONDAY; // 中国周一是一周的第一天
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;
    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    private DateUtils(){

    }

    /**
     * 获取SimpleDateFormat
     * @param format
     * @return
     */
    public static SimpleDateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     *
     * @param smallDate
     *            较小的日期
     * @param bigDate
     *            较大的日期
     * @return 相差天数
     */
    public static int daysBetween(String smallDate, String bigDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(smallDate));
            long smallDateTime = calendar.getTimeInMillis();
            calendar.setTime(sdf.parse(bigDate));
            long bigDateTime = calendar.getTimeInMillis();
            long betweenDays = (bigDateTime - smallDateTime) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(betweenDays));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     *
     * @param date1 <String>
     * @param date2 <String>
     * @return int
     * @throws ParseException
     */
    public static int monthBetween(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int result = 0;
        try {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(sdf.parse(date1));
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(sdf.parse(date2));
            result = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
            return result == 0 ? 1 : Math.abs(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }



    /**
     * 取得日期：年
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        return year;
    }

    /**
     * 取得日期：月
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        return month + 1;
    }

    /**
     * 取得日期：日
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int da = c.get(Calendar.DAY_OF_MONTH);
        return da;
    }

    /**
     * 获取某年第一天
     * @param year
     * @return
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }
    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public String getWeekOfDate(Date date) {
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 取得一年的第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(Calendar.WEEK_OF_YEAR);
        return week_of_year;
    }

    /**
     * getWeekBeginAndEndDate
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getWeekBeginAndEndDate(Date date, String pattern) {
        Date monday = getMondayOfWeek(date);
        Date sunday = getSundayOfWeek(date);
        return getDateStr(pattern,monday)+"-"+getDateStr(pattern,sunday);
    }

    /**
     * 根据日期取得对应周周一日期
     *
     * @param date
     * @return
     */
    public static Date getMondayOfWeek(Date date) {
        Calendar monday = Calendar.getInstance();
        monday.setTime(date);
        monday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
        monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return monday.getTime();
    }

    /**
     * 根据日期取得对应周周日日期
     *
     * @param date
     * @return
     */
    public static Date getSundayOfWeek(Date date) {
        Calendar sunday = Calendar.getInstance();
        sunday.setTime(date);
        sunday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
        sunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return sunday.getTime();
    }

    /**
     * 取得月的剩余天数
     *
     * @param date
     * @return
     */
    public static int getRemainDayOfMonth(Date date) {
        int dayOfMonth = getDayOfMonth(date);
        int day = getPassDayOfMonth(date);
        return dayOfMonth - day;
    }

    /**
     * 取得月已经过的天数
     *
     * @param date
     * @return
     */
    public static int getPassDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得月天数
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }


    /**
     * 取得季度第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfSeason(Date date) {
        return getFirstDateOfMonth(getSeasonDate(date)[0]);
    }

    /**
     * 取得季度最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfSeason(Date date) {
        return getLastDateOfMonth(getSeasonDate(date)[2]);
    }

    /**
     * 取得季度天数
     *
     * @param date
     * @return
     */
    public static int getDayOfSeason(Date date) {
        int day = 0;
        Date[] seasonDates = getSeasonDate(date);
        for (Date date2 : seasonDates) {
            day += getDayOfMonth(date2);
        }
        return day;
    }

    /**
     * 取得季度剩余天数
     *
     * @param date
     * @return
     */
    public static int getRemainDayOfSeason(Date date) {
        return getDayOfSeason(date) - getPassDayOfSeason(date);
    }

    /**
     * 取得季度已过天数
     *
     * @param date
     * @return
     */
    public static int getPassDayOfSeason(Date date) {
        int day = 0;

        Date[] seasonDates = getSeasonDate(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);

        if (month == Calendar.JANUARY || month == Calendar.APRIL
                || month == Calendar.JULY || month == Calendar.OCTOBER) {// 季度第一个月
            day = getPassDayOfMonth(seasonDates[0]);
        } else if (month == Calendar.FEBRUARY || month == Calendar.MAY
                || month == Calendar.AUGUST || month == Calendar.NOVEMBER) {// 季度第二个月
            day = getDayOfMonth(seasonDates[0])
                    + getPassDayOfMonth(seasonDates[1]);
        } else if (month == Calendar.MARCH || month == Calendar.JUNE
                || month == Calendar.SEPTEMBER || month == Calendar.DECEMBER) {// 季度第三个月
            day = getDayOfMonth(seasonDates[0]) + getDayOfMonth(seasonDates[1])
                    + getPassDayOfMonth(seasonDates[2]);
        }
        return day;
    }

    /**
     * 取得季度月
     *
     * @param date
     * @return
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason == 1) {// 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nSeason == 2) {// 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nSeason == 3) {// 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nSeason == 4) {// 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     *
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return
     */
    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 获取当前日期季区间
     * @param date
     * @return
     */
    public static Map getSeasonTime(Date date) {
        Map<String, String> map = Maps.newHashMap();
        String dateUp = null;
        String dateDown = null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                dateUp = String.valueOf(getYear(date)) + "0101";
                dateDown = String.valueOf(getYear(date)) + "0331";
                map.put("dateUp",dateUp);
                map.put("dateDown",dateDown);
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                dateUp = String.valueOf(getYear(date)) + "0401";
                dateDown = String.valueOf(getYear(date)) + "0630";
                map.put("dateUp",dateUp);
                map.put("dateDown",dateDown);
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                dateUp = String.valueOf(getYear(date)) + "0701";
                dateDown = String.valueOf(getYear(date)) + "0930";
                map.put("dateUp",dateUp);
                map.put("dateDown",dateDown);
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                dateUp = String.valueOf(getYear(date)) + "1001";
                dateDown = String.valueOf(getYear(date)) + "1231";
                map.put("dateUp",dateUp);
                map.put("dateDown",dateDown);
                break;
            default:
                break;
        }
        return map;
    }

    /**
     * 获取当前日期上一季区间
     * @param date
     * @return
     */
    public static Map getSeasonTimeUp(Date date) {
        Map<String, Object> map = Maps.newHashMap();
        String dateUp = null;
        String dateDown = null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month-3) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                dateUp = String.valueOf(getYear(date)) + "0101";
                dateDown = String.valueOf(getYear(date)) + "0331";
                map.put("dateUp",dateUp);
                map.put("dateDown",dateDown);
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                dateUp = String.valueOf(getYear(date)) + "0401";
                dateDown = String.valueOf(getYear(date)) + "0630";
                map.put("dateUp",dateUp);
                map.put("dateDown",dateDown);
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                dateUp = String.valueOf(getYear(date)) + "0701";
                dateDown = String.valueOf(getYear(date)) + "0930";
                map.put("dateUp",dateUp);
                map.put("dateDown",dateDown);
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                dateUp = String.valueOf(getYear(date)) + "1001";
                dateDown = String.valueOf(getYear(date)) + "1231";
                map.put("dateUp",dateUp);
                map.put("dateDown",dateDown);
                break;
            default:
                break;
        }
        return map;
    }

    /*
     * Params :
     * dateFormat : 日期格式 如 yyyy-MM-dd
     * date:要修改的日期的String类型
     * type:要增加的日期，年或者月或者日
     * num:要加的位数
     * */
    public static String addDateNum(String dateFormat,String date,String type,int num) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Calendar c =  Calendar.getInstance();
        c.setTime(format.parse(date));
        if(type.equals("MONTH")){
            c.add(Calendar.MONTH,+num);
            return format.format(c.getTime());
        }else if(type.equals("YEAR")){
            c.add(Calendar.YEAR,+num);
            return format.format(c.getTime());
        }else if(type.equals("DAY")){
            c.add(Calendar.DATE,+num);
            return format.format(c.getTime());
        }else{
            return date;
        }
    }

    /**
     * 获取两个时间中的每一天
     * @param bigtimeStr 开始时间 yyyyMMdd
     * @param endTimeStr 结束时间 yyyyMM-dd
     * @return
     * @throws ParseException
     */
    public static List<String> getDays(String bigtimeStr, String endTimeStr){
        Date bigtime = null;
        try {
            bigtime = new SimpleDateFormat("yyyyMMdd").parse(bigtimeStr);
        Date endtime = new SimpleDateFormat("yyyyMMdd").parse(endTimeStr);
        //定义一个接受时间的集合
        List<Date> lDate = new ArrayList<>();
        lDate.add(bigtime);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(bigtime);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(endtime);
        // 测试此日期是否在指定日期之后
        while (endtime.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        List<String> datas = new LinkedList<>();
        for (Date date : lDate) {
            datas.add(new SimpleDateFormat("yyyyMMdd").format(date));
        }
        return datas;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 字符串转时间
     * @param date
     * @param format
     * @return
     */
    public static Date toDate(String date, String format) {
        try {
            return getFormat(format).parse(date);
        } catch (Exception e) {
            log.error("DateUtil => Date format error, value: {}, format: {}.", date, format);
            throw new RuntimeException("DateUtil => Date format error.");
        }
    }

    /**
     * @param number
     */
    public static String getPreStr(String formatStr,Integer type, Integer number) {
        return getFormat(formatStr).format(getPreDate(type,number));
    }

    /**
     * 获取n(天,)
     */
    public static Date getPreDate(Integer type ,Integer number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(type,-number);
        return calendar.getTime();
    }

    /**
     * 获取过去未来时间的yyyyMMdd long值
     * @param formatStr
     * @param number
     * @return
     */
    public static long getPreDayLong(String formatStr,Integer number) {
        String preDayStr = getPreStr(formatStr,Calendar.DATE, number);
        return Long.valueOf(Long.valueOf(preDayStr.replace("-",""))).longValue();
    }

    /**
     * 获取给定时间的long值(yyyyMMdd)
     * @param formatStr
     * @param date
     * @return
     */
    public static long getDateLong(String formatStr,Date date) {
        String str = getFormat(formatStr).format(date);
        return Long.valueOf(Long.valueOf(str.replace("-",""))).longValue();
    }

    /**
     *时间转字符串
     * @param formatStr
     * @param date
     * @return
     */
    public static String getDateStr(String formatStr,Date date) {
        SimpleDateFormat  format= getFormat(formatStr);
        return format.format(date);
    }

    /**
     * 获取时间距离当前时间几秒,几分钟,几小时,几天,几月,几年前
     * @param date
     * @return
     */
    public static String format(Date date) {
        long delta = System.currentTimeMillis() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

    }
