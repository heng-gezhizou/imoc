package com.greenism.eduservice.service;

import com.greenism.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.greenism.eduservice.entity.frontvo.CourseFrontVo;
import com.greenism.eduservice.entity.frontvo.CourseWebVo;
import com.greenism.eduservice.entity.vo.CourseInfoVO;
import com.greenism.eduservice.entity.vo.CoursePublishVO;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-06
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程信息
    String saveCourse(CourseInfoVO courseInfoVO);

    //根据id获取课程的详细信息
    CourseInfoVO getCourseById(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoVO courseInfoVO);

    //获取发布课程信息
    CoursePublishVO getCoursePublish(String courseId);

    //删除课程信息
    void deleteCourse(String courseId);

    //分页条件获取课程信息
    Map<String, Object> getCourseByPage(long currentPage, long limit, CourseFrontVo courseFrontVo);

    //根据课程id查询课程及课程相关信息
    CourseWebVo getCourseInfoById(String courseId);
}
