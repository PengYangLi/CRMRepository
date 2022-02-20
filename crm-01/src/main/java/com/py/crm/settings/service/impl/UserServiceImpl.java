package com.py.crm.settings.service.impl;

import com.py.crm.exception.LoginException;
import com.py.crm.settings.dao.UserDao;
import com.py.crm.settings.domain.User;
import com.py.crm.settings.service.UserService;
import com.py.crm.utils.DateTimeUtil;
import com.py.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd,String ip) throws LoginException {

        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = userDao.login(map);

        if (user == null){
            throw new LoginException("账号密码错误！");
        }

        //继续验证失效时间
        String expireTime = user.getExpireTime();
        String sysTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(sysTime) < 0){
            throw new LoginException("账号已失效！");
        }

        //验证账号状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw new LoginException("账号已锁定！");
        }

        //验证ip
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new LoginException("ip地址受限！");
        }

        return user;
    }
}
