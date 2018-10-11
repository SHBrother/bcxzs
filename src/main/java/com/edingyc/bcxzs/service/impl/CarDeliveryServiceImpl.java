package com.edingyc.bcxzs.service.impl;

import com.edingyc.bcxzs.Utils.EbusRequestSession;
import com.edingyc.bcxzs.Utils.SensitiveWordUtil;
import com.edingyc.bcxzs.Utils.convert.ConvertUtil;
import com.edingyc.bcxzs.Utils.randomId.IDGenerator;
import com.edingyc.bcxzs.dataEntity.*;
import com.edingyc.bcxzs.dto.CarTransportDTO;
import com.edingyc.bcxzs.dto.PicDTO;
import com.edingyc.bcxzs.exception.ExceptionMsg;
import com.edingyc.bcxzs.exception.WrapException;
import com.edingyc.bcxzs.repository.*;
import com.edingyc.bcxzs.service.CarDeliveryService;
import com.edingyc.bcxzs.service.CarReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @program: bcxzs
 * @description: Car Delivery Service Impl
 * @author: YSM
 * @create: 2018-09-27 16:15
 **/
@Service
public class CarDeliveryServiceImpl implements CarDeliveryService{

    @Autowired
    private IDGenerator iDGenerator;
    @Autowired
    private CarReceiveService carReceiveService;
    @Autowired
    private CarDeliveryRepository carDeliveryRepository;
    @Autowired
    private CarReceiveRepository carReceiveRepository;
    @Autowired
    private CarReceiveHistoryRepository carReceiveHistoryRepository;
    @Autowired
    PicRepository picRepository;
    @Autowired
    SensitiveWordUtil sensitiveWordUtil;
    @Autowired
    BrandRepository brandRepository;
    @Value("${picAddr}")
    String picAddrPrefix;

    @Transactional
    public String addCarDelivery(CarTransportDTO carTransportDTO)  throws WrapException{

        String vin = carTransportDTO.getVin();
        if (vin.length()!=17){
            carTransportDTO.setVin("");
        }else{
            CarDeliveryEntity carDelivery = findCarDeliveryByVin(carTransportDTO.getVin());
            if (carDelivery != null){
                throw new WrapException(ExceptionMsg.DuplicateVinError.getCode(),ExceptionMsg.DuplicateVinError.getMsg());
            }
        }

        Set<String> words =  sensitiveWordUtil.getSensitiveWord(carTransportDTO.getBrand());
        if (words.size()>0){
            Iterator<String> setIterator = words.iterator();
            String brand = setIterator.next();
            List<BrandEntity> brandEntityList = brandRepository.findByBrand(brand);
            carTransportDTO.setBrand(brandEntityList.get(0).getLogo());
        }else {
            carTransportDTO.setBrand("");
        }

        CarDeliveryEntity carDeliveryEntity  = ConvertUtil.convert(carTransportDTO,CarDeliveryEntity.class);
        String id = iDGenerator.generateID();
        carDeliveryEntity.setId(id);
        carDeliveryEntity.setCreateTime(new Date());
        String userId = EbusRequestSession.getExHeader(EbusRequestSession.HEADER_USERID);
        carDeliveryEntity.setOpenId(userId);
        carDeliveryEntity.setUserId(userId);
        carDeliveryRepository.save(carDeliveryEntity);

        List<PicDTO> picList = carTransportDTO.getDeliveryPics();
        carReceiveService.addPics(picList,id);

        //move relative received record to tab_car_receive_history
        CarReceiveEntity carReceiveEntity = carReceiveRepository.findByVinAndUserId(vin,userId);
        if (carReceiveEntity != null){
            CarReceiveHistoryEntity carReceiveHistoryEntity = ConvertUtil.convert(carReceiveEntity, CarReceiveHistoryEntity.class);
            carReceiveHistoryRepository.save(carReceiveHistoryEntity);
            carReceiveRepository.deleteByVinAndUserId(vin,userId);
        }
        return id;
    }

    /**
     * findCarDeliveryList
     * @param pageable
     * @return
     * @throws WrapException
     */
    public List<CarTransportDTO> findCarDeliveryList(Pageable pageable) throws WrapException{
        String userId = EbusRequestSession.getExHeader(EbusRequestSession.HEADER_USERID);
        Page<CarTransportDTO> carTransportDTOPage = carDeliveryRepository.findByUserId(userId,pageable);
        List<CarTransportDTO> carTransportDTOList = carTransportDTOPage.getContent();
        for (CarTransportDTO carTransportDTO:carTransportDTOList) {
            List<PicEntity> DPicDTOList = picRepository.findByRecordId(carTransportDTO.getId());
            DPicDTOList.forEach(picDTO -> picDTO.setPicAddr(picAddrPrefix+picDTO.getPicAddr()));
            carTransportDTO.setDeliveryPics(ConvertUtil.convert(DPicDTOList,PicDTO.class));
            List<PicEntity> RPicDTOList = picRepository.findByRecordId(carTransportDTO.getReceiveId());
            RPicDTOList.forEach(picDTO -> picDTO.setPicAddr(picAddrPrefix+picDTO.getPicAddr()));
            carTransportDTO.setReceivePics(ConvertUtil.convert(RPicDTOList,PicDTO.class));
        }

        return carTransportDTOList;

    }

    /**
     * findCarDeliveryDetail
     * @param id
     * @return
     */
    public CarTransportDTO findCarDeliveryDetail(String id) throws WrapException{
        String userId = EbusRequestSession.getExHeader(EbusRequestSession.HEADER_USERID);
        CarTransportDTO carTransportDTO = carDeliveryRepository.findByDeliveryId(id,userId);

        List<PicEntity> DPicDTOList = picRepository.findByRecordId(carTransportDTO.getId());
        DPicDTOList.forEach(picDTO -> picDTO.setPicAddr(picAddrPrefix+picDTO.getPicAddr()));
        carTransportDTO.setDeliveryPics(ConvertUtil.convert(DPicDTOList,PicDTO.class));

        List<PicEntity> RPicDTOList = picRepository.findByRecordId(carTransportDTO.getReceiveId());
        RPicDTOList.forEach(picDTO -> picDTO.setPicAddr(picAddrPrefix+picDTO.getPicAddr()));
        carTransportDTO.setReceivePics(ConvertUtil.convert(RPicDTOList,PicDTO.class));

        return carTransportDTO;
    }


    /**
     * findCarDeliveryByVin
     * @param vin
     * @return
     */
    public CarDeliveryEntity findCarDeliveryByVin(String vin){
        String userId = EbusRequestSession.getExHeader(EbusRequestSession.HEADER_USERID);
        return carDeliveryRepository.findByVinAndUserId(vin,userId);
    }

}
