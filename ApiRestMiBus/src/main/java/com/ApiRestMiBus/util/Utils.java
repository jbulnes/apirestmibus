package com.ApiRestMiBus.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static SimpleDateFormat formatter;

    public static String generateDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat();
        return formatter.format(date);
    }

    public static Date getToday() {
        setFormatter(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
        Date date = new Date();
        return date;
    }

    public static SimpleDateFormat getFormatter() {
        return formatter;
    }

    public static void setFormatter(SimpleDateFormat formatter) {
        Utils.formatter = formatter;
    }
}
