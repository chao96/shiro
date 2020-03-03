package com.springboot.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xuchao
 * @since 2020/3/3 13:37
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/update")
    public String update() {
        return "user/update";
    }

    @RequestMapping("/tologin")
    public String tologin() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(String name, String password, Model model) {
        /**
         * 编写认证方法
         * 1.获取subject
         * 2.封装用户数据
         * 3.执行登录方法
         */
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try {
            subject.login(token);
            //登陆成功，跳转到index.html
            return "redirect:/index";
        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            model.addAttribute("msg", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码不正确");
            return "login";
        }
    }

    @RequestMapping("/unAuth")
    public String unAuth(){
        return "/user/unAuth";
    }
}
