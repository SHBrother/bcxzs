package com.edingyc.bcxzs.interceptor;

import com.edingyc.bcxzs.Utils.CacheUtils;
import com.edingyc.bcxzs.Utils.EbusRequestSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: bcxzs
 * @description: login interceptor
 * @author: YSM
 * @create: 2018-09-28 17:15
 **/
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String urlSb = request.getRequestURL().toString();
            if(urlSb.contains("login") || urlSb.contains("webLogin")){
                return true;
            }

            String token = request.getHeader("Authorization");
            log.info("token={}",token);
            if (!StringUtils.isEmpty(token)) {
                String sessionToken =  CacheUtils.getCache(token);
                log.info("sessionToken={}",sessionToken);
                if (!StringUtils.isEmpty(sessionToken)){
                    String openid = sessionToken.split(",")[0];
                    EbusRequestSession.addExHeader(EbusRequestSession.HEADER_USERID,openid);
                    return true;
                }

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":\"-1\",\"msg\":\"authorization fail\"}");
                return false;

            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":\"-1\",\"msg\":\"authorization fail\"}");
            return false;

        }


}
