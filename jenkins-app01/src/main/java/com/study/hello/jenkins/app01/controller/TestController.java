package com.study.hello.jenkins.app01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : fangxiangqian
 * @created : 11/27/2023
 **/
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello v0.0.18 canary\n<br/>";
    }


    @Autowired
    private Environment env;

    @RequestMapping("/helloEnv")
    public String helloEnv(HttpServletRequest request) {
        // 获取环境变量参数并打印
        String paramValue = env.getProperty(request.getParameter("PARAM_NAME"));

        // 打印环境变量参数
        String message = "PARAM_NAME: " + request.getParameter("PARAM_NAME") + ", PARAM_VALUE:" + paramValue + "\n";

        return message;
    }
}
