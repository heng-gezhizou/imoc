package com.greenism.staservice.service;

import com.greenism.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //根据日期创建统计数据
    void createStatisticsByDay(String day);

    //根据类型,开始时间,结束时间查询统计数据
    Map<String, Object> getStatisticsData(String type, String begin, String end);
}
