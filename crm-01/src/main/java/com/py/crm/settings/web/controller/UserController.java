package com.py.crm.settings.web.controller;

import com.py.crm.settings.domain.User;
import com.py.crm.settings.service.UserService;
import com.py.crm.settings.service.impl.UserServiceImpl;
import com.py.crm.utils.MD5Util;
import com.py.crm.utils.PrintJson;
import com.py.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();

        if ("/settings/user/login.do".equals(path)){

            login(req,resp);

        }else if ("/settings/user/xxx.do".equals(path)){

        }

    }

    private void login(HttpServletRequest req, HttpServletResponse resp) {

        String loginAct = req.getParameter("loginAct");
        String loginPwd1 = req.getParameter("loginPwd");
        //转换密码
        String loginPwd = MD5Util.getMD5(loginPwd1);
        //获取ip
        String ip = req.getRemoteAddr();

        //业务层开发
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try {
            User user = us.login(loginAct,loginPwd,ip);
            req.getSession().setAttribute("user",user);

            //程序执行到此处表示登录成功
            PrintJson.printJsonFlag(resp,true);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(resp,map);
        }
    }
}
