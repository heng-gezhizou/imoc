package com.greenism.order.service.impl;

import com.greenism.commonutils.vo.CourseWebVoOrder;
import com.greenism.commonutils.vo.UcenterMemberOrder;
import com.greenism.order.client.CourseClient;
import com.greenism.order.client.MemberClient;
import com.greenism.order.entity.Order;
import com.greenism.order.mapper.OrderMapper;
import com.greenism.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greenism.order.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-15
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private MemberClient memberClient;

    @Override
    public String createOrder(String courseId, String memberId) {
        CourseWebVoOrder course = courseClient.getCourseOrderById(courseId);
        UcenterMemberOrder member = memberClient.getMemberById(memberId);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(course.getTitle());
        order.setCourseCover(course.getCover());
        order.setTeacherName(course.getTeacherName());
        order.setTotalFee(course.getPrice());
        order.setMemberId(memberId);
        order.setMobile(member.getMobile());
        order.setNickname(member.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
