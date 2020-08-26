package com.adtec.imoc.service.impl;

import com.adtec.imoc.service.TokenService;
import com.adtec.imoc.utils.ConstantImocUtil;
import com.adtec.imoc.utils.PostUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void getToken() {
        try {
            String url ="https://moccloud.huawei.com/iam-auth/rest/v2/token/iamtoken";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("akvalue", ConstantImocUtil.HUAWEI_IMOC_AKVALUE);
            jsonObject.put("skvalue",ConstantImocUtil.HUAWEI_IMOC_SKID);
            jsonObject.put("tenant",ConstantImocUtil.HUAWEI_IMOC_TENANT);
            jsonObject.put("user",ConstantImocUtil.HUAWEI_IMOC_USER);
            JSONObject result = PostUtil.doPost(url,jsonObject);
            System.out.println(result.get("data"));
            redisTemplate.opsForValue().set("imoc-token",result.get("data"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
