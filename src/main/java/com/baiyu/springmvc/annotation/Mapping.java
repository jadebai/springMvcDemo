package com.baiyu.springmvc.annotation;

import java.lang.annotation.*;

/**
 * @author baiyu
 * @description: Mapping  请求的映射关系注解
 * @date: 2018/7/17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Mapping {

    String value() default "";
}
