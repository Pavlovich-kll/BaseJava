package com.basejava.webapp.util;

public class HtmlUtil {

    public static boolean isNotEmpty(String value) {
        return value != null && value.trim().length() != 0;
    }
}