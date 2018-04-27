package com.mmall.controller.portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/4/26.
 */
@Controller
@RequestMapping("/user/")
public class UserController {
    /**
     * 登陆
     * 自动将返回值序列化成json
     * @param userName
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value="login.do",method = RequestMethod.POST)
    @ResponseBody
    public Object login(String userName, String password, HttpSession session){
        //service--myBatic--dao
        return null;
    }
}
