package com.greenism.eduucenter.controller;


import com.greenism.commonutils.JwtUtils;
import com.greenism.commonutils.Result;
import com.greenism.commonutils.vo.UcenterMemberOrder;
import com.greenism.eduucenter.entity.UcenterMember;
import com.greenism.eduucenter.entity.vo.RegisterVo;
import com.greenism.eduucenter.service.UcenterMemberService;
import org.apache.http.HttpRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-12
 */
@RestController
@RequestMapping("/eduucenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @PostMapping("/login")
    public Result login(@RequestBody UcenterMember ucenterMember){
        String token = memberService.login(ucenterMember);
        return Result.ok().data("token",token);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return Result.ok();
    }

    @GetMapping("/getLoginInfo")
    public Result getLoginInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return Result.ok().data("loginUserInfo",member);
    }

    @GetMapping("getMemberById/{id}")
    public UcenterMemberOrder getMemberById(@PathVariable String id){
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //查询某天注册的人数
    @GetMapping("/getMemberCount/{day}")
    public Result getMemberCount(@PathVariable String day){
        Integer count = memberService.getMemberCount(day);
        return Result.ok().data("memberCount",count);
    }

}

