package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/5/6.
 */
@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;

    //保存产品
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session,Product product){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        if(iUserService.checkAdminEole(user).isSuccess()){
            //增加产品的业务逻辑
            return iProductService.saveOrUpdateProduct(product);
        }else{
            return ServerResponse.createByErrorMessage("没有操作权限");
        }
    }
    //修改产品销售状态
    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session,Integer productId,Integer status){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        if(iUserService.checkAdminEole(user).isSuccess()){
            //增加产品的业务逻辑
           return iProductService.setSaleStatus(productId,status);
        }else{
            return ServerResponse.createByErrorMessage("没有操作权限");
        }
    }
    //获取商品详情
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session,Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        if(iUserService.checkAdminEole(user).isSuccess()){
            //获取商品详情
            return iProductService.manageProductDetail(productId);
        }else{
            return ServerResponse.createByErrorMessage("没有操作权限");
        }
    }
    //获取商品列表
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        if(iUserService.checkAdminEole(user).isSuccess()){
            //获取商品详情
            return iProductService.getProductList(pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("没有操作权限");
        }
    }
    //搜索商品
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse getSearch(HttpSession session,String productName,Integer productId,  @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        if(iUserService.checkAdminEole(user).isSuccess()){
            return iProductService.searchProduct(productName,productId,pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("没有操作权限");
        }
    }

}
