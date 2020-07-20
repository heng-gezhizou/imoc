package com.greenism.eduservice.service;

import com.greenism.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-29
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String,Object> getTeacherPage(long currentPage, long limit);
}
