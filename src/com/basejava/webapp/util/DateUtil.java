package com.basejava.webapp.util;

import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public static final YearMonth NOW = YearMonth.of(3000, 1);

    public static YearMonth of(int year, Month month) {
        return YearMonth.of(year, month);
    }
}