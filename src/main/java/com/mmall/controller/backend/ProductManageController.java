package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.google.zxing.common.StringUtils;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

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
    @Autowired
    private IFileService iFileService;

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
    //文件上传
    @RequestMapping(value="upload.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(HttpSession session,@RequestParam(value="upl_file",required = false)MultipartFile file , HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        if(iUserService.checkAdminEole(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetName = iFileService.upload(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetName;
            Map map = Maps.newHashMap();
            map.put("uri",targetName);
            map.put("url",url);
            return ServerResponse.createBySuccess(map);
        }else{
            return ServerResponse.createByErrorMessage("没有操作权限");
        }


    }
    //富文本上传
    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpSession session, @RequestParam(value="upload_file",required = false)MultipartFile file , HttpServletRequest request, HttpServletResponse response){
        Map map = Maps.newHashMap();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            map.put("success",false);
            map.put("msg","请登陆管理员");
            return map;
        }
        //富文本中对于返回值有自己的要求，我们使用simditor的要求进行返回
        //
        if(iUserService.checkAdminEole(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetName = iFileService.upload(file,path);
            if(org.apache.commons.lang3.StringUtils.isBlank(targetName)){
                map.put("success",false);
                map.put("msg","上传文件失败");
                return map;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetName;
            map.put("success",true);
            map.put("msg","上传文件成功");
            map.put("file_path",url);
            //增加header
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return map;
        }else{
            map.put("success",false);
            map.put("msg","无权限操作");
            return map;
        }


    }

}
