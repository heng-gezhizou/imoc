package com.greenism.staservice.controller;


import com.greenism.commonutils.Result;
import com.greenism.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService dailyService;

    //根据日期创建统计数据
    @PostMapping("/createStatisticsByDay/{day}")
    public Result createStatisticsByDay(@PathVariable String day){
        dailyService.createStatisticsByDay(day);
        return Result.ok();
    }

    //根据类型,开始时间,结束时间查询统计数据
    @GetMapping("/getStatisticsData/{type}/{begin}/{end}")
    public Result getStatisticsData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = dailyService.getStatisticsData(type,begin,end);
        return Result.ok().data(map);
    }

}

