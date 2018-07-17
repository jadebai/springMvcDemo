package com.baiyu.springmvc.handler;

import com.baiyu.springmvc.annotation.Controller;
import com.baiyu.springmvc.annotation.Mapping;
import com.baiyu.springmvc.annotation.Qualifier;
import com.baiyu.springmvc.annotation.Service;
import com.baiyu.springmvc.utils.CollectionUtil;
import com.baiyu.springmvc.utils.MapUtil;
import com.baiyu.springmvc.utils.ObjectUtil;
import com.baiyu.springmvc.utils.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author baiyu
 * @description: InitHandler
 * @date: 2018/7/17
 */
public class InitHandler {

    public static List<String> beanNames = new ArrayList<>();

    public static Map<String, Object> instances = new HashMap<>();

    public static Map<String, Object> mapperMethods = new HashMap<>();

    public static Map<String, Object> mapperControllers = new HashMap<>();

    /**
     * 初始化类  和 请求映射关系
     */
    public static void init() {
        findAllClassName();
        findAllAnnotaationClassName();
        handlerMapper();
        ioc();
        System.out.println(beanNames.toString());
        System.out.println(instances.toString());
        System.out.println(mapperMethods.toString());
        System.out.println(mapperControllers.toString());
    }

    /**
     * 获取所有的类名称
     *
     * @return
     */
    private static void findAllClassName() {
        //获取当前项目根路径
        URL url = InitHandler.class.getClassLoader().getResource("");
        String baseUrl = ObjectUtil.isEmpty(url) ? "" : url.getFile();
        doFindAllClassName(baseUrl, "");
    }

    /**
     * 获取所有类的路径
     *
     * @param baseUrl     当前路径
     * @param packageName 父类的报名
     */
    private static void doFindAllClassName(String baseUrl, String packageName) {
        File file = new File(baseUrl);
        if (file.exists() && file.isDirectory()) {
            File[] fileList = file.listFiles();
            for (File file1 : fileList) {
                if (file1.isDirectory()) {
                    doFindAllClassName(file1.getAbsolutePath(), packageUtil(packageName) + file1.getName());
                } else {
                    beanNames.add(packageUtil(packageName) + file1.getName());
                }
            }
        } else {
            beanNames.add(file.getName());
        }
    }

    private static String packageUtil(String packageName) {
        if (StringUtils.isEmpty(packageName)) {
            return "";
        }
        return packageName + ".";
    }


    /**
     * 获取所有的有注解的类实例
     *
     * @return
     */
    private static void findAllAnnotaationClassName() {
        if (CollectionUtil.isEmpty(beanNames)) {
            return;
        }
        for (String str : beanNames) {
            String className = str.replace(".class", "").trim();
            try {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Controller.class)) {
                    Object instance = clazz.newInstance();
                    Controller controller = clazz.getAnnotation(Controller.class);
                    instances.put(controller.value(), instance);
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    Object instance = clazz.newInstance();
                    Service service = clazz.getAnnotation(Service.class);
                    instances.put(service.value(), instance);
                } else {
                    continue;
                }
            } catch (ClassNotFoundException e) {
                System.out.println(className + " is not find");
            } catch (Exception e) {
                System.out.println(className + " 实例化失败");
            }
        }
    }

    /**
     * 初始化请求映射关系
     *
     * @return
     */
    private static void handlerMapper() {
        if (MapUtil.isEmpty(instances)) {
            return;
        }
        Iterator<Map.Entry<String, Object>> iterator = instances.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            Class<?> clazz = entry.getValue().getClass();
            if (clazz.isAnnotationPresent(Controller.class)) {
                String baseUrl = "";
                if (clazz.isAnnotationPresent(Mapping.class)) {
                    Mapping mapping = clazz.getAnnotation(Mapping.class);
                    baseUrl = mapping.value();
                }
                Method[] methods = clazz.getMethods();
                if (ObjectUtil.isEmpty(methods)) {
                    continue;
                }
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Mapping.class)) {
                        Mapping mapping = method.getAnnotation(Mapping.class);
                        mapperMethods.put("/" + baseUrl + "/" + mapping.value(), method);
                        mapperControllers.put("/" + baseUrl + "/" + mapping.value(), entry.getValue());
                    }
                }
            }
        }
    }

    /**
     * 如果有依赖注入的类  需要实例化关系
     *
     * @return
     */
    private static void ioc() {
        if (MapUtil.isEmpty(instances)) {
            return;
        }
        Iterator<Map.Entry<String, Object>> iterator = instances.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            Class<?> clazz = entry.getValue().getClass();
            Field[] fields = clazz.getDeclaredFields();
            if (ObjectUtil.isEmpty(fields)) {
                continue;
            }
            for (Field field : fields) {
                if (field.isAnnotationPresent(Qualifier.class)) {
                    Qualifier qualifier = field.getAnnotation(Qualifier.class);
                    try {
                        field.setAccessible(true);
                        field.set(entry.getValue(),instances.get(qualifier.value()));
                    }catch (Exception e){
                        System.out.println(entry.getValue()+"依赖注入失败");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        init();
        System.out.println(beanNames.toString());
        System.out.println(instances.toString());
        System.out.println(mapperMethods.toString());
        System.out.println(mapperControllers.toString());
    }

}
