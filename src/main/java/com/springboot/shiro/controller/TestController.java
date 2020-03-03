package com.springboot.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xuchao
 * @since 2020/3/3 11:28
 */
@Controller
public class TestController {

    @GetMapping(value = {"/", "/index"})
    public String testHtml(){
        System.out.println("进入首页index.html");
        return "index";
    }
}
