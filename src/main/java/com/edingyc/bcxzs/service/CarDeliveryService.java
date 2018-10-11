package com.edingyc.bcxzs.service;

import com.edingyc.bcxzs.dto.CarTransportDTO;
import com.edingyc.bcxzs.exception.WrapException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CarDeliveryService {

    String addCarDelivery(CarTransportDTO carTransportDTO)  throws WrapException;

    List<CarTransportDTO> findCarDeliveryList(Pageable pageable) throws WrapException;

    CarTransportDTO findCarDeliveryDetail(String id) throws WrapException;


}
