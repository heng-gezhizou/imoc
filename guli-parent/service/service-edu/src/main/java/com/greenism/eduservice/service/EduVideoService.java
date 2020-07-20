package com.greenism.eduservice.service;

import com.greenism.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-06
 */
public interface EduVideoService extends IService<EduVideo> {

    //根据课程id删除小节信息
    void deleteVideoByCourseId(String courseId);
}
