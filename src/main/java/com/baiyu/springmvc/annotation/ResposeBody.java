package com.baiyu.springmvc.annotation;

import java.lang.annotation.*;

/**
 * @author baiyu
 * @description: ResposeBody
 * @date: 2018/7/19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD ,ElementType.TYPE})
public @interface ResposeBody {
}
