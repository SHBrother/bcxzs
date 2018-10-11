package com.edingyc.bcxzs.service;

import com.edingyc.bcxzs.dataEntity.UserEntity;
import com.edingyc.bcxzs.dto.CarShareDTO;
import com.edingyc.bcxzs.dto.UserDTO;
import com.edingyc.bcxzs.exception.WrapException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CarShareService {

    void addUserInfo(UserDTO userDTO) throws WrapException;

    UserDTO getUserInfo(String openId)  throws WrapException;

    void unionId(String driverId,String dispatcherId);

    List<UserDTO> getDispatcherInfo() throws WrapException;

    void carReceiveShare(String id,String dispatcherId) throws WrapException;

    void carDeliveryShare(String id,String dispatcherId) throws WrapException;

    List<CarShareDTO> findCarShareList(Pageable pageable) throws WrapException ;

    CarShareDTO findCarShareDetail(String id) throws WrapException;

    void addCarFile(CarShareDTO carShareDTO) throws WrapException;

    List<CarShareDTO> findCarFileList(Pageable pageable,CarShareDTO carShareDTO) throws WrapException;

    List<UserDTO> findUserList() throws WrapException;

}
