package com.baiyu.springmvc.controller;

import com.baiyu.springmvc.annotation.Controller;
import com.baiyu.springmvc.annotation.Mapping;
import com.baiyu.springmvc.annotation.Qualifier;
import com.baiyu.springmvc.service.TestService;

/**
 * @author baiyu
 * @description: TestController
 * @date: 2018/7/17
 */
@Controller("testController")
@Mapping("test")
public class TestController {

    @Qualifier("testServiceImpl")
    private TestService testService;

    @Mapping("test")
    public Object test(){
        testService.test();
        return "test springmvc demo";
    }
}
