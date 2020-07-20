package com.greenism.order.client;

import com.greenism.commonutils.vo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface CourseClient {

    @GetMapping("/eduservice/course/getCourseOrderById/{courseId}")
    public CourseWebVoOrder getCourseOrderById(@PathVariable("courseId") String courseId);
}
