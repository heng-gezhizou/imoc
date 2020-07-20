package com.greenism.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greenism.commonutils.Result;
import com.greenism.eduservice.entity.EduCourse;
import com.greenism.eduservice.entity.EduTeacher;
import com.greenism.eduservice.service.EduCourseService;
import com.greenism.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @PostMapping("/getTeacherPage/{currentPage}/{limit}")
    public Result getTeacherPage(@PathVariable long currentPage,@PathVariable long limit){
        Map<String,Object> map = teacherService.getTeacherPage(currentPage,limit);
        return Result.ok().data(map);
    }

    @GetMapping("/getTeacherById/{id}")
    public Result getTeacherById(@PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> courses = courseService.list(wrapper);

        return Result.ok().data("teacher",teacher).data("course",courses);
    }

}
