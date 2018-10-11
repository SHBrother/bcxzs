package com.edingyc.bcxzs.repository;

import com.edingyc.bcxzs.dataEntity.CarReceiveEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarReceiveRepository  extends JpaRepository<CarReceiveEntity, String> {

    void deleteByVinAndUserId(String vin,String userId);

    CarReceiveEntity findByVinAndUserId(String vin,String userId);

    Page<CarReceiveEntity> findByUserId(String userId, Pageable pageable);

}
