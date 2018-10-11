package com.edingyc.bcxzs.controller;

import com.edingyc.bcxzs.LogManage.BaseLog;
import com.edingyc.bcxzs.Utils.ResultUtil;
import com.edingyc.bcxzs.dataEntity.UserEntity;
import com.edingyc.bcxzs.dto.CarShareDTO;
import com.edingyc.bcxzs.dto.ResultDTO;
import com.edingyc.bcxzs.dto.UserDTO;
import com.edingyc.bcxzs.exception.WrapException;
import com.edingyc.bcxzs.service.CarShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: bcxzs
 * @description: CarShareController
 * @author: YSM
 * @create: 2018-09-25 18:31
 **/
@RestController
@RequestMapping("/share")
public class CarShareController {

    @Autowired
    CarShareService carShareService;

    @PostMapping("addUserInfo")
    @BaseLog
    public ResultDTO addUserInfo(@RequestBody UserDTO userDTO) throws WrapException{
        userDTO.setUserId(userDTO.getOpenid());
        carShareService.addUserInfo(userDTO);
        return ResultUtil.success();
    }

    @GetMapping("getDispatcherInfo")
    @BaseLog
    public ResultDTO getDispatcherInfo() throws WrapException{
        List<UserDTO> userDTOList = carShareService.getDispatcherInfo();
        return ResultUtil.success(userDTOList);
    }

    @GetMapping("carReceiveShare")
    @BaseLog
    public ResultDTO carReceiveShare(String id,String dispatcherId) throws WrapException{
        carShareService.carReceiveShare(id,dispatcherId);
        return ResultUtil.success();
    }

    @GetMapping("carDeliveryShare")
    @BaseLog
    public ResultDTO carDeliveryShare(String id,String dispatcherId) throws WrapException{
        carShareService.carDeliveryShare(id,dispatcherId);
        return ResultUtil.success();
    }

    @PostMapping("findCarShareList")
    @BaseLog
    public ResultDTO findCarShareList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size
                                      ) throws WrapException{
        Sort sort = new Sort(Sort.Direction.DESC, "shareTime");
        Pageable pageable = PageRequest.of(page,size,sort);
        List<CarShareDTO> carShareDTOList = carShareService.findCarShareList(pageable);
        return ResultUtil.success(carShareDTOList);
    }

    @GetMapping("findCarShareDetail/{id}")
    @BaseLog
    public ResultDTO findCarShareDetail(@PathVariable String id) throws WrapException{
        CarShareDTO carShareDTO = carShareService.findCarShareDetail(id);
        return ResultUtil.success(carShareDTO);
    }

    @PostMapping("addCarFile")
    @BaseLog
    public ResultDTO addCarFile(@RequestBody CarShareDTO carShareDTO) throws WrapException{
        carShareService.addCarFile(carShareDTO);
        return ResultUtil.success();
    }

    @PostMapping("findCarFileList")
    @BaseLog
    public ResultDTO findCarFileList(@RequestBody CarShareDTO carShareDTO) throws WrapException{
        Sort sort = new Sort(Sort.Direction.DESC, "receiveTime");
        Pageable pageable = PageRequest.of(carShareDTO.getPage(),carShareDTO.getSize(),sort);
        List<CarShareDTO> carShareDTOList = carShareService.findCarFileList(pageable,carShareDTO);
        return ResultUtil.success(carShareDTOList);

    }

    @GetMapping("findUserList")
    @BaseLog
    public ResultDTO findUserList() throws WrapException{
        List<UserDTO> userDTOList = carShareService.findUserList();
        return ResultUtil.success(userDTOList);
    }




}
