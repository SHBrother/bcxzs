package com.edingyc.bcxzs.dataEntity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: bcxzs
 * @description: picture
 * @author: YSM
 * @create: 2018-09-25 11:40
 **/


@Entity
@DynamicUpdate
@Data
@Table(name = "TAB_BCXZS_PIC")
public class PicEntity {

    @Id
    private String id;

    private String recordId;

    private String picAddr;

    private int type;

    private String tag;

    private Date createTime;
}
