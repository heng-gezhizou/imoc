package com.greenism.oss.controller;

import com.greenism.commonutils.Result;
import com.greenism.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/file")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file){
        String url = ossService.uploadFile(file);
        return Result.ok().message("头像上传成功").data("url",url);
    }
}
