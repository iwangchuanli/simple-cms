package com.cms.template.common.util;

import cn.hutool.core.date.DateTime;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;


public class DateTimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";


    //Date->str
    public static String dateToStr(Date date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    //Date->str
    public static String dateToStr(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

    public static String getDayBegin() {
        return getDayBegin(System.currentTimeMillis());
    }

    public static String getDayEnd() {
        return getDayEnd(System.currentTimeMillis());
    }

    public static String getDayBegin(Long dateTimeMillis) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(dateTimeMillis));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return dateToStr(new Date(c.getTimeInMillis()));
    }


    public static String getDayEnd(Long dateTimeMillis) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(dateTimeMillis));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return dateToStr(new Date(c.getTimeInMillis()));
    }

    /**
     * 获取指定时间戳所在月份开始的时间戳
     */
    public static String getMonthBegin() {
        return getMonthBegin(System.currentTimeMillis());
    }

    public static String getMonthEnd() {
        return getMonthEnd(System.currentTimeMillis());
    }
    public static String getMonthBegin(Long dateTimeMillis) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(dateTimeMillis));
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return dateToStr(new Date(c.getTimeInMillis()));
    }

    public static String getMonthEnd(Long dateTimeMillis) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(dateTimeMillis));
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return dateToStr(new Date(c.getTimeInMillis()));
    }

    public static String getPreviousMonthBegin() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
        return dateToStr(new Date(c.getTimeInMillis()));
    }

    public static String getPreviousMonthEnd() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        int MaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), MaxDay, 23, 59, 59);
        return dateToStr(new Date(c.getTimeInMillis()));
    }




}
