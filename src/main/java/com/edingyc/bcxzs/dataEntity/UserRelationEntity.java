package com.edingyc.bcxzs.dataEntity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@DynamicUpdate
@Data
@Table(name = "TAB_BCXZS_USER_RELATION")
public class UserRelationEntity {
    @Id
    private String id;
    private String driverId;
    private String dispatcherId;
}
