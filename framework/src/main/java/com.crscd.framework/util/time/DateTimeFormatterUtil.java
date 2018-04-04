package com.crscd.framework.util.time;

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.util.text.StringUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Calendar;

/**
 * @author lzy
 * Create by: lzy
 * Date: 2017/7/21
 * Time: 14:45
 */
public class DateTimeFormatterUtil {

    static final String DATETIME_FORMAT_PATTERN = FrameworkConstant.DATETIME_FORMAT_PATTERN;
    static final String DATE_FORMAT_PATTERN = FrameworkConstant.DATE_FORMAT_PATTERN;
    static final String TIME_FORMAT_PATTERN = FrameworkConstant.TIME_FORMAT_PATTERN;
    static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN);
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN);
    private static final String TIME_NO_SECOND_PATTERN = FrameworkConstant.TIME_NOSECOND_PATTERN;
    private static final DateTimeFormatter TIME_NO_SECOND_FORMATTER = DateTimeFormatter.ofPattern(TIME_NO_SECOND_PATTERN);

    /**
     * Get a time without second
     */
    public static String getTimeWithoutSecond(String time) {
        if ("--".equals(time)) {
            return "--";
        } else if (isValidDateTime(time)) {
            LocalDateTime dateTime = convertStringToDatetimeWithFomatter(time, DATETIME_FORMAT_PATTERN);
            return dateTime.format(TIME_NO_SECOND_FORMATTER);
        } else if (isValidTime(time)) {
            LocalDateTime dateTime = convertStringToDatetimeWithFomatter(time, TIME_FORMAT_PATTERN);
            return dateTime.format(TIME_NO_SECOND_FORMATTER);
        } else {
            throw new IllegalArgumentException("string is " + time + " not in valid time format!");
        }
    }

    /**
     * Get LocalTime without second
     *
     * @param time
     * @return
     */
    public static LocalTime convertStringToTimeNoSecond(String time) {
        if (StringUtil.isEmpty(time) || time.equals(FrameworkConstant.EMPTY_TIME)) {
            return null;
        } else {
            return LocalTime.parse(time, TIME_FORMATTER).withSecond(0);
        }
    }

    /**
     * Get LocalTime without second
     *
     * @param time
     * @return
     */
    public static LocalTime convertStringToTime(String time) {
        if (StringUtil.isEmpty(time) || time.equals(FrameworkConstant.EMPTY_TIME)) {
            return null;
        } else {
            return LocalTime.parse(time, TIME_FORMATTER);
        }
    }

    /**
     * Convert time string in yyyy-MM-dd to stand LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate convertStringToDate(String date) {
        if (StringUtil.isEmpty(date) || date.equals(FrameworkConstant.EMPTY_TIME)) {
            return null;
        } else {
            if (isValidDate(date)) {
                return LocalDate.parse(date, DATE_FORMATTER);
            } else {
                throw new RuntimeException("datetime " + date + " is not meet the format requirement of yyyy-MM-dd");
            }
        }
    }

    /**
     * Convert time string to stand LocalDateTime of yyyy-MM-dd hh:MM:ss
     *
     * @param datetime
     * @return
     */
    public static LocalDateTime convertStringToDatetime(String datetime) {
        if (StringUtil.isEmpty(datetime) || datetime.equals(FrameworkConstant.EMPTY_TIME)) {
            return null;
        } else {
            if (isValidDateTime(datetime)) {
                return LocalDateTime.parse(datetime, DATETIME_FORMATTER);
            } else {
                throw new RuntimeException("datetime " + datetime + " is not meet the format requirement of yyyy-MM-dd hh:MM:ss");
            }
        }
    }

    /**
     * Convert localtime to string without second
     *
     * @param time
     * @return
     */
    public static String convertTimeToStringNoSecond(LocalTime time) {
        if (time == null) {
            return "--";
        }
        return time.format(TIME_NO_SECOND_FORMATTER);
    }

    /**
     * convert localtime to string
     *
     * @param time
     * @return
     */
    public static String convertTimeToString(LocalTime time) {
        if (time == null) {
            return "--";
        }
        return time.format(TIME_FORMATTER);
    }

    /**
     * convert local date to string
     *
     * @param date
     * @return
     */
    public static String convertDateToString(LocalDate date) {
        if (date == null) {
            return "--";
        }
        return date.format(DATE_FORMATTER);
    }

    /**
     * convert localdatetime to string
     *
     * @param datetime
     * @return
     */
    public static String convertDatetimeToString(LocalDateTime datetime) {
        if (datetime == null) {
            return "--";
        }
        return datetime.format(DATETIME_FORMATTER);
    }

    /**
     * convert localdatetime to string
     *
     * @param datetime
     * @return
     */
    public static String convertDatetimeToStringZeroSecond(LocalDateTime datetime) {
        if (datetime == null) {
            return "--";
        }
        return datetime.withSecond(0).format(DATETIME_FORMATTER);
    }

    public static String convertStringDatetimeToStringTime(String datetime) {
        LocalDateTime dateTime = convertStringToDatetime(datetime);
        return dateTime.format(TIME_FORMATTER);
    }

    /**
     * estimate whether the string is a valid string that confirms to the format
     * requirement of yyyy-MM-dd hh:MM:ss
     */
    public static boolean isValidDateTime(String str) {
        return isDateTimeStringValidFormat(str, DATETIME_FORMAT_PATTERN);
    }

    /**
     * estimate whether the string is a valid string that confirms to the format
     * requirement of yyyy-MM-dd
     */
    public static boolean isValidDate(String str) {
        return isDateTimeStringValidFormat(str, DATE_FORMAT_PATTERN);
    }

    /**
     * estimate whether the string is a valid string that confirms to the format
     * requirement of hh:MM:ss
     *
     * @param str
     * @return
     */
    public static boolean isValidTime(String str) {
        return isDateTimeStringValidFormat(str, TIME_FORMAT_PATTERN);
    }

    /**
     * Convert date-time string to a indicated format
     */
    public static LocalDateTime convertStringToDatetimeWithFomatter(String datetime, String formatter) {
        DateTimeFormatter DATE_FORMATTER = getDynamicDateTimeFormatter(formatter);
        return LocalDateTime.parse(datetime, DATE_FORMATTER);
    }

    /**
     * Estimate whether string is confirm to the indicated format
     */
    public static boolean isDateTimeStringValidFormat(String datetime, String formatter) {
        boolean result = true;
        DateTimeFormatter dateFormatter = getDynamicDateTimeFormatter(formatter);

        try {
            LocalDateTime.parse(datetime, dateFormatter);
        } catch (Exception exceptionIgnore) {
            result = false;
        }
        return result;
    }

    /**
     * Convert calender to string
     *
     * @param date
     * @param format
     * @return
     */
    public static String convertCalenderToString(Calendar date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(date.getTime());
        return dateStr;
    }

    /**
     * build a dynamic formatter
     * default datetime is 1800-1-1 00:00:00
     */
    private static DateTimeFormatter getDynamicDateTimeFormatter(String formatter) {
        DateTimeFormatter dateTimeFormatter = getDirectDateTimeFormatter(formatter);
        // build a dynamic parser
        return new DateTimeFormatterBuilder().append(dateTimeFormatter)
                .parseDefaulting(ChronoField.YEAR_OF_ERA, 1800)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
    }

    /**
     * build a direct formatter
     */
    private static DateTimeFormatter getDirectDateTimeFormatter(String dateTimeFormatter) {
        return DateTimeFormatter.ofPattern(dateTimeFormatter);
    }

    /**
     * convert localdatetime to time
     *
     * @param localDateTime
     * @return
     */
    public static LocalTime convertDatetimeToTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int second = localDateTime.getSecond();
        return LocalTime.of(hour, minute, second);
    }

    /**
     * convert localdatetime to date
     *
     * @param localDateTime
     * @return
     */
    public static LocalDate convertDatetimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        return LocalDate.of(year, month, day);
    }


    /**
     * convert localdatetime to time string
     *
     * @param localDateTime
     * @return
     */
    public static String convertDatetimeToTimeString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "--";
        }
        LocalTime localTime = convertDatetimeToTime(localDateTime);
        if (localTime == null) {
            return "--";
        }
        return convertTimeToString(localTime);
    }
}
