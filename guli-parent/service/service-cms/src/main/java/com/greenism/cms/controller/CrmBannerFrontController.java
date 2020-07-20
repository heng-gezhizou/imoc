package com.greenism.cms.controller;


import com.greenism.cms.entity.CrmBanner;
import com.greenism.cms.service.CrmBannerService;
import com.greenism.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class CrmBannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("/getBanner")
    public Result getBannerList(){
        List<CrmBanner> list = crmBannerService.selectBannerList();
        return Result.ok().data("banner",list);
    }
}

