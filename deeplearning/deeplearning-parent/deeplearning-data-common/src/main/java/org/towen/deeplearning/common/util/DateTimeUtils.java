/*
 * Copyright (c) 2016 Augmentum, Inc. All rights reserved.
 */
package org.towen.deeplearning.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String HHmmssSSS = "HHmmssSSS";
    public static final String HHmm00000 = "HHmm00000";

    public static Date toDate(int date, String timeFormat) {
        StringBuilder sbBuilder = new StringBuilder();
        if (!yyyyMMdd.equals(timeFormat) && String.valueOf(date).length() == 8) {
            sbBuilder.append("0");
        }
        sbBuilder.append(date);
        DateFormat sdf = new SimpleDateFormat(timeFormat);
        Date datetime = new Date();
        try {
            datetime = sdf.parse(sbBuilder.toString());
        } catch (ParseException e) {
            throw new RuntimeException("parse the time [" + date + "] with format [" + timeFormat + "] error", e);
        }
        return datetime;
    }

    public static Date toDate(int date, int time, String timeFormat) {
        StringBuilder sbBuilder = new StringBuilder();
        sbBuilder.append(date);
        if (String.valueOf(time).length() == 8) {
            sbBuilder.append("0");
        }
        sbBuilder.append(time);
        DateFormat sdf = new SimpleDateFormat(timeFormat);
        Date datetime = new Date();
        try {
            datetime = sdf.parse(sbBuilder.toString());
        } catch (ParseException e) {
            throw new RuntimeException("parse the time [" + date + " " + time + "] with format [" + timeFormat
                    + "] error", e);
        }
        return datetime;
    }

    public static int getIntegerDateTime(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        int dateTime = Integer.parseInt(sdf.format(date.getTime()));
        return dateTime;
    }

    public static String getStringDateTime(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String dateTime = sdf.format(date.getTime());
        return dateTime;
    }

    public static Calendar getCalendarByDateTime(int dateTime, String dateFormat) {

        Date date = toDate(dateTime, dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static int getNextTime(int time, String timeFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        Calendar date = getCalendarByDateTime(time, timeFormat);
        date.add(Calendar.MINUTE, +1);
        int nextTime = Integer.parseInt(sdf.format(date.getTime()));
        return nextTime;
    }

    public static int getNextDate(int date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Calendar calendar = DateTimeUtils.getCalendarByDateTime(date, dateFormat);
        calendar.add(Calendar.DATE, 1);
        int nextDate = Integer.parseInt(sdf.format(calendar.getTime()));
        return nextDate;
    }

    public static int getPreviousDay(int date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Calendar calendar = getPreviousCalendar(date, dateFormat);
        int previousDay = Integer.parseInt(sdf.format(calendar.getTime()));
        return previousDay;
    }

    /**
     * 
     * @return the unix timestamp of today's 23:59:59
     */
    public static long getEndOfToday() {
        Calendar endOfToday = Calendar.getInstance();
        endOfToday.setTime(new Date());
        endOfToday.set(Calendar.HOUR_OF_DAY, 23);
        endOfToday.set(Calendar.MINUTE, 59);
        endOfToday.set(Calendar.SECOND, 59);
        endOfToday.set(Calendar.MILLISECOND, 0);
        return endOfToday.getTimeInMillis() / 1000;
    }

    public static Calendar getPreviousCalendar(int date, String dateFormat) {
        Calendar calendar = DateTimeUtils.getCalendarByDateTime(date, dateFormat);
        calendar.add(Calendar.DATE, -1);
        return calendar;
    }

    public static long getCurrentUnixTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}
