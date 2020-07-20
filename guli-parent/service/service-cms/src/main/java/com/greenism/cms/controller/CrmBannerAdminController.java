package com.greenism.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.greenism.cms.entity.CrmBanner;
import com.greenism.cms.service.CrmBannerService;
import com.greenism.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class CrmBannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/getBannerByPage/{currentPage}/{limit}")
    public Result getBannerByPage(@PathVariable long currentPage,@PathVariable long limit){
        Page<CrmBanner> page = new Page<>(currentPage,limit);
        bannerService.page(page,null);
        return Result.ok().data("banner",page.getRecords()).data("total",page.getTotal());
    }

    @GetMapping("/getBannerById/{id}")
    public Result getBannerById(@PathVariable String id){
        CrmBanner banner = bannerService.getById(id);
        return Result.ok().data("banner",banner);
    }

    @PostMapping("/updateBanner")
    public Result updateBanner(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);
        return Result.ok();
    }

    @PostMapping("/addBanner")
    public Result addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return Result.ok();
    }

    @DeleteMapping("{id}")
    public Result deleteBanner(@PathVariable String id){
        bannerService.removeById(id);
        return Result.ok();
    }
}
