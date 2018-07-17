package com.baiyu.springmvc.utils;

import java.util.Map;

/**
 * @author baiyu
 * @description: MapUtil
 * @date: 2018/7/17
 */
public class MapUtil {

    public static boolean isEmpty(Map map) {
        return null == map || map.isEmpty();
    }
}
