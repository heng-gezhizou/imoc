package com.greenism.cms.service;

import com.greenism.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectBannerList();
}
