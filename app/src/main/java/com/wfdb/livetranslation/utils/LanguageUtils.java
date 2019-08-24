package com.wfdb.livetranslation.utils;

/**
 * Created by warren on 2019-08-24.
 */
public class LanguageUtils {

    public static String getLocaleCode(String language, String region) {
        return language + "-" + region;
    }
}
