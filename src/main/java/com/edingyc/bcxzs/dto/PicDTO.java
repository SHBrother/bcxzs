package com.edingyc.bcxzs.dto;

import com.edingyc.bcxzs.Utils.convert.IgnoreField;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: bcxzs
 * @description: picture dto
 * @author: YSM
 * @create: 2018-09-25 11:43
 **/

@Data
public class PicDTO implements Serializable{
    @IgnoreField
    private static final long serialVersionUID = 1L;

    private String id;

    private String recordId;

    private String picAddr;

    private int type;

    private String tag;

    private Date createTime;
}
