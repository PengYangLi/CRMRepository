package com.py.crm.settings.service;

import com.py.crm.exception.LoginException;
import com.py.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd,String ip) throws LoginException;

    List<User> getUserList();
}
