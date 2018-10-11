package com.edingyc.bcxzs.repository;

import com.edingyc.bcxzs.dataEntity.CarInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarInfoRepository extends JpaRepository<CarInfoEntity, String> {

    CarInfoEntity findByRecordId(String recordId);

}
