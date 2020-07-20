package com.greenism.order.controller;


import com.greenism.commonutils.Result;
import com.greenism.order.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-15
 */
@RestController
@RequestMapping("/orderservice/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //生成支付二维码
    @GetMapping("/createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo){
        Map map = payLogService.createNative(orderNo);
        return Result.ok().data(map);
    }

    //查询支付状态
    @GetMapping("/queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if(map == null){
            return Result.error().message("支付出错了");
        }

        if(map.get("trade_state").equals("SUCCESS")){
            //支付成功,更改订单状态
            payLogService.updateOrderStatus(map);
            return Result.ok().message("支付成功");
        }
        return Result.ok().code(25000).message("支付中");
    }

}

