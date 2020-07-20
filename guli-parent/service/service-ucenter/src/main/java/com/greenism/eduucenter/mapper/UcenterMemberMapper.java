package com.greenism.eduucenter.mapper;

import com.greenism.eduucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-07-12
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer getMemberCount(String day);
}
