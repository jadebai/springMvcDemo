package com.baiyu.springmvc.annotation;

import java.lang.annotation.*;

/**
 * @author baiyu
 * @description: Qualifier  引入对象注解
 * @date: 2018/7/17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Qualifier {
    String value() default "";
}
