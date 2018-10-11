package com.edingyc.bcxzs.dataEntity;

import com.edingyc.bcxzs.Utils.convert.IgnoreField;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: bcxzs
 * @description: car delivery
 * @author: YSM
 * @create: 2018-09-25 11:39
 **/

@Entity
@DynamicUpdate
@Data
@Table(name = "TAB_BCXZS_CAR_DELIVERY")
public class CarDeliveryEntity {

    @Id
    private String id;

    private String vin;

    private String brand;

    private Date deliveryTime;

    private String deliveryAddr;

    @IgnoreField
    private String openId;
    @IgnoreField
    private String userId;

    private Date createTime;
    private Date updateTime;

}
