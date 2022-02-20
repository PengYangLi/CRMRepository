package settings.service;


import settings.domain.User;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip);
}
