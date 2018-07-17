package com.baiyu.springmvc.utils;

import java.util.Collection;

/**
 * @author baiyu
 * @description: CollectionUtil
 * @date: 2018/7/17
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.size() == 0;
    }
}
