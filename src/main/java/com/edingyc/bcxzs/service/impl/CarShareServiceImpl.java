package com.edingyc.bcxzs.service.impl;

import com.edingyc.bcxzs.Utils.EbusRequestSession;
import com.edingyc.bcxzs.Utils.convert.ConvertUtil;
import com.edingyc.bcxzs.Utils.randomId.IDGenerator;
import com.edingyc.bcxzs.dataEntity.*;
import com.edingyc.bcxzs.dto.CarShareDTO;
import com.edingyc.bcxzs.dto.PicDTO;
import com.edingyc.bcxzs.dto.UserDTO;
import com.edingyc.bcxzs.exception.WrapException;
import com.edingyc.bcxzs.repository.*;
import com.edingyc.bcxzs.service.CarShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CarShareServiceImpl implements CarShareService{

    @Autowired
    UserRelationRepository userRelationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IDGenerator idGenerator;
    @Autowired
    CarReceiveRepository carReceiveRepository;
    @Autowired
    CarDeliveryRepository carDeliveryRepository;
    @Autowired
    CarShareRepository carShareRepository;
    @Autowired
    PicRepository picRepository;
    @Autowired
    CarReceiveHistoryRepository carReceiveHistoryRepository;
    @Value("${picAddr}")
    String picAddrPrefix;

    @Override
    public void addUserInfo(UserDTO userDTO) throws WrapException{
        userDTO.setUserId(userDTO.getOpenid());
        userRepository.save(ConvertUtil.convert(userDTO,UserEntity.class));
    }

    @Override
    public UserDTO getUserInfo(String openId)  throws WrapException{
        UserEntity userEntity  = userRepository.findByOpenid(openId);
        return ConvertUtil.convert(userEntity,UserDTO.class);
    }

    @Override
    public void unionId(String driverId,String dispatcherId){
        UserRelationEntity userRelationEntity = new UserRelationEntity();
        userRelationEntity.setDriverId(driverId);
        userRelationEntity.setDispatcherId(dispatcherId);
        userRelationEntity.setId(idGenerator.generateRamdomID());
        userRelationRepository.save(userRelationEntity);
    }

    @Override
    public List<UserDTO> getDispatcherInfo() throws WrapException{
        String  driverId = EbusRequestSession.getExHeader(EbusRequestSession.HEADER_USERID);
        List<UserRelationEntity> userRelationEntityList = userRelationRepository.findByDriverId(driverId);
        List<UserEntity> userEntityList = new ArrayList<>();
        userRelationEntityList.forEach(userRelationEntity -> {
            Optional<UserEntity> optionalUserEntity =  userRepository.findById(userRelationEntity.getDispatcherId());
            userEntityList.add(optionalUserEntity.get());
        });
        return ConvertUtil.convert(userEntityList,UserDTO.class);
    }

    @Override
    public void carReceiveShare(String id, String dispatcherId) throws WrapException{
        CarReceiveEntity carReceiveEntity = carReceiveRepository.findById(id).get();
        CarShareEntity carSharedEntity =  ConvertUtil.convert(carReceiveEntity,CarShareEntity.class);

        String driverId = EbusRequestSession.getExHeader(EbusRequestSession.HEADER_USERID);
        carSharedEntity.setDriverId(driverId);
        carSharedEntity.setDispatcherId(dispatcherId);
        carSharedEntity.setShareTime(new Date());

        carShareRepository.save(carSharedEntity);
    }

    @Override
    @Transactional
    public void carDeliveryShare(String id, String dispatcherId)  throws WrapException{
        CarDeliveryEntity carDeliveryEntity = carDeliveryRepository.findById(id).get();
        CarShareEntity carSharedEntity = ConvertUtil.convert(carDeliveryEntity,CarShareEntity.class);

        String driverId = EbusRequestSession.getExHeader(EbusRequestSession.HEADER_USERID);
        carSharedEntity.setDriverId(driverId);
        carSharedEntity.setDispatcherId(dispatcherId);
        carSharedEntity.setShareTime(new Date());

        CarReceiveHistoryEntity carReceiveEntity = carReceiveHistoryRepository.findByVinAndUserId(carSharedEntity.getVin(),driverId);
        carSharedEntity.setReceiveAddr(carReceiveEntity.getReceiveAddr());
        carSharedEntity.setReceiveTime(carReceiveEntity.getReceiveTime());
        carSharedEntity.setReceiveId(carReceiveEntity.getId());

        String vin = carSharedEntity.getVin();
        if (!StringUtils.isEmpty(vin)){
            carShareRepository.deleteByVinAndDriverId(vin,driverId);
        }

        carShareRepository.save(carSharedEntity);
    }

    @Override
    public List<CarShareDTO> findCarShareList(Pageable pageable) throws WrapException{
        String userId = EbusRequestSession.getExHeader(EbusRequestSession.HEADER_USERID);
        Page<CarShareEntity> carShareEntityPage = carShareRepository.findByDispatcherIdAndFileFlag(userId,pageable,0);
        List<CarShareEntity> carShareEntityList = carShareEntityPage.getContent();
        List<CarShareDTO> carShareDTOList = ConvertUtil.convert(carShareEntityList,CarShareDTO.class);
        carShareDTOList.forEach(carShareDTO -> {
            String driverId = carShareDTO.getDriverId();
            UserEntity userEntity = userRepository.findById(driverId).get();
            carShareDTO.setDriverName(userEntity.getNickname());
            carShareDTO.setDriverImg(userEntity.getHeadimgurl());
        });
        return carShareDTOList;
    }

    @Override
    public CarShareDTO findCarShareDetail(String id) throws WrapException {
        CarShareEntity carShareEntity = carShareRepository.findById(id).get();
        CarShareDTO carShareDTO = ConvertUtil.convert(carShareEntity,CarShareDTO.class);

        String driverId = carShareDTO.getDriverId();
        UserEntity userEntity = userRepository.findById(driverId).get();
        carShareDTO.setDriverName(userEntity.getNickname());
        carShareDTO.setDriverImg(userEntity.getHeadimgurl());

        //receiveId为空代表接车记录，id为接车记录id
        String deliveryId = carShareDTO.getId();
        String receiveId = carShareDTO.getReceiveId();
        if (StringUtils.isEmpty(receiveId)){
            receiveId = carShareDTO.getId();
            deliveryId = "";
        }

        if (!StringUtils.isEmpty(deliveryId)){
            List<PicEntity> DPicDTOList = picRepository.findByRecordId(deliveryId);
            DPicDTOList.forEach(picDTO -> picDTO.setPicAddr(picAddrPrefix+picDTO.getPicAddr()));
            carShareDTO.setDeliveryPics(ConvertUtil.convert(DPicDTOList,PicDTO.class));
        }

        List<PicEntity> RPicDTOList = picRepository.findByRecordId(receiveId);
        RPicDTOList.forEach(picDTO -> picDTO.setPicAddr(picAddrPrefix+picDTO.getPicAddr()));
        carShareDTO.setReceivePics(ConvertUtil.convert(RPicDTOList,PicDTO.class));

        return carShareDTO;
    }

    @Override
    public void addCarFile(CarShareDTO carShareDTO) {
        String id = carShareDTO.getId();
        CarShareEntity carShareEntity = carShareRepository.getOne(id);
        carShareEntity.setDeliveryAddr(carShareDTO.getDeliveryAddr());
        carShareEntity.setDeliveryTime(carShareDTO.getDeliveryTime());
        carShareEntity.setDelegateCompany(carShareDTO.getDelegateCompany());
        carShareEntity.setFee(carShareDTO.getFee());
        carShareEntity.setSettleDate(carShareDTO.getSettleDate());
        carShareEntity.setFileFlag(1);
        carShareRepository.deleteById(id);
        carShareRepository.save(carShareEntity);
    }

    @Override
    public List<CarShareDTO> findCarFileList(Pageable pageable,CarShareDTO carShareDTO) throws WrapException{

        Specification<CarShareEntity> specification = new Specification<CarShareEntity>(){
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList();
                if (!StringUtils.isEmpty(carShareDTO.getVin())){
                    predicateList.add(cb.equal(root.get("vin").as(String.class),carShareDTO.getVin()));
                }
                if (!StringUtils.isEmpty(carShareDTO.getDelegateCompany())){
                    predicateList.add(cb.like(root.get("delegateCompany").as(String.class),"%"+carShareDTO.getDelegateCompany()+"%"));
                }
                if (!StringUtils.isEmpty(carShareDTO.getDeliveryTime())){
                    predicateList.add(cb.equal(root.get("deliveryTime").as(Date.class),carShareDTO.getDeliveryTime()));
                }
                predicateList.add(cb.equal(root.get("fileFlag").as(int.class),1));
                Predicate[] arrayPredicates = new Predicate[predicateList.size()];
                return cb.and(predicateList.toArray(arrayPredicates));
            }
        };
        Page<CarShareEntity> carShareEntityPage= carShareRepository.findAll(specification,pageable);
        List<CarShareDTO> carShareDTOList = ConvertUtil.convert(carShareEntityPage.getContent(),CarShareDTO.class);
        return carShareDTOList;

    }

    @Override
    public List<UserDTO> findUserList() throws WrapException{
        String userId = EbusRequestSession.getExHeader(EbusRequestSession.HEADER_USERID);
        List<UserRelationEntity> userRelationEntityList = userRelationRepository.findByDispatcherId(userId);
        List<UserEntity> userEntityList = new ArrayList<>();
        userRelationEntityList.forEach(userRelationEntity -> {
            UserEntity userEntity = userRepository.findById(userRelationEntity.getDriverId()).get();
            userEntityList.add(userEntity);
        });
        return ConvertUtil.convert(userEntityList,UserDTO.class);
    }
}
