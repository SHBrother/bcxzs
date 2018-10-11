package com.edingyc.bcxzs.dataEntity;

import com.edingyc.bcxzs.Utils.convert.IgnoreField;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: bcxzs
 * @description: car receive
 * @author: YSM
 * @create: 2018-09-25 11:34
 **/

@Entity
@DynamicUpdate
@Data
@Table(name = "TAB_BCXZS_CAR_RECEIVE")
public class CarReceiveEntity {

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
