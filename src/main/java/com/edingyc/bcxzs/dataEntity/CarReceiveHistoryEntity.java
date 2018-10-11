package com.edingyc.bcxzs.dataEntity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: bcxzs
 * @description: car receive history entity
 * @author: YSM
 * @create: 2018-09-27 16:32
 **/

@Entity
@DynamicUpdate
@Data
@Table(name = "TAB_BCXZS_CAR_RECEIVE_HISTORY")
public class CarReceiveHistoryEntity {

    @Id
    private String id;

    private String vin;

    private String brand;

    private Date receiveTime;

    private String receiveAddr;

    private String openId;

    private String userId;

    private Date createTime;

    private Date updateTime;

}
