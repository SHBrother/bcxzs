package com.edingyc.bcxzs.dto;

import com.edingyc.bcxzs.Utils.convert.IgnoreField;
import lombok.Data;

/**
 * @program: bcxzs
 * @description: car info dto
 * @author: YSM
 * @create: 2018-09-29 18:21
 **/

@Data
public class CarInfoDTO {

    @IgnoreField
    private static final long serialVersionUID = 1L;

    private String id;

    private String vin;
    /** 记录id */
    private String recordId;
    /** 钥匙 */
    private int carKey;
    /** 备胎 */
    private int spareWheel;
    /** 说明书 */
    private int instruction;
    /** 天线 */
    private int antenna;
    /** 导航卡 */
    private int navigationCard;
    /** 烟缸 */
    private int ashtray;
    /**千斤顶 */
    private int jack;
    /** 保修 */
    private int guarantee;
    /** 机油 */
    private int engineOil;
    /** 一次性证书 */
    private int disposableCertificate;
    /** 点烟器 */
    private int cigarLighter;
    /** 工具 */
    private int tool;
    /** 三包 */
    private int threeGuarantee;
    /** 机油格 */
    private int oilFilter;
    /** 合格证*/
    private int qualifiedCertificate;
    /** 拖车钩 */
    private int towHook;
    /** 三脚架 */
    private int tripod;
    /** 发票*/
    private int invoice;
    /** 充气泵 */
    private int airPump;
    /** 拖车盖 */
    private int towBracket;
    /** 轮毂盖 */
    private int hubcap;
    /** 地毯 */
    private int carpet;
    /** 相片 */
    private int photo;
    /** 托印 */
    private int seal;
    /**品牌*/
    private String brand;
    /** 车型 */
    private String vehicleModel;
    /** 公里数 */
    private String kil;
    /** 外观 */
    private String facade;
    /** 内饰*/
    private String interior;
    /**其它*/
    private String others;

}
