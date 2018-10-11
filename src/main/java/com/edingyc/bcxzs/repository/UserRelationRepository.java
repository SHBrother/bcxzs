package com.edingyc.bcxzs.repository;

import com.edingyc.bcxzs.dataEntity.UserRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRelationRepository extends JpaRepository<UserRelationEntity, String> {

    List<UserRelationEntity> findByDriverId(String driverId);

    List<UserRelationEntity> findByDispatcherId(String dispatcherId);

}
