package com.greenism.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.greenism.eduservice.entity.EduSubject;
import com.greenism.eduservice.entity.excel.SubjectData;
import com.greenism.eduservice.entity.subject.OneSubject;
import com.greenism.eduservice.entity.subject.TwoSubject;
import com.greenism.eduservice.listener.SubjectListener;
import com.greenism.eduservice.mapper.EduSubjectMapper;
import com.greenism.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greenism.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-06
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try{
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectListener(subjectService)).sheet().doRead();
        } catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {


        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();

        //查询一级分类下的所有数据
        wrapper.eq("parent_id","0");
        List<EduSubject> subjectList = baseMapper.selectList(wrapper);

        //查询二级分类下的所有数据
        wrapper1.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapper1);

        List<OneSubject> finalSubject = new ArrayList<>();
        for (EduSubject subject: subjectList) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(subject,oneSubject);
            List<TwoSubject> twoList = new ArrayList<>();

            for (EduSubject subject1: twoSubjectList) {
                TwoSubject twoSubject = new TwoSubject();

                if(subject1.getParentId().equals(subject.getId())){
                    BeanUtils.copyProperties(subject1,twoSubject);
                    twoList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoList);
            finalSubject.add(oneSubject);
        }
        return finalSubject;
    }
}
