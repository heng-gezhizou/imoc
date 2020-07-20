package com.greenism.staservice.schedule;

import com.greenism.staservice.service.StatisticsDailyService;
import com.greenism.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService dailyService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduleTask(){
        dailyService.createStatisticsByDay(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }

}
