package com.edingyc.bcxzs.Utils.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: bcxzs
 * @description: applet config
 * @author: YSM
 * @create: 2018-10-08 10:56
 **/

@Component
@ConfigurationProperties(prefix = "applet")
@Data
public class AppletConfig {

    private String loginUrl;

    private String appid;

    private String secret;

    private String grant_type;

}
