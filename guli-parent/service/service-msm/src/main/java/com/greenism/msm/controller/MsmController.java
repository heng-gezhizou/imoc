package com.greenism.msm.controller;

import com.greenism.commonutils.Result;
import com.greenism.msm.service.MsmService;
import com.greenism.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/phone")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/sendMsg/{phone}")
    public Result sendMsg(@PathVariable String phone){
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) return Result.ok();
        System.out.println(phone);
        code = RandomUtil.getSixBitRandom();
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        boolean isSend = msmService.send(phone,"SMS_195872310",map);
        if(isSend){
            redisTemplate.opsForValue().set(phone,code,5,TimeUnit.MINUTES);
            return Result.ok();
        }else{
            return Result.error().message("发送短信失败");
        }
    }

}
