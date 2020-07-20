package com.greenism.eduservice.service;

import com.greenism.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.greenism.eduservice.entity.chapter.ChapterVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-06
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVO> getChapterVideoByCourseId(String courseId);

    //根据id删除章节
    int deleteChapterById(String id);

    //根据课程id删除章节信息
    void deleteChapterByCourseId(String courseId);
}
