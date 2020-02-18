package com.qianfeng.openapi.web.master.controller;

import com.qianfeng.openapi.web.master.service.MenuService;
import com.qianfeng.openapi.web.master.util.AjaxMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SystemController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("logout")
    public AjaxMessage logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new AjaxMessage(true);
    }

    @RequestMapping("dologin")
    public Map<String, Object> login(String email, String password) {
        Map<String, Object> result = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);
        try {
            subject.login(token);
            result.put("code", 0);
            System.out.println("成功=================");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            result.put("msg", "用户名或密码错误");
        }
        return result;

    }

    @RequestMapping("auth_error.html")
    public String error() {
        return "error";
    }

    @RequestMapping("side")
    public Map<String, Object> getMenuTree() {

        Subject subject = SecurityUtils.getSubject();
        Map<String, Object> result  = menuService.getUserPermission(Integer.parseInt(subject.getPrincipal().toString()));
        result.put("code", 0);
        return result;
    }
}
