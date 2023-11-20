package com.gz.example.one;

/**
 * @Description: TODO
 * @Author: zguo
 * @CreateTime: 2023-11-20  16:05
 * @Version: 1.0
 */
public class StringUtils {
    public static boolean isNotBlank(String readConverterExp) {
        return !readConverterExp.isEmpty();
    }

    public static String stripEnd(String str, String separator) {
        return str.substring(0,str.length()-1);
    }

    public static boolean containsAny(String separator, String propertyValue) {
        return true;
    }
}
