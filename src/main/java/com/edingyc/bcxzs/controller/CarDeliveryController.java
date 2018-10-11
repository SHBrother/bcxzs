package com.edingyc.bcxzs.controller;

import com.edingyc.bcxzs.LogManage.BaseLog;
import com.edingyc.bcxzs.Utils.ResultUtil;
import com.edingyc.bcxzs.dto.CarTransportDTO;
import com.edingyc.bcxzs.dto.ResultDTO;
import com.edingyc.bcxzs.exception.WrapException;
import com.edingyc.bcxzs.service.CarDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: bcxzs
 * @description: Car Delivery Conreoller
 * @author: YSM
 * @create: 2018-09-27 16:14
 **/

@RestController
@RequestMapping("/delivery")
public class CarDeliveryController {

    @Autowired
    CarDeliveryService carDeliveryService;

    @PostMapping("addCarDelivery")
    @BaseLog
    public ResultDTO addCarDelivery(@RequestBody CarTransportDTO carTransportDTO) throws WrapException{

        String id = carDeliveryService.addCarDelivery(carTransportDTO);
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        System.out.println("1234344");
        return ResultUtil.success(map);

    }

    @PostMapping("findCarDeliveryList")
    @BaseLog
    public ResultDTO findCarDeliveryList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size
                                        ) throws WrapException{

        Sort sort = new Sort(Direction.DESC, "deliveryTime");
        Pageable pageable = PageRequest.of(page,size,sort);
        List<CarTransportDTO> carTransportDTOList = carDeliveryService.findCarDeliveryList(pageable);
        return ResultUtil.success(carTransportDTOList);

    }

    @GetMapping("findCarDeliveryDetail/{id}")
    @BaseLog
    public ResultDTO findCarDeliveryDetail(@PathVariable String id) throws WrapException{

        CarTransportDTO carTransportDTO = carDeliveryService.findCarDeliveryDetail(id);
        return ResultUtil.success(carTransportDTO);

    }






}
