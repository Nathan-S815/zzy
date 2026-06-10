package com.zzy.db.entity.carpark;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 停车场信息
 * </p>
 *
 * @author zzy
 * @since 2020-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GetParkInfo extends Model<GetParkInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 索引ID
     */
    @TableId(value = "index_id", type = IdType.AUTO)
    private Integer indexId;

    /**
     * 车场唯一编号
     */
    private String parkKey;

    /**
     * 停车场名称
     */
    private String parkName;

    /**
     * 所属景区
     */
    private String scenicName;

    /**
     * 车场维度
     */
    private String parkLatitude;

    /**
     * 车场经度
     */
    private String parkLongitude;

    /**
     * 车场地址
     */
    private String parkAdd;

    /**
     * 车场联系电话
     */
    private String parkTel;

    /**
     * 车场联系人
     */
    private String parkLinkman;

    /**
     * 免费分钟数
     */
    private String parkFreetime;

    /**
     * 免费超时分钟数
     */
    private String parkFreetimeOut;

    /**
     * 车场收费说明
     */
    private String chargeSdesc;

    /**
     * 停车场是否启用预定功能
     */
    private String reserveStatus;

    /**
     * 注册时间
     */
    private String regTime;

    /**
     * 停车场有效期止日
     */
    private String validTime;

    /**
     * 停车场可容纳的车位总数
     */
    private String spaceTotal;

    /**
     * 车牌地区简称
     */
    private String cityShortName;

    /**
     * 月租车收费规则[]
     */
    private String carTypeChargRules;

    /**
     * url
     */
    private String parkqrcodeUrl;


    private Date createTime;

    private Date updateTime;

    private String warningTime;
    @Override
    protected Serializable pkVal() {
        return this.indexId;
    }

}
