package com.crscd.framework.utiltest;

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

/**
 * @author lzy
 * Date: 2017/7/17
 * Time: 11:07
 */
public class DateTimeTest {
    private static final String datetimeFormatPattern = FrameworkConstant.DATETIME_FORMAT_PATTERN;
    private static final String dateFormatPattern = FrameworkConstant.DATE_FORMAT_PATTERN;
    private static final String timeFormatPattern = FrameworkConstant.TIME_FORMAT_PATTERN;
    private static final String timeNoSencondPattern = FrameworkConstant.TIME_NOSECOND_PATTERN;

    private static final DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern(datetimeFormatPattern);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormatPattern);
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timeFormatPattern);
    private static final DateTimeFormatter timeNoSecondFormatter = DateTimeFormatter.ofPattern(timeNoSencondPattern);

    @Test
    public void parseStringToIndicatedFormat() {
        String dateString = "2017-03-01";
        String timeString = "12:01:30";
        String datetimeString = "2017-06-05 15:30:00";

        assertTrue(DateTimeFormatterUtil.isDateTimeStringValidFormat(dateString, FrameworkConstant.DATE_FORMAT_PATTERN));
        assertFalse(DateTimeFormatterUtil.isDateTimeStringValidFormat(timeString, FrameworkConstant.DATE_FORMAT_PATTERN));
        assertFalse(DateTimeFormatterUtil.isDateTimeStringValidFormat(datetimeString, FrameworkConstant.DATE_FORMAT_PATTERN));

        assertFalse(DateTimeFormatterUtil.isDateTimeStringValidFormat(dateString, FrameworkConstant.TIME_FORMAT_PATTERN));
        assertTrue(DateTimeFormatterUtil.isDateTimeStringValidFormat(timeString, FrameworkConstant.TIME_FORMAT_PATTERN));
        assertFalse(DateTimeFormatterUtil.isDateTimeStringValidFormat(datetimeString, FrameworkConstant.TIME_FORMAT_PATTERN));

        assertFalse(DateTimeFormatterUtil.isDateTimeStringValidFormat(dateString, FrameworkConstant.DATETIME_FORMAT_PATTERN));
        assertFalse(DateTimeFormatterUtil.isDateTimeStringValidFormat(timeString, FrameworkConstant.DATETIME_FORMAT_PATTERN));
        assertTrue(DateTimeFormatterUtil.isDateTimeStringValidFormat(datetimeString, FrameworkConstant.DATETIME_FORMAT_PATTERN));
    }

    @Test
    public void convertStringToDateTimeTest() {
        String dateString = "2017-03-01";
        String timeString = "12:01:30";
        String datetimeString = "2017-06-05 15:30:01";

        LocalDateTime dt1 = DateTimeFormatterUtil.convertStringToDatetimeWithFomatter(dateString, dateFormatPattern);
        LocalDateTime dt2 = DateTimeFormatterUtil.convertStringToDatetimeWithFomatter(timeString, timeFormatPattern);
        LocalDateTime dt3 = DateTimeFormatterUtil.convertStringToDatetimeWithFomatter(datetimeString, datetimeFormatPattern);

        System.out.println(dateString + "->" + dt1.toString());
        System.out.println(timeString + "->" + dt2.toString());
        System.out.println(datetimeString + "->" + dt3.toString());

        assertNotNull(dt1);
        assertNotNull(dt2);
        assertNotNull(dt3);
    }

    @Test
    public void convertDatetimeToStringTest() {
        LocalDateTime localDateTime = LocalDateTime.now();

        String dt1 = localDateTime.format(dateFormatter);
        String dt2 = localDateTime.format(timeFormatter);
        String dt3 = localDateTime.format(datetimeFormatter);
        String dt4 = localDateTime.format(timeNoSecondFormatter);

        System.out.println(dt1);
        System.out.println(dt2);
        System.out.println(dt3);
        System.out.println(dt4);

        assertNotNull(dt1);
        assertNotNull(dt2);
        assertNotNull(dt3);
        assertNotNull(dt4);
    }

    @Test
    public void calcDateTimeTest() {
        String dateString = "2017-03-01";
        String timeString = "12:01:30";
        String datetimeString = "2017-06-05 15:30:01";

        try {
            String dt1 = DateTimeFormatterUtil.getTimeWithoutSecond(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String dt2 = DateTimeFormatterUtil.getTimeWithoutSecond(timeString);
            assertEquals(dt2, "12:01");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String dt3 = DateTimeFormatterUtil.getTimeWithoutSecond(datetimeString);
            assertEquals(dt3, "15:30");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void currentTimeTest() {
        System.out.println(DateTimeUtil.currentTimeMillis());
        LocalTime time = LocalTime.of(23, 59, 59);
        System.out.println(time.equals(LocalTime.MAX));
        System.out.println(LocalTime.MAX);
        System.out.println(LocalTime.MIN);
        System.out.println(LocalTime.MIDNIGHT);
        System.out.println(LocalTime.NOON);
    }

    @Test
    public void timestampTest() {
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.now().getNano());
    }

    @Test
    public void periodTest() {
        String start = "2018-01-30";
        String end = "2018-03-01";
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(FrameworkConstant.DATE_FORMAT_PATTERN);
        LocalDate startDate = LocalDate.parse(start, DATE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, DATE_FORMATTER);
        long period = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        System.out.println(period);
    }
}
