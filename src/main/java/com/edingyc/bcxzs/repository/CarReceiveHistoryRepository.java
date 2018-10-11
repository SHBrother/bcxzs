package com.edingyc.bcxzs.repository;

import com.edingyc.bcxzs.dataEntity.CarReceiveHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarReceiveHistoryRepository  extends JpaRepository<CarReceiveHistoryEntity, String> {

    CarReceiveHistoryEntity findByVinAndUserId(String vin, String userId);

}
