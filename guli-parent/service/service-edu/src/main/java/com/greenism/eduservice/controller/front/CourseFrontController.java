package com.greenism.eduservice.controller.front;

import com.greenism.commonutils.JwtUtils;
import com.greenism.commonutils.Result;
import com.greenism.eduservice.client.OrderClient;
import com.greenism.eduservice.entity.chapter.ChapterVO;
import com.greenism.eduservice.entity.frontvo.CourseFrontVo;
import com.greenism.eduservice.entity.frontvo.CourseWebVo;
import com.greenism.eduservice.service.EduChapterService;
import com.greenism.eduservice.service.EduCourseService;
import com.greenism.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("/getFrontCourseByPage/{currentPage}/{limit}")
    public Result getFrontCourseByPage(@PathVariable long currentPage,
                                       @PathVariable long limit,
                                       @RequestBody(required = false) CourseFrontVo courseFrontVo){

        Map<String,Object> map = courseService.getCourseByPage(currentPage,limit,courseFrontVo);
        return Result.ok().data("course",map);
    }

    @GetMapping("/getCourseInfoById/{courseId}")
    public Result getCourseInfoById(@PathVariable String courseId,HttpServletRequest request){
        CourseWebVo courseWebVo = courseService.getCourseInfoById(courseId);
        List<ChapterVO> chapterList = chapterService.getChapterVideoByCourseId(courseId);
        boolean isBuy = orderClient.isBuyCourse(courseId,JwtUtils.getMemberIdByJwtToken(request));
        return Result.ok().data("courseWebVo",courseWebVo).data("chapterList",chapterList).data("isBuy",isBuy);
    }
}
