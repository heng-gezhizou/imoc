package com.greenism.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greenism.commonutils.JwtUtils;
import com.greenism.commonutils.Result;
import com.greenism.order.entity.Order;
import com.greenism.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-15
 */
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder/{courseId}")
    public Result createOrder(@PathVariable String courseId, HttpServletRequest request){
        //创建订单接口
        String orderId = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return Result.ok().data("orderId",orderId);
    }

    @GetMapping("/getOrderById/{id}")
    public Result getOrderById(@PathVariable String id){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",id);
        Order order = orderService.getOne(wrapper);
        return Result.ok().data("order",order);
    }

    //根据课程id跟用户id查询该用户有没有购买该课程
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if(count ==1){
            return true;
        }else {
            return false;
        }
    }
}

