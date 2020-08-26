package com.adtec.imoc.schedule;

import com.adtec.imoc.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    @Autowired
    private TokenService tokenService;

    @Scheduled(cron = "* 0/15 * * * ? ")
    public void getToken(){
        tokenService.getToken();
    }

}
