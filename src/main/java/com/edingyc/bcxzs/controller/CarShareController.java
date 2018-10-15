package com.edingyc.bcxzs.controller;

import com.edingyc.bcxzs.LogManage.BaseLog;
import com.edingyc.bcxzs.Utils.ExcelUtils.ExcelUtil;
import com.edingyc.bcxzs.Utils.ResultUtil;
import com.edingyc.bcxzs.dataEntity.UserEntity;
import com.edingyc.bcxzs.dto.CarRecordExcelDTO;
import com.edingyc.bcxzs.dto.CarShareDTO;
import com.edingyc.bcxzs.dto.ResultDTO;
import com.edingyc.bcxzs.dto.UserDTO;
import com.edingyc.bcxzs.exception.ExceptionMsg;
import com.edingyc.bcxzs.exception.WrapException;
import com.edingyc.bcxzs.service.CarShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("findCarRecordExcelDTO")
    @BaseLog
    public void findCarRecordExcelDTO(Date settleDate, String delegateCompany) throws WrapException{
        List<CarRecordExcelDTO> carRecordExcelDTOList = carShareService.findCarRecordExcelDTO(settleDate,delegateCompany);
        File f=new File("test.xls");
        try{
            Map<String,String> map = new LinkedHashMap<>();
            map = genHeaderMap(map);
            OutputStream out =new FileOutputStream(f);
            ExcelUtil.exportExcel(map, carRecordExcelDTOList, out);
        }catch (Exception e){

        }
    }

    private Map<String,String> genHeaderMap(Map<String,String> map){

        map.put("3","品牌");
        map.put("4","车型");
        map.put("5","公里数");
        map.put("6","外观");
        map.put("7","内饰");
        map.put("8","其它");
        map.put("a","车架地");
        map.put("b","接车地");
        map.put("c","交车地");
        map.put("d","费用");
        map.put("e","钥匙");
        map.put("f","备胎");
        map.put("g","说明书");
        map.put("h","天线");
        map.put("i","导航卡");
        map.put("j","烟缸");
        map.put("k","千斤顶");
        map.put("l","保修");
        map.put("m","机油");
        map.put("n","一次性证书");
        map.put("o","点烟器");
        map.put("p","工具");
        map.put("q","三包");
        map.put("r","机油格");
        map.put("s","合格证");
        map.put("t","拖车钩");
        map.put("u","三脚架");
        map.put("v","发票");
        map.put("w","充气泵");
        map.put("x","拖车盖");
        map.put("y","轮毂盖");
        map.put("z","地毯");







        return map;
    }




}
