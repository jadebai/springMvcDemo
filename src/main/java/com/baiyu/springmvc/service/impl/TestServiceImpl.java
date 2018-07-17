package com.baiyu.springmvc.service.impl;

import com.baiyu.springmvc.annotation.Service;
import com.baiyu.springmvc.service.TestService;

/**
 * @author baiyu
 * @description: TestServiceImpl
 * @date: 2018/7/17
 */
@Service("testServiceImpl")
public class TestServiceImpl implements TestService {

    @Override
    public void test() {
        System.out.println("Test springMvc annotation");
    }
}
