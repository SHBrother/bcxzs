package com.edingyc.bcxzs.repository;

import com.edingyc.bcxzs.dataEntity.CarShareEntity;
import com.edingyc.bcxzs.dto.CarShareDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.security.PermitAll;
import javax.transaction.Transactional;
import java.util.Date;

public interface CarShareRepository   extends JpaRepository<CarShareEntity, String> {

    void deleteByVinAndDriverId(String vin,String driverId);

    Page<CarShareEntity> findByDispatcherIdAndFileFlag(String dispatcherId, Pageable pageable,int fileFlag);

    Page<CarShareEntity> findAll(Specification<CarShareEntity> specification, Pageable pageable);



}
