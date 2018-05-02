package com.mmall.common;

/**
 * Created by Administrator on 2018/4/28.
 */
public class Const {
    public static final String CURRENT_USER ="current_user";
    public static final String EMAIL = "email";
    public static final String USERNAME="username";
    public interface Role{
        int ROLE_CURRENTUSER = 0;//普通用户
        int ROLE_ADMIN = 1;//管理员
    }
}
