package com.greenism.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greenism.commonutils.Result;
import com.greenism.eduservice.entity.EduCourse;
import com.greenism.eduservice.entity.EduTeacher;
import com.greenism.eduservice.service.EduCourseService;
import com.greenism.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前8热门课程,前四名热门名师
    @GetMapping("/index")
    public Result index(){
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 0,8");
        List<EduCourse> courseList = courseService.list(courseWrapper);

        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 0,4");
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper);

        return Result.ok().data("courses",courseList).data("teachers",teacherList);
    }

}
