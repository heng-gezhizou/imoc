package com.greenism.eduucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greenism.commonutils.JwtUtils;
import com.greenism.commonutils.MD5;
import com.greenism.eduucenter.entity.UcenterMember;
import com.greenism.eduucenter.entity.vo.RegisterVo;
import com.greenism.eduucenter.mapper.UcenterMemberMapper;
import com.greenism.eduucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greenism.servicebase.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-12
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private UcenterMemberMapper memberMapper;

    @Override
    public String login(UcenterMember ucenterMember) {

        String phone = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();

        //手机号与密码的非空校验
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"手机号或密码不能为空");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",phone);
        UcenterMember member = baseMapper.selectOne(wrapper);

        //根据查询出的结果判断手机号真伪
        if(member == null){
            throw new GuliException(20001,"手机号错误");
        }

        //判断密码
        if(!MD5.encrypt(password).equals(member.getPassword())){
            throw new GuliException(20001,"密码错误");
        }

        //判断该账户是否被禁用
        if(member.getIsDisabled()){
            throw new GuliException(20001,"改账户已被禁用,请联系管理员进行处理");
        }

        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {

        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //非空校验
        if(StringUtils.isEmpty(nickname) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)){
            throw new GuliException(20001,"请将要注册的信息补全");
        }


        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        //判断手机号是否已经注册
        if(count > 0){
            throw new GuliException(20001,"该手机号已经注册");
        }

        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!redisCode.equals(code)){
            throw new GuliException(20001,"验证码错误或超时");
        }

        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        member.setIsDisabled(false);
        baseMapper.insert(member);

    }

    @Override
    public Integer getByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public UcenterMember getMemberByOpenId(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Integer getMemberCount(String day) {
        return memberMapper.getMemberCount(day);
    }
}
