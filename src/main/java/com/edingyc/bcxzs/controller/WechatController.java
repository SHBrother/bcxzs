package com.edingyc.bcxzs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edingyc.bcxzs.LogManage.BaseLog;
import com.edingyc.bcxzs.Utils.CacheUtils;
import com.edingyc.bcxzs.Utils.MD5Util;
import com.edingyc.bcxzs.Utils.ResultUtil;
import com.edingyc.bcxzs.Utils.config.AppletConfig;
import com.edingyc.bcxzs.Utils.config.PcConfig;
import com.edingyc.bcxzs.dto.ResultDTO;
import com.edingyc.bcxzs.dto.UserDTO;
import com.edingyc.bcxzs.exception.ExceptionMsg;
import com.edingyc.bcxzs.exception.WrapException;
import com.edingyc.bcxzs.service.CarShareService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: bcxzs
 * @description: wechat controller
 * @author: YSM
 * @create: 2018-09-28 15:21
 **/

@RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    AppletConfig appletConfig;
    @Autowired
    PcConfig pcConfig;
    @Autowired
    CarShareService carShareService;

    @GetMapping("/login")
    public ResultDTO login(String code,String dispatcherId) throws WrapException{
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = appletConfig.getLoginUrl() + "?" + "appid=" +appletConfig.getAppid() +"&js_code=" +code + "&secret="+appletConfig.getSecret()+"&grant_type="+appletConfig.getGrant_type();
            String loginResp = restTemplate.getForObject(url, String.class);
            log.info("loginResp={}",loginResp);

            JSONObject jo = JSON.parseObject(loginResp);
            String openid = (String)jo.get("openid");
            String session_key = (String)jo.get("session_key");
            if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(session_key)){
                throw new WrapException(ExceptionMsg.LoginError.getCode(),ExceptionMsg.LoginError.getMsg());
            }
            String value = openid +","+session_key+","+ RandomStringUtils.randomAlphanumeric(5);
            String key = MD5Util.encrypt(value);
            CacheUtils.setCache(key,value);
            Map<String,String> map = new HashMap<>();
            map.put("Authorization",key);

            if (!StringUtils.isEmpty(dispatcherId)){
                carShareService.unionId(openid,dispatcherId);
            }

            return ResultUtil.success(map);
        }catch (Exception e){
            throw new WrapException(ExceptionMsg.LoginError.getCode(),ExceptionMsg.LoginError.getMsg());
        }
    }

    @GetMapping("/webLogin/{code}")
    public ResultDTO webLogin(@PathVariable String code) throws WrapException{
        try {
            /*String url = pcConfig.getAccessTokenUrl()+"?"+"appid=" +pcConfig.getAppid() +"&code=" +code + "&secret="+pcConfig.getSecret()+"&grant_type="+pcConfig.getGrant_type();
            RestTemplate restTemplate = new RestTemplate();
            String loginResp = restTemplate.getForObject(url, String.class);
            JSONObject jo = JSON.parseObject(loginResp);
            String openid = (String)jo.get("openid");
            String access_token = (String)jo.get("access_token");
            if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(access_token)){
                throw new WrapException(ExceptionMsg.LoginError.getCode(),ExceptionMsg.LoginError.getMsg());
            }

            UserDTO userDTO = carShareService.getUserInfo(openid);
            String unionid = userDTO.getUnionid();
            if (StringUtils.isEmpty(unionid)){
                String userInfoUrl = pcConfig.getUserInfoUrl()+"?"+"access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
                String userInfoResp = restTemplate.getForObject(userInfoUrl, String.class);
                UserDTO userDTO1 = JSON.parseObject(userInfoResp,UserDTO.class);
                unionid = userDTO1.getUnionid();
                userDTO1.setUserId(unionid);
                carShareService.addUserInfo(userDTO1);
            }

            String value = unionid +","+access_token+","+ RandomStringUtils.randomAlphanumeric(5);
            String key = MD5Util.encrypt(value);*/
            String value = RandomStringUtils.randomAlphanumeric(10)+","+ RandomStringUtils.randomAlphanumeric(10);
            String key = MD5Util.encrypt(value);
            log.info(value);
            CacheUtils.setCache(key,value);
            Map<String,String> map = new HashMap<>();
            map.put("Authorization",key);
            return ResultUtil.success(map);
        } catch (Exception e) {
            throw new WrapException(ExceptionMsg.LoginError.getCode(),ExceptionMsg.LoginError.getMsg());
        }
    }




}
