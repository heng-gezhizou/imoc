package com.greenism.eduservice.mapper;

import com.greenism.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.greenism.eduservice.entity.frontvo.CourseWebVo;
import com.greenism.eduservice.entity.vo.CoursePublishVO;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-07-06
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVO getCoursePublish(String courseId);

    CourseWebVo selectCourseInfoById(String courseId);
}
