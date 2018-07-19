package com.baiyu.springmvc.selvet;

import com.baiyu.springmvc.annotation.Controller;
import com.baiyu.springmvc.annotation.ResposeBody;
import com.baiyu.springmvc.handler.InitHandler;
import com.baiyu.springmvc.utils.ObjectUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import netscape.javascript.JSObject;
import sun.org.mozilla.javascript.internal.json.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
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
            Object result = method.invoke(controller);
            //定义网络文件的类型和编码，决定浏览器以什么形式、什么编码读取这个文件
            resp.setContentType("text/html;charst=utf-8");
            //构造一个标准输出流
            PrintWriter out = resp.getWriter();
            if (controller.getClass().isAnnotationPresent(ResposeBody.class)
                    || method.isAnnotationPresent(ResposeBody.class)){
                //将内容输出到客户端
                Gson gson = new Gson();
                out.println(gson.toJson(result));
            }else{
                //将内容输出到客户端
                out.println(result);
            }
            //关闭输出流
            out.close();
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
