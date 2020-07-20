package com.greenism.eduservice.client;

import com.greenism.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@FeignClient( value = "service-vod",fallback = VodClientImpl.class)
@FeignClient( value = "service-vod")
@Component
public interface VodClient {
    //一定要写这个@PathVariable("id"),不然会出bug
    @DeleteMapping("/eduvod/file/deleteAliYunVideo/{id}")
    public Result deleteAliYunVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/file/deleteMoreAliYunVideos")
    public Result deleteMoreAliYunVideos(@RequestParam("videoIdList") List<String> videoIdList);
}
