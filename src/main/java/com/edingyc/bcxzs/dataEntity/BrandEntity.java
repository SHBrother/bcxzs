package com.edingyc.bcxzs.dataEntity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: bcxzs
 * @description: brand entity
 * @author: YSM
 * @create: 2018-10-03 16:22
 **/
@Entity
@DynamicUpdate
@Data
@Table(name = "brand")
public class BrandEntity {

    @Id
    private String id;

    private String mark;

    private String brand;

    private String logo;

    private String vic;

    private String locked;

    private String createTime;

    private String updateTime;




}
