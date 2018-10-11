package com.edingyc.bcxzs.dto;

import com.edingyc.bcxzs.Utils.convert.IgnoreField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CarShareDTO {

    @IgnoreField
    private static final long serialVersionUID = 1L;

    private String id;

    private String receiveId;

    private String vin;

    private String brand;

    private Date receiveTime;

    private String receiveAddr;

    private Date deliveryTime;

    private String deliveryAddr;

    private String driverId;

    private String dispatcherId;

    private String delegateCompany;

    private String fee;

    private Date settleDate;

    private int fileFlag;

    private String driverName;

    private String driverImg;

    private Date shareTime;

    @IgnoreField
    private List<PicDTO> receivePics;

    @IgnoreField
    private List<PicDTO> deliveryPics;
    @IgnoreField
    private int page;
    @IgnoreField
    private int size;



}
