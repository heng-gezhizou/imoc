package com.greenism.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.greenism.commonutils.Result;
import com.greenism.eduservice.entity.EduTeacher;
import com.greenism.eduservice.entity.vo.TeacherQuery;
import com.greenism.eduservice.service.EduTeacherService;
import com.greenism.servicebase.exception.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-29
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //获取所有讲师信息
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/getAllTeacher")
    public Result getAll(){
        List<EduTeacher> list = teacherService.list(null);
        return Result.ok().data("items",list);
    }

    //逻辑删除讲师信息
    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("{id}")
    public Result deleteById(
            @ApiParam(name = "id",value = "讲师id",required = true)
            @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @ApiOperation(value = "分页查询讲师信息")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public Result getPageTeacher(
            @PathVariable long current,
            @PathVariable long limit){
        Page<EduTeacher> page = new Page<>(current,limit);
        teacherService.page(page,null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        //链式编程
        return Result.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "分页条件查询讲师信息")
    @PostMapping("/pageQuery/{current}/{limit}")
    public Result pageQuery(@PathVariable long current,
                            @PathVariable long limit,
                            @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> page = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(teacherQuery.getName())){
            wrapper.like("name",teacherQuery.getName());
        }
        if(!StringUtils.isEmpty(teacherQuery.getLevel())){
            wrapper.eq("level",teacherQuery.getLevel());
        }
        if(!StringUtils.isEmpty(teacherQuery.getBegin())){
            wrapper.ge("gmt_create",teacherQuery.getBegin());
        }
        if(!StringUtils.isEmpty(teacherQuery.getEnd())){
            wrapper.le("gmt_create",teacherQuery.getEnd());
        }
        //按照时间降序排序
        wrapper.orderByDesc("gmt_create");
        teacherService.page(page,wrapper);
        return Result.ok().data("total",page.getTotal()).data("teacher",page.getRecords());
    }

    @ApiOperation(value = "添加讲师信息")
    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if(save){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @ApiOperation(value = "根据id获取讲师信息")
    @GetMapping("/getTeacherById/{id}")
    public Result getTeacherById(@PathVariable String id){
//        try {
//            int i = 10/0;
//        } catch (Exception e) {
//            throw new GuliException(10001,"执行了自定义异常");
//        }
        EduTeacher teacher = teacherService.getById(id);
        return Result.ok().data("teacher",teacher);
    }

    @ApiOperation(value = "修改讲师信息")
    @PostMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}

