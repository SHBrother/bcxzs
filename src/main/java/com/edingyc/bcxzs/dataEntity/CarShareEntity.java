package com.edingyc.bcxzs.dataEntity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
@Table(name = "TAB_BCXZS_CAR_SHARE")
public class CarShareEntity {

    @Id
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

    private Date shareTime;


}
