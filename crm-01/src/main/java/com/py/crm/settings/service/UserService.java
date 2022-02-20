package com.py.crm.settings.service;

import com.py.crm.exception.LoginException;
import com.py.crm.settings.domain.User;

public interface UserService {
    User login(String loginAct, String loginPwd,String ip) throws LoginException;
}
