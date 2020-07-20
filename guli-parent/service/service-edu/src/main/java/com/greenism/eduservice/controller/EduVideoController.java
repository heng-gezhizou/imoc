package com.greenism.eduservice.controller;


import com.greenism.commonutils.Result;
import com.greenism.eduservice.client.VodClient;
import com.greenism.eduservice.entity.EduVideo;
import com.greenism.eduservice.service.EduVideoService;
import com.greenism.servicebase.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-06
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return Result.ok();
    }

    //删除小节
    @DeleteMapping("{videoId}")
    public Result deleteVideo(@PathVariable String videoId){
        //根据小节id获取小节信息
        EduVideo eduVideo = eduVideoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            Result result = vodClient.deleteAliYunVideo(videoSourceId);
            if(result.getCode() == 20001){
                throw new GuliException(20001,"删除视频失败了,hystrix");
            }
        }
        eduVideoService.removeById(videoId);
        return Result.ok();
    }

    //修改小节
    @PostMapping("/updateVideo")
    public Result updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return Result.ok();
    }

    //根据id获取小节信息
    @GetMapping("/getVideo/{videoId}")
    public Result getVideo(@PathVariable String videoId){
        EduVideo video = eduVideoService.getById(videoId);
        return Result.ok().data("video",video);
    }

}

