package com.crscd.framework.util.time;

import com.crscd.framework.FrameworkConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.crscd.framework.util.time.DateTimeFormatterUtil.*;

/**
 * This util is a combination of DateUtil and TimeUtil
 * Use new java time class in jdk 1.8 to refactor old utils
 * <p>
 * Create by: lzy
 * Date: 2017/7/17
 * Time: 10:09
 */
public class DateTimeUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    /**
     * Convert Date type to LocalDateTime type
     */
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * Convert Date to string
     *
     * @param date
     * @return
     */
    public static String getDateTimeFromDateType(Date date) {
        return convertDatetimeToString(convertDateToLocalDateTime(date));
    }

    /**
     * Get current date-time and return a string
     */
    public static String getCurrentDatetimeString() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    /**
     * Get current date and return a string
     */
    public static String getCurrentDateString() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * Get current weekday
     */
    public static DayOfWeek getCurrentWeekDay() {
        return LocalDate.now().getDayOfWeek();
    }

    public static String getCurrentWeekDayString() {
        return getCurrentWeekDay().toString();
    }

    /**
     * Get current time and return a string
     */
    public static String getCurrentTimeString() {
        return LocalDateTime.now().format(TIME_FORMATTER);
    }

    // todo: 需要考虑发生异常是在底层处理还是在调用处处理
    public static Date getDateFromString(String datetime) {
        try {
            LocalDateTime localDateTime = convertStringToDatetime(datetime);
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zone).toInstant();
            return Date.from(instant);
        } catch (Exception e) {
            logger.error("datetime is illegal:" + datetime, e);
        }
        return null;
    }


    /**
     * Determines whether the time string is after the current time
     *
     * @param str
     * @return
     */
    public static boolean isAfterPresentTime(String str) {
        try {
            LocalDateTime dateTime = convertStringToDatetime(str);
            return dateTime.isAfter(LocalDateTime.now());
        } catch (Exception e) {
            logger.error("Time string" + str + " format is illegal", e);
        }
        return false;
    }

    /**
     * Determine whether the first time parameter is after the second time parameter
     *
     * @param t1
     * @param t2
     * @return
     * @throws Exception
     */
    public static boolean isAfterLatterTime(String t1, String t2) throws Exception {
        if (isValidTime(t1) && isValidTime(t2)) {
            return isAfterLatterDateTime(t1, t2, TIME_FORMAT_PATTERN);
        } else if (isValidDate(t1) && isValidDate(t2)) {
            return isAfterLatterDateTime(t1, t2, DATE_FORMAT_PATTERN);
        } else if (isValidDateTime(t1) && isValidDateTime(t2)) {
            return isAfterLatterDateTime(t1, t2, DATETIME_FORMAT_PATTERN);
        } else {
            throw new Exception("time " + t1 + " or time " + t2 + " is illegal!");
        }
    }

    /**
     * Determine whether the first time parameter is after the second time parameter
     *
     * @param t1
     * @param t2
     * @return
     * @throws Exception
     */
    public static boolean isAfterOrEqualLatterDate(String t1, String t2) {
        if (isValidTime(t1) && isValidTime(t2)) {
            return isAfterOrEqualLatterDateTime(t1, t2, TIME_FORMAT_PATTERN);
        } else if (isValidDate(t1) && isValidDate(t2)) {
            return isAfterOrEqualLatterDateTime(t1, t2, DATE_FORMAT_PATTERN);
        } else if (isValidDateTime(t1) && isValidDateTime(t2)) {
            return isAfterOrEqualLatterDateTime(t1, t2, DATETIME_FORMAT_PATTERN);
        } else {
            throw new RuntimeException("time " + t1 + " or time " + t2 + " is illegal!");
        }
    }

    /**
     * Determine whether the first time parameter is after the second time parameter
     *
     * @param t1
     * @param t2
     * @param formatter
     * @return
     * @throws Exception
     */
    public static boolean isAfterLatterDateTime(String t1, String t2, String formatter) {
        if (isDateTimeStringValidFormat(t1, formatter) && isDateTimeStringValidFormat(t2, formatter)) {
            LocalDateTime time1 = convertStringToDatetimeWithFomatter(t1, formatter);
            LocalDateTime time2 = convertStringToDatetimeWithFomatter(t2, formatter);
            return time1.isAfter(time2);
        } else {
            throw new RuntimeException("time " + t1 + " or time " + t2 + " is illegal!");
        }
    }

    public static boolean isNowInInterval(LocalDate start, LocalDate end) {
        return isInInterval(getCurrentDate(), start, end);
    }

    /**
     * start date <= date <= end date
     *
     * @param date
     * @param start
     * @param end
     * @return
     */
    public static boolean isInInterval(LocalDate date, LocalDate start, LocalDate end) {
        boolean afterOrEqualStart = true;
        if (null != start) {
            afterOrEqualStart = date.isAfter(start) || date.isEqual(start);
        }
        boolean beforeOrEqualEnd = true;
        if (null != end) {
            beforeOrEqualEnd = date.isEqual(end) || end.isAfter(date);
        }
        return afterOrEqualStart && beforeOrEqualEnd;
    }

    public static boolean isInInterval(LocalDateTime dateTime, LocalDateTime start, LocalDateTime end) {
        boolean afterOrEqualStart = true;
        if (null != start) {
            afterOrEqualStart = dateTime.isAfter(start) || dateTime.isEqual(start);
        }
        boolean beforeOrEqualEnd = true;
        if (null != end) {
            beforeOrEqualEnd = dateTime.isEqual(end) || end.isAfter(dateTime);
        }
        return afterOrEqualStart && beforeOrEqualEnd;
    }

    /**
     * Determine whether the first time parameter is after the second time parameter
     *
     * @param t1
     * @param t2
     * @param formatter
     * @return
     * @throws Exception
     */
    public static boolean isAfterOrEqualLatterDateTime(String t1, String t2, String formatter) {
        if (isDateTimeStringValidFormat(t1, formatter) && isDateTimeStringValidFormat(t2, formatter)) {
            LocalDateTime time1 = convertStringToDatetimeWithFomatter(t1, formatter);
            LocalDateTime time2 = convertStringToDatetimeWithFomatter(t2, formatter);
            return time1.isAfter(time2) || time1.equals(time2);
        } else {
            throw new RuntimeException("time " + t1 + " or time " + t2 + " is illegal!");
        }
    }

    /**
     * Adjust the seconds on the basis of the base time
     * Returns the "--" if a problem occurs during the calculation
     *
     * @param time
     * @param second
     * @return
     */
    public static String calcTimeWithSecond(String time, int second) {
        try {
            LocalDateTime dateTime = convertStringToDatetime(time);
            if (null != dateTime) {
                return convertDatetimeToString(dateTime.plus(second, ChronoUnit.SECONDS));
            }
        } catch (Exception e) {
            logger.error("Time string" + time + " format is illegal", e);
        }
        return FrameworkConstant.EMPTY_TIME;
    }

    /**
     * Adjust the minutes on the basis of the base time
     * Returns the "--" if a problem occurs during the calculation
     *
     * @param time
     * @param min
     * @return
     */
    public static String calcTimeWithMinute(String time, int min) {
        try {
            LocalDateTime dateTime = convertStringToDatetime(time);
            if (null != dateTime) {
                return convertDatetimeToString(dateTime.plus(min, ChronoUnit.MINUTES));
            }
        } catch (Exception e) {
            logger.error("Time string" + time + " format is illegal", e);
        }
        return FrameworkConstant.EMPTY_TIME;
    }

    /**
     * Adjust the minutes on the basis of the base time
     * Returns the "--" if a problem occurs during the calculation
     *
     * @param time
     * @param day
     * @return
     */
    public static String calcTimeWithDay(String time, int day) {
        try {
            LocalDateTime dateTime = convertStringToDatetime(time);
            if (null != dateTime) {
                return convertDatetimeToString(dateTime.plus(day, ChronoUnit.DAYS));
            }
        } catch (Exception e) {
            logger.error("Time string" + time + " format is illegal", e);
        }
        return FrameworkConstant.EMPTY_TIME;
    }

    /**
     * Get million second of current time
     */
    public static long currentTimeMillis() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * Get string date list between start day and end day
     *
     * @param start
     * @param end
     * @return
     */
    public static List<String> getDateList(String start, String end) {
        LocalDate startDate = LocalDate.parse(start, DATE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, DATE_FORMATTER);
        List<String> dateList = new ArrayList<>();
        long period = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        for (int i = 0; i < period; i++) {
            dateList.add(startDate.plusDays(i).format(DATE_FORMATTER));
        }
        return dateList;
    }

    public static long getDateNumInPeriod(String start, String end) {
        LocalDate startDate = LocalDate.parse(start, DATE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, DATE_FORMATTER);
        return ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    public static List<String> getDateList(LocalDate start, LocalDate end) {
        List<String> dateList = new ArrayList<>();
        int period = Period.between(start, end).getDays() + 1;
        for (int i = 0; i < period; i++) {
            dateList.add(start.plusDays(i).format(DATE_FORMATTER));
        }
        return dateList;
    }

    /**
     * 在当前日期的基础上增加i天
     *
     * @param i
     * @return
     */
    public static String getDateByPlusDays(int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, i);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 时间累加
     *
     * @param localDateTime
     * @param timeChange
     * @param addFlag
     * @return LocalDateTime
     */
    public static LocalDateTime dateTimeCalculator(LocalDateTime localDateTime, LocalTime timeChange, boolean addFlag) {
        int hour = timeChange.getHour();
        int minute = timeChange.getMinute();
        int second = timeChange.getSecond();
        LocalDateTime result;
        if (addFlag) {
            result = localDateTime.plusHours(hour);
            result = result.plusMinutes(minute);
            result = result.plusSeconds(second);
        } else {
            result = localDateTime.minusHours(hour);
            result = result.minusMinutes(minute);
            result = result.minusSeconds(second);
        }
        return result;
    }


    /**
     * 时间比较
     *
     * @param localDateTime
     * @param localTime
     * @return boolean
     */
    public static boolean compareTime(LocalDateTime localDateTime, LocalTime localTime) {
        if (null == localDateTime && null == localTime) {
            return true;
        } else if (null == localDateTime || null == localTime) {
            return false;
        } else {
            int hour = localDateTime.getHour();
            int minute = localDateTime.getMinute();
            int second = localDateTime.getSecond();
            LocalTime parseTime = LocalTime.of(hour, minute, second);
            return parseTime.equals(localTime);
        }

    }


}
