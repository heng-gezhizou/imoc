package com.greenism.eduservice.controller;


import com.greenism.commonutils.Result;
import com.greenism.eduservice.entity.EduChapter;
import com.greenism.eduservice.entity.chapter.ChapterVO;
import com.greenism.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-06
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //根据课程id查询该课程下的所有章节
    @GetMapping("/getAllChapter/{courseId}")
    public Result getAllChapter(@PathVariable String courseId){
        List<ChapterVO> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return Result.ok().data("chapterVideoList",list);
    }

    //新增章节
    @PostMapping("/addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return Result.ok();
    }

    //根据id查询章节(数据回显用的)
    @GetMapping("/getChapterById/{chapterId}")
    public Result getChapterById(@PathVariable String chapterId){
        EduChapter chapter = eduChapterService.getById(chapterId);
        return Result.ok().data("chapter",chapter);
    }

    //根据id修改章节
    @PostMapping("/updateChapterById")
    public Result updateChapterById(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return Result.ok();
    }

    //根据id删除章节
    @DeleteMapping("{id}")
    public Result deleteChapterById(@PathVariable String id){
        int flag = eduChapterService.deleteChapterById(id);
        if(flag == 1){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

}

