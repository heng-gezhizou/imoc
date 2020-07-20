package com.greenism.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greenism.eduservice.client.VodClient;
import com.greenism.eduservice.entity.EduVideo;
import com.greenism.eduservice.mapper.EduVideoMapper;
import com.greenism.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-06
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void deleteVideoByCourseId(String courseId) {

        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        videoWrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(videoWrapper);

        List<String> eduVideo = new ArrayList<>();
        for (EduVideo video: eduVideoList) {
            String videoSourceId = video.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                eduVideo.add(videoSourceId);
            }
        }

        if(eduVideo.size() > 0){
            vodClient.deleteMoreAliYunVideos(eduVideo);
        }

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
