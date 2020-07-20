package com.greenism.eduucenter.service;

import com.greenism.eduucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.greenism.eduucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-12
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //登录
    String login(UcenterMember ucenterMember);

    //注册
    void register(RegisterVo registerVo);

    Integer getByOpenid(String openid);

    UcenterMember getMemberByOpenId(String openid);

    //查询某天注册的人数
    Integer getMemberCount(String day);
}
