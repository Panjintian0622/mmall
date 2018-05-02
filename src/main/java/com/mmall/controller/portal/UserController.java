package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.omg.CORBA.ServerRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private IUserService iUserService;

    //登陆接口
    @RequestMapping(value="login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String userName, String password, HttpSession session){
        //service--myBatic--dao
        ServerResponse<User> response = iUserService.login(userName,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
    //登出接口
    @RequestMapping(value="logout.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }
    //注册接口
    @RequestMapping(value="register.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> register(User user){
       return iUserService.register(user);
    }
    //校验
    @RequestMapping(value="check_valid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }
    //获取用户信息
    @RequestMapping(value="get_user_info.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户信息");
    }
    //忘记密码
    @RequestMapping(value="forget_get_question.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.forgetGetQuestiom(username);
    }

    //问题答案
    @RequestMapping(value="forget_check_answer.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String>  forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }
    //重置密码
    public ServerResponse<String> forgetResetPassword(String username,String newPassword,String forgetToken){
        return iUserService.forgetResetPassword(username,newPassword,forgetToken);
    }

}
