package com.edingyc.bcxzs.service.impl;

import com.edingyc.bcxzs.Utils.EbusRequestSession;
import com.edingyc.bcxzs.Utils.SensitiveWordUtil;
import com.edingyc.bcxzs.Utils.convert.ConvertUtil;
import com.edingyc.bcxzs.Utils.randomId.IDGenerator;
import com.edingyc.bcxzs.dataEntity.BrandEntity;
import com.edingyc.bcxzs.dataEntity.CarInfoEntity;
import com.edingyc.bcxzs.dataEntity.CarReceiveEntity;
import com.edingyc.bcxzs.dataEntity.PicEntity;
import com.edingyc.bcxzs.dto.CarInfoDTO;
import com.edingyc.bcxzs.dto.CarTransportDTO;
import com.edingyc.bcxzs.dto.PicDTO;
import com.edingyc.bcxzs.exception.ExceptionMsg;
import com.edingyc.bcxzs.exception.WrapException;
import com.edingyc.bcxzs.repository.BrandRepository;
import com.edingyc.bcxzs.repository.CarInfoRepository;
import com.edingyc.bcxzs.repository.CarReceiveRepository;
import com.edingyc.bcxzs.repository.PicRepository;
import com.edingyc.bcxzs.service.CarReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.lang.Iterable;

/**
 * @program: bcxzs
 * @description: car receive service impl
 * @author: YSM
 * @create: 2018-09-25 18:26
 **/
@Service
public class CarReceiveServiceImpl implements CarReceiveService{

    @Autowired
    CarReceiveRepository carReceiveRepository;
    @Autowired
    PicRepository picRepository;
    @Autowired
    CarInfoRepository carInfoRepository;
    @Autowired
    IDGenerator iDGenerator;
    @Autowired
    SensitiveWordUtil sensitiveWordUtil;
    @Autowired
    BrandRepository brandRepository;
    @Value("${picAddr}")
    String picAddrPrefix;


    /**
     * Add car received record
     * @param receiveDTO
     * @throws WrapException
     */
    @Transactional
    public String addCarReceive(CarTransportDTO receiveDTO) throws WrapException{
        String vin = receiveDTO.getVin();
        if (vin.length()!=17){
            receiveDTO.setVin("");
        }else{
            CarReceiveEntity carReceive = findCarReceiveByVin(receiveDTO.getVin());
            if (carReceive != null){
                throw new WrapException(ExceptionMsg.DuplicateVinError.getCode(),ExceptionMsg.DuplicateVinError.getMsg());
            }
        }

        Set<String> words =  sensitiveWordUtil.getSensitiveWord(receiveDTO.getBrand());
        if (words.size()>0){
            Iterator<String> setIterator = words.iterator();
            String brand = setIterator.next();
            List<BrandEntity> brandEntityList = brandRepository.findByBrand(brand);
            receiveDTO.setBrand(brandEntityList.get(0).getLogo());
        }else {
            receiveDTO.setBrand("");
        }

        CarReceiveEntity carReceiveEntity = ConvertUtil.convert(receiveDTO,CarReceiveEntity.class);
        String id = iDGenerator.generateID();
        carReceiveEntity.setId(id);
        carReceiveEntity.setCreateTime(new Date());
        String userId = EbusRequestSession.getExHeader(EbusRequestSession.HEADER_USERID);
        carReceiveEntity.setOpenId(userId);
        carReceiveEntity.setUserId(userId);
        carReceiveRepository.save(carReceiveEntity);

        List<PicDTO> picList = receiveDTO.getReceivePics();
        addPics(picList,id);

        return id;

    }

    /**
     * add pics
     * @param picList
     * @param recordId  recordId
     */
    public void addPics(List<PicDTO> picList,String recordId)  throws WrapException{

        List<PicEntity> picEntityList = new ArrayList<>();
        for (PicDTO pic:picList) {
            pic.setId(iDGenerator.generateRamdomID());
            pic.setCreateTime(new Date());
            pic.setRecordId(recordId);
            PicEntity pe = ConvertUtil.convert(pic,PicEntity.class);
            picEntityList.add(pe);
        }

        picRepository.saveAll(picEntityList);

    }

    /**
     * find car receive list
     * @param pageable
     * @return
     * @throws WrapException
     */
    public List<CarTransportDTO> findCarReceiveList(Pageable pageable) throws WrapException{
        String userId = EbusRequestSession.getExHeader(EbusRequestSession.HEADER_USERID);
        Page<CarReceiveEntity> carReceiveEntityPage = carReceiveRepository.findByUserId(userId,pageable);
        List<CarReceiveEntity> carReceiveEntityList = carReceiveEntityPage.getContent();
        List<CarTransportDTO> carTransportDTOList = new ArrayList<>();
        for (CarReceiveEntity carReceiveEntity:carReceiveEntityList) {
            CarTransportDTO carTransportDTO =  ConvertUtil.convert(carReceiveEntity,CarTransportDTO.class);
            List<PicEntity> picEntityList = picRepository.findByRecordId(carTransportDTO.getId());
            List<PicDTO> picDTOList = ConvertUtil.convert(picEntityList,PicDTO.class);
            picDTOList.forEach(picDTO -> picDTO.setPicAddr(picAddrPrefix+picDTO.getPicAddr()));
            carTransportDTO.setReceivePics(picDTOList);
            carTransportDTOList.add(carTransportDTO);
        }
        return carTransportDTOList;
    }

    /**
     * find car receive detail
     * @param id
     * @return
     * @throws WrapException
     */
    public CarTransportDTO findCarReceiveDetail(String id) throws WrapException{
        Optional<CarReceiveEntity> optionalCarReceiveEntity = carReceiveRepository.findById(id);
        CarReceiveEntity carReceiveEntity = optionalCarReceiveEntity.get();
        CarTransportDTO carTransportDTO = ConvertUtil.convert(carReceiveEntity,CarTransportDTO.class);
        List<PicDTO> picDTOList = ConvertUtil.convert(picRepository.findByRecordId(carTransportDTO.getId()),PicDTO.class);
        picDTOList.forEach(picDTO -> picDTO.setPicAddr(picAddrPrefix+picDTO.getPicAddr()));
        carTransportDTO.setReceivePics(picDTOList);
        return  carTransportDTO;
    }

    @Transactional
    public void deleteCarReceive(String id){
        carReceiveRepository.deleteById(id);
        picRepository.deleteByRecordId(id);
    }

    public CarReceiveEntity findCarReceiveByVin(String vin){
        String userId = EbusRequestSession.getExHeader(EbusRequestSession.HEADER_USERID);
        return carReceiveRepository.findByVinAndUserId(vin,userId);
    }

    public void addCarInfo(CarInfoDTO carInfoDTO) throws WrapException{
        CarInfoEntity carInfoEntity = ConvertUtil.convert(carInfoDTO, CarInfoEntity.class);
        carInfoEntity.setId(iDGenerator.generateRamdomID());
        carInfoRepository.save(carInfoEntity);
    }

    public CarInfoDTO getCarInfo(String recordId) throws  WrapException {
        CarInfoEntity carInfoEntity = carInfoRepository.findByRecordId(recordId);
        return ConvertUtil.convert(carInfoEntity,CarInfoDTO.class);
    }




}
