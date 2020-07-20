package com.greenism.order.client;

import com.greenism.commonutils.vo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface MemberClient {

    @GetMapping("/eduucenter/member/getMemberById/{id}")
    public UcenterMemberOrder getMemberById(@PathVariable("id") String id);
}
