package com.adtec.imoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/getToken")
    public String getToken(){
        return (String) redisTemplate.opsForValue().get("imoc-token");
    }

}
