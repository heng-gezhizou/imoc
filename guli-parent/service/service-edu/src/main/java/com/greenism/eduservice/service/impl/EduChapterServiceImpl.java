package com.greenism.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greenism.eduservice.entity.EduChapter;
import com.greenism.eduservice.entity.EduVideo;
import com.greenism.eduservice.entity.chapter.ChapterVO;
import com.greenism.eduservice.entity.chapter.VideoVO;
import com.greenism.eduservice.mapper.EduChapterMapper;
import com.greenism.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greenism.eduservice.service.EduVideoService;
import com.greenism.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-06
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVO> getChapterVideoByCourseId(String courseId) {

        //获取该课程的所有章节信息
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(chapterWrapper);

        //获取该课程的所有小节信息
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideos = eduVideoService.list(videoWrapper);

        //封装最终数据集合
        List<ChapterVO> finalList = new ArrayList<>();

        //封装章节信息
        for (EduChapter eduChapter : eduChapters) {
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(eduChapter,chapterVO);

            List<VideoVO> videoVOList = new ArrayList<>();
            //封装小结信息
            for (EduVideo eduVideo : eduVideos) {
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(eduVideo,videoVO);
                    videoVOList.add(videoVO);
                }
            }
            chapterVO.setChildren(videoVOList);
            finalList.add(chapterVO);
        }

        return finalList;
    }

    @Override
    public int deleteChapterById(String id) {
        //判断该章节下是否还有小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        //获取该章节下小节的数量
        int count = eduVideoService.count(wrapper);
        if(count > 0){
            throw new GuliException(20001,"该章节下还有小节,不可以删除!");
        }else{
            int i = baseMapper.deleteById(id);
            return i;
        }
    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
