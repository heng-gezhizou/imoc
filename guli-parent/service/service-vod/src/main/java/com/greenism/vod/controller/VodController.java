package com.greenism.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.greenism.commonutils.Result;
import com.greenism.servicebase.exception.GuliException;
import com.greenism.vod.service.VodService;
import com.greenism.vod.utils.AliyunVodSDKUtils;
import com.greenism.vod.utils.ConstantUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/file")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @GetMapping("/getVideoAuth/{videoId}")
    public Result getVideoAuth(@PathVariable String videoId){
        try{
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantUtil.KEY_ID, ConstantUtil.KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.ok().data("videoPlayAuth",playAuth);
        } catch(Exception e){}
                throw new GuliException(20001,"获取阿里云视频凭证失败");
    }

    //上传视频到阿里云
    @PostMapping("/uploadVideoToAliYun")
    public Result uploadVideoToAliYun(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return Result.ok().data("videoId",videoId);
    }

    @DeleteMapping("/deleteAliYunVideo/{id}")
    public Result deleteAliYunVideo(@PathVariable String id){
        try{
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantUtil.KEY_ID, ConstantUtil.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return Result.ok();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }

    }

    @DeleteMapping("/deleteMoreAliYunVideos")
    public Result deleteMoreAliYunVideos(@RequestParam("videoIdList") List<String> videoIdList){
        try{
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantUtil.KEY_ID, ConstantUtil.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //将传过来的视频id转换成11,22,33这种形式
            String join = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(join);
            client.getAcsResponse(request);
            return Result.ok();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"批量删除视频失败");
        }
    }



}
