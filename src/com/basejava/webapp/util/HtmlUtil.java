package com.basejava.webapp.util;

public class HtmlUtil {

    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }
}