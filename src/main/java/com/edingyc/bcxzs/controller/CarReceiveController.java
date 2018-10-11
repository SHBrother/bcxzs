package com.edingyc.bcxzs.controller;

import com.edingyc.bcxzs.LogManage.BaseLog;
import com.edingyc.bcxzs.Utils.ResultUtil;
import com.edingyc.bcxzs.dto.CarInfoDTO;
import com.edingyc.bcxzs.dto.CarTransportDTO;
import com.edingyc.bcxzs.dto.ResultDTO;
import com.edingyc.bcxzs.exception.WrapException;
import com.edingyc.bcxzs.service.CarReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: bcxzs
 * @description: car receive controller
 * @author: YSM
 * @create: 2018-09-25 18:31
 **/
@RestController
@RequestMapping("/receive")
public class CarReceiveController {

    @Autowired
    CarReceiveService carReceiveService;


    @PostMapping("addCarReceive")
    @BaseLog
    public ResultDTO addCarReceive(@RequestBody CarTransportDTO dto) throws WrapException{
        String id = carReceiveService.addCarReceive(dto);
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        return ResultUtil.success(map);
    }

    @PostMapping("addPics")
    @BaseLog
    public ResultDTO addPics(@RequestBody CarTransportDTO dto) throws WrapException{

        carReceiveService.addPics(dto.getReceivePics(),dto.getId());
        return ResultUtil.success();

    }

    @PostMapping("findCarReceiveList")
    @BaseLog
    public ResultDTO findCarReceiveList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size
                                        ) throws WrapException{

        Sort sort = new Sort(Direction.DESC, "receiveTime");
        Pageable pageable = PageRequest.of(page,size,sort);
        List<CarTransportDTO> list= carReceiveService.findCarReceiveList(pageable);

        return ResultUtil.success(list);

    }

    @GetMapping("findCarReceiveDetail/{id}")
    @BaseLog
    public ResultDTO findCarReceiveDetail(@PathVariable("id") String id) throws WrapException{

        CarTransportDTO carTransportDTO = carReceiveService.findCarReceiveDetail(id);
        return ResultUtil.success(carTransportDTO);

    }

    @GetMapping("deleteCarReceive/{id}")
    @BaseLog
    public ResultDTO deleteCarReceive(@PathVariable("id") String id){
        carReceiveService.deleteCarReceive(id);
        return ResultUtil.success();
    }

    @PostMapping("addCarInfo")
    @BaseLog
    public ResultDTO addCarInfo(@RequestBody CarInfoDTO carInfoDTO) throws WrapException{
        carReceiveService.addCarInfo(carInfoDTO);
        return ResultUtil.success();
    }

    @GetMapping("findCarInfo/{recordId}")
    @BaseLog
    public ResultDTO findCarInfo(@PathVariable("recordId") String recordId) throws WrapException{
        CarInfoDTO carInfoDTO = carReceiveService.getCarInfo(recordId);
        return ResultUtil.success(carInfoDTO);
    }




}
