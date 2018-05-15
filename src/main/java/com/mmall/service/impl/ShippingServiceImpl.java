package com.mmall.service.impl;

import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/15.
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService{

    @Autowired
    private ShippingMapper shippingMapper;
    public ServerResponse add(Integer userId,Shipping shipping){
        shipping.setUserId(userId);
        int rowCount =shippingMapper.insert(shipping);
        if(rowCount>0){
            Map result = Maps.newHashMap();
            result.put("shipping",shipping.getId());
            return ServerResponse.createBySuccess("添加地址成功",result);
        }
        return ServerResponse.createByErrorMessage("添加地址失败");
    }

    public ServerResponse<String> del(Integer userId,Integer shippingId){
        //存在横向越权问题
        //int resultCount = shippingMapper.deleteByPrimaryKey(shippingId);
        int resultCount = shippingMapper.deleteByShippingIdAndUserId(userId,shippingId);
        if(resultCount>0){
            return ServerResponse.createBySuccess("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");

    }

    public ServerResponse update(Integer userId,Shipping shipping){
        shipping.setUserId(userId);
        int rowCount =shippingMapper.updateByShipping(shipping);
        if(rowCount>0){
            return ServerResponse.createBySuccess("添加地址成功");
        }
        return ServerResponse.createByErrorMessage("添加地址失败");
    }

}
