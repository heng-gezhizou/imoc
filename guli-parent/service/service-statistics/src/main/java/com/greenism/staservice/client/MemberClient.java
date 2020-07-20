package com.greenism.staservice.client;

import com.greenism.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface MemberClient {
    //查询某天注册的人数
    @GetMapping("/eduucenter/member/getMemberCount/{day}")
    public Result getMemberCount(@PathVariable("day") String day);
}
