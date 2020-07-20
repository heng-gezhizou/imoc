package com.greenism.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.greenism.eduservice.entity.EduCourse;
import com.greenism.eduservice.entity.EduCourseDescription;
import com.greenism.eduservice.entity.frontvo.CourseFrontVo;
import com.greenism.eduservice.entity.frontvo.CourseWebVo;
import com.greenism.eduservice.entity.vo.CourseInfoVO;
import com.greenism.eduservice.entity.vo.CoursePublishVO;
import com.greenism.eduservice.mapper.EduCourseMapper;
import com.greenism.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greenism.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-06
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;

    @Override
    public String saveCourse(CourseInfoVO courseInfoVO) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert <= 0){
            throw new GuliException(20001,"添加课程信息失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        eduCourseDescription.setId(eduCourse.getId());
        eduCourseDescriptionService.save(eduCourseDescription);

        return eduCourse.getId();
    }

    @Override
    public CourseInfoVO getCourseById(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        BeanUtils.copyProperties(eduCourse,courseInfoVO);
        EduCourseDescription description = eduCourseDescriptionService.getById(courseId);
        courseInfoVO.setDescription(description.getDescription());
        return courseInfoVO;
    }

    @Override
    public void updateCourseInfo(CourseInfoVO courseInfoVO) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0){
            throw new GuliException(20001,"修改课程信息失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVO.getId());
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVO getCoursePublish(String courseId) {
        return baseMapper.getCoursePublish(courseId);
    }

    @Override
    public void deleteCourse(String courseId) {
        //删除章节信息
        chapterService.deleteChapterByCourseId(courseId);
        //删除描述信息
        eduCourseDescriptionService.removeById(courseId);
        //删除小节信息
        videoService.deleteVideoByCourseId(courseId);
        //删除课程信息
        baseMapper.deleteById(courseId);
    }

    @Override
    public Map<String, Object> getCourseByPage(long currentPage, long limit, CourseFrontVo courseFrontVo) {
        Page<EduCourse> page = new Page<>(currentPage,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        System.out.println("currentPage:"+currentPage);
        System.out.println("limit:"+limit);
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            wrapper.orderByDesc("price");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            wrapper.orderByDesc("gmt_create");
        }
        baseMapper.selectPage(page,wrapper);
        List<EduCourse> records = page.getRecords();
        long current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getCourseInfoById(String courseId) {
        return baseMapper.selectCourseInfoById(courseId);
    }
}
