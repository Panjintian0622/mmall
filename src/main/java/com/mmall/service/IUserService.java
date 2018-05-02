package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * Created by Administrator on 2018/4/26.
 */
public interface IUserService {
    ServerResponse<User> login(String userName, String password);
    ServerResponse<String> register(User user);
    ServerResponse<String> checkValid(String str,String type);
    ServerResponse forgetGetQuestiom(String username);
    ServerResponse<String> checkAnswer(String username,String question,String answer);
    ServerResponse<String> forgetResetPassword(String username,String newPassword,String forgetToken);
}
