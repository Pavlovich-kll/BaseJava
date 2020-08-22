package com.basejava.webapp.util;

import java.time.YearMonth;

public class DateUtil {
    public static final YearMonth NOW = YearMonth.of(3000, 1);

    public static YearMonth parse(String str) {
        if (!str.contains("-")) {
            return NOW;
        } else {
            String[] strs = str.split("-");
            return YearMonth.of(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]));
        }
    }
}