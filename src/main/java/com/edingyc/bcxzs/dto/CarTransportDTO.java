package com.edingyc.bcxzs.dto;


import com.edingyc.bcxzs.Utils.convert.IgnoreField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: bcxzs
 * @description: car delivery
 * @author: YSM
 * @create: 2018-09-25 11:43
 **/

@Data
public class CarTransportDTO  implements Serializable {

    @IgnoreField
    private static final long serialVersionUID = 1L;

    private String id;
    //车架号
    private String vin;
    //品牌
    private String brand;
    //在查询交车记录时使用，区分交车id
    private String receiveId;

    private Date receiveTime;

    private String receiveAddr;

    @IgnoreField
    private List<PicDTO> receivePics;

    private Date deliveryTime;

    private String deliveryAddr;

    @IgnoreField
    private List<PicDTO> deliveryPics;

    private Date createTime;

    private Date updateTime;

    public CarTransportDTO(){

    }

    public CarTransportDTO(String id, String vin, String brand, String receiveId, Date receiveTime, String receiveAddr,Date deliveryTime, String deliveryAddr) {
        this.id = id;
        this.vin = vin;
        this.brand = brand;
        this.receiveId = receiveId;
        this.receiveTime = receiveTime;
        this.receiveAddr = receiveAddr;
        this.deliveryTime = deliveryTime;
        this.deliveryAddr = deliveryAddr;
    }
}
