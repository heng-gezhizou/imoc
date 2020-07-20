package com.greenism.eduservice.controller;


import com.greenism.commonutils.Result;
import com.greenism.eduservice.entity.subject.OneSubject;
import com.greenism.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-06
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("/addSubject")
    public Result addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return Result.ok();
    }

    @GetMapping("/getAllSubject")
    public Result getAllSubject(){
        List<OneSubject> list = subjectService.getAllSubject();
        return Result.ok().data("subject",list);
    }

}

