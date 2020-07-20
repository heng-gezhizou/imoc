package com.greenism.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.greenism.commonutils.Result;
import com.greenism.commonutils.vo.CourseWebVoOrder;
import com.greenism.eduservice.entity.EduCourse;
import com.greenism.eduservice.entity.frontvo.CourseWebVo;
import com.greenism.eduservice.entity.vo.CourseInfoVO;
import com.greenism.eduservice.entity.vo.CoursePublishVO;
import com.greenism.eduservice.entity.vo.CourseQuery;
import com.greenism.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-06
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService eduCourseService;

    //添加课程信息
    @PostMapping("/addCourse")
    public Result addCourse(@RequestBody CourseInfoVO courseInfoVO){
        String id = eduCourseService.saveCourse(courseInfoVO);
        return Result.ok().data("id",id);
    }

    //根据id获取课程的详细信息
    @GetMapping("/getCourseInfoById/{courseId}")
    public Result getCourseById(@PathVariable String courseId){
        CourseInfoVO courseInfoVO = eduCourseService.getCourseById(courseId);
        return Result.ok().data("courseInfo",courseInfoVO);
    }

    //修改课程信息
    @PostMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVO courseInfoVO){
        eduCourseService.updateCourseInfo(courseInfoVO);
        return Result.ok();
    }

    //获取发布课程信息
    @GetMapping("/getCoursePublish/{courseId}")
    public Result getCoursePublish(@PathVariable String courseId){
        CoursePublishVO coursePublish = eduCourseService.getCoursePublish(courseId);
        return Result.ok().data("coursePublish",coursePublish);
    }

    //发布课程
    @GetMapping("/updateCoursePublish/{courseId}")
    public Result updateCoursePublish(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return Result.ok();
    }

    //分页获取课程基本信息
    @PostMapping("/getCourseInfoByPage/{current}/{limit}")
    public Result getCourseInfoByPage(@PathVariable long current,@PathVariable long limit,@RequestBody CourseQuery courseQuery){
        Page<EduCourse> page = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseQuery.getTitle())){
            wrapper.like("title",courseQuery.getTitle());
        }
        if(!StringUtils.isEmpty(courseQuery.getStatus())){
            if(courseQuery.getStatus() == 1){
                wrapper.eq("status","Normal");
            }else{
                wrapper.eq("status","Draft");
            }

        }
        if(!StringUtils.isEmpty(courseQuery.getBegin())){
            wrapper.ge("gmt_create",courseQuery.getBegin());
        }
        if(!StringUtils.isEmpty(courseQuery.getEnd())){
            wrapper.le("gmt_create",courseQuery.getEnd());
        }
        wrapper.orderByDesc("gmt_create");
        eduCourseService.page(page,wrapper);
        return Result.ok().data("total",page.getTotal()).data("course",page.getRecords());
    }

    //删除课程信息
    @DeleteMapping("{courseId}")
    public Result deleteCourse(@PathVariable String courseId){
        eduCourseService.deleteCourse(courseId);
        return Result.ok();
    }

    //根据课程id查询课程信息,远程调用使用
    @GetMapping("/getCourseOrderById/{courseId}")
    public CourseWebVoOrder getCourseOrderById(@PathVariable String courseId){
        CourseWebVo course = eduCourseService.getCourseInfoById(courseId);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(course,courseWebVoOrder);
        return courseWebVoOrder;
    }
}

