package com.greenism.eduservice.service;

import com.greenism.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.greenism.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-06
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    //获取所有的课程信息(树型)
    List<OneSubject> getAllSubject();
}
