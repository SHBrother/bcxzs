package com.edingyc.bcxzs.repository;

import com.edingyc.bcxzs.dataEntity.CarDeliveryEntity;
import com.edingyc.bcxzs.dto.CarTransportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CarDeliveryRepository   extends JpaRepository<CarDeliveryEntity, String> {

    String baseSQL = "SELECT new com.edingyc.bcxzs.dto.CarTransportDTO( cd.id,cd.vin,cd.brand,cr.id,cd.deliveryTime,cd.deliveryAddr,cr.receiveTime,cr.receiveAddr) " +
                     "FROM CarDeliveryEntity cd " +
                     "LEFT JOIN CarReceiveHistoryEntity cr ON cr.vin = cd.vin ";
    @Query( baseSQL+"WHERE cd.id = ?1 AND cd.userId = ?2 AND cr.userId=?2")
    CarTransportDTO findByDeliveryId(String id,String userId);

    @Query(baseSQL+"WHERE cd.userId = ?1 AND cr.userId=?1")
    Page<CarTransportDTO> findByUserId(String userId,Pageable pageable);

    CarDeliveryEntity findByVinAndUserId(String vin,String userId);

}
