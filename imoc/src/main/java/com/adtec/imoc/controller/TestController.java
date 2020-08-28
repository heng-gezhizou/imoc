package com.adtec.imoc.controller;

import com.adtec.imoc.service.TokenService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private TokenService tokenService;

    @GetMapping("/getToken")
    public String getToken(){
        return (String) redisTemplate.opsForValue().get("imoc-token");
    }

    @GetMapping("/getCrsfToken")
    public String getCrsfToken(){
        return tokenService.getCrsfToken();
    }

}

