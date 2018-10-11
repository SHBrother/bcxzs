package com.edingyc.bcxzs.Utils.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: bcxzs
 * @description: pc config
 * @author: YSM
 * @create: 2018-10-08 11:17
 **/

@Component
@ConfigurationProperties(prefix = "pc")
@Data
public class PcConfig {

    private String appid;

    private String secret;

    private String grant_type;

    private String accessTokenUrl;

    private String userInfoUrl;
}
