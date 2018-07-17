package com.baiyu.springmvc.annotation;

import java.lang.annotation.*;

/**
 * @author baiyu
 * @description: Service  注解
 * @date: 2018/7/17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Service {

    String value() default "";
}
