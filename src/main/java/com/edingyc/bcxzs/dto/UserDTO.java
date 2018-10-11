package com.edingyc.bcxzs.dto;

import com.edingyc.bcxzs.Utils.convert.IgnoreField;
import lombok.Data;

/**
 * @program: bcxzs
 * @description: user dto
 * @author: YSM
 * @create: 2018-10-08 16:36
 **/
@Data
public class UserDTO {
    @IgnoreField
    private static final long serialVersionUID = 1L;
    private String userId;
    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    @IgnoreField
    private String[] privilege;
    private String unionid;
}
