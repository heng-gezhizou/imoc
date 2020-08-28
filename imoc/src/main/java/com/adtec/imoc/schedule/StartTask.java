package com.adtec.imoc.schedule;

import com.adtec.imoc.service.TokenService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class StartTask implements ApplicationRunner {

    @Resource
    private TokenService tokenService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        tokenService.getToken();
    }

}

