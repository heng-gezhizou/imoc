package com.greenism.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greenism.commonutils.Result;
import com.greenism.staservice.client.MemberClient;
import com.greenism.staservice.entity.StatisticsDaily;
import com.greenism.staservice.mapper.StatisticsDailyMapper;
import com.greenism.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private MemberClient memberClient;

    @Override
    public void createStatisticsByDay(String day) {

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        Result result = memberClient.getMemberCount(day);
        Integer memberCount = (Integer) result.getData().get("memberCount");
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(memberCount);

        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setDateCalculated(day);

        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> getStatisticsData(String type, String begin, String end) {

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> list = baseMapper.selectList(wrapper);
        List<String> dateList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for (StatisticsDaily item : list) {
            dateList.add(item.getDateCalculated());
            switch (type){
                case "register_num":
                    countList.add(item.getRegisterNum());
                    break;
                case "login_num":
                    countList.add(item.getLoginNum());
                    break;
                case "video_view_num":
                    countList.add(item.getVideoViewNum());
                    break;
                case "course_num":
                    countList.add(item.getCourseNum());
                    break;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("dateList",dateList);
        map.put("countList",countList);
        return map;
    }
}
