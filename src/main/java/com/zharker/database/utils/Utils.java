package com.zharker.database.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Slf4j
public class Utils {

    private Utils() {
    }


//    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss[XXX]";
    public static final DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static Date string2Date(String dateStr){
        return string2Date(dateStr,null);
    }

    public static Date string2Date(String dateStr, String format) {
        DateTimeFormatter dateTimeFormatter = dtf;
        if (StringUtils.isNotEmpty(format)) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        }
        Date result = null;
        try {
            result = Date.from(dateTimeFormatter.parse(dateStr, Instant::from));
        }catch (DateTimeParseException e){
            e.printStackTrace();
        }
        return result;
    }

    public static String date2String(Date date, String format){
        DateTimeFormatter dateTimeFormatter = dtf;
        if (StringUtils.isNotEmpty(format)) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        }
        return dateTimeFormatter.withZone(ZoneId.systemDefault()).format(date.toInstant());
    }

    public static String date2String(Date date){

        return date2String(date,null);
    }
}
