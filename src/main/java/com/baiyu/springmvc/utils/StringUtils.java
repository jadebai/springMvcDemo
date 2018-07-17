package com.baiyu.springmvc.utils;

/**
 * @author baiyu
 * @description: StringUtils
 * @date: 2018/7/17
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str) || str.length() == 0;
    }
}
