package com.greenism.eduucenter.controller;

import com.google.gson.Gson;
import com.greenism.commonutils.JwtUtils;
import com.greenism.eduucenter.entity.UcenterMember;
import com.greenism.eduucenter.service.UcenterMemberService;
import com.greenism.eduucenter.utils.ConstantWxUtil;
import com.greenism.eduucenter.utils.HttpClientUtils;
import com.greenism.servicebase.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

@Controller
//@RequestMapping("/eduucenter/member/wx")
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("/callback")
    public String callback(String code,String state){
        try{
            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String baseUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtil.WX_OPEN_APP_ID,
                    ConstantWxUtil.WX_OPEN_APP_SECRET,
                    code
            );

            String accessTokenJson = HttpClientUtils.get(baseUrl);
            Gson gson = new Gson();
            HashMap map = gson.fromJson(accessTokenJson, HashMap.class);

            String accessToken = (String) map.get("access_token");
            String openid = (String) map.get("openid");

            //查询数据库,当前用户是否曾经使用过微信登录
            UcenterMember member = memberService.getMemberByOpenId(openid);
            if(member == null){
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                String userInfoJson = HttpClientUtils.get(userInfoUrl);

                HashMap userInfoMap = gson.fromJson(userInfoJson, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");

                member = new UcenterMember();
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                member.setOpenid(openid);

                memberService.save(member);
            }
            System.out.println(member.getId());
            String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());
            return "redirect:http://localhost:3000?token=" +token;
        } catch(Exception e){
                throw new GuliException(20001,"微信登录失败");
        }
    }

    @GetMapping("/login")
    public String getWxCode(){
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirectUrl = ConstantWxUtil.WX_OPEN_REDIRECT_URL;
        try{
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        } catch(Exception e){
            e.printStackTrace();
        }

        String url = String.format(
                baseUrl,
                ConstantWxUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );
        return "redirect:"+url;
    }
}
