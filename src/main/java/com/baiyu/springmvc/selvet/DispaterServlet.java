package com.baiyu.springmvc.selvet;

import com.baiyu.springmvc.annotation.Controller;
import com.baiyu.springmvc.handler.InitHandler;
import com.baiyu.springmvc.utils.ObjectUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author baiyu
 * @description: DispaterServlet
 * @date: 2018/7/17
 */
public class DispaterServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = req.getRequestURI();
        String contextUrl = req.getContextPath();
        Method method = (Method)InitHandler.mapperMethods.get(requestUrl.replace(contextUrl,""));
        Object controller = InitHandler.mapperControllers.get(requestUrl.replace(contextUrl,""));
        if (ObjectUtil.isEmpty(method) || ObjectUtil.isEmpty(controller)){
            return;
        }
        try {
            method.invoke(controller,null);
        }catch (Exception e){
            System.out.println(method+"反射调用失败"+e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    public void init() throws ServletException {
        InitHandler.init();
    }
}
