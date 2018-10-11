package com.edingyc.bcxzs.service;

import com.edingyc.bcxzs.dto.CarInfoDTO;
import com.edingyc.bcxzs.dto.CarTransportDTO;
import com.edingyc.bcxzs.dto.PicDTO;
import com.edingyc.bcxzs.exception.WrapException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CarReceiveService {

    String addCarReceive(CarTransportDTO dto)  throws WrapException;

    void addPics(List<PicDTO> picList,String recordId)  throws WrapException;

    List<CarTransportDTO> findCarReceiveList(Pageable pageable) throws WrapException;

    CarTransportDTO findCarReceiveDetail(String id) throws WrapException;

    void deleteCarReceive(String id);

    void addCarInfo(CarInfoDTO carInfoDTO) throws WrapException ;

    CarInfoDTO getCarInfo(String recordId) throws  WrapException;

}
