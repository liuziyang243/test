package com.crscd.passengerservice.app;

import com.crscd.framework.restful.server.RestfulApplication;
import com.crscd.framework.util.base.ContextUtil;

/**
 * Created by zs
 * on 2017/8/10.
 *
 * @author Administrator
 */
public class App {
    public static void main(String[] args) throws Exception {

        /************ 启动后台服务 ***********/
        BackgroundService backgroundService = ContextUtil.getInstance().getBean("backgroundService", BackgroundService.class);
//        backgroundService.init();

        /************ 发布SOAP接口 ***********/
        // spring-aop生成的代理类没有类注解，因此下面直接调用会报错
        // 找不到@WebService注解
        // PubSoapService.pub();
        // 替代的方法是使用spring

        /************ 发布restful接口 ***********/
        new RestfulApplication().run(args);

        System.out.println("Publish Service Success!");
    }
}
