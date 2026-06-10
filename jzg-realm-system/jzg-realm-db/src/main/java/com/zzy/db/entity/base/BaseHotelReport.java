package com.zzy.db.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 酒店客流收入表
 * </p>
 *
 * @author zzy
 * @since 2020-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseHotelReport extends Model<BaseHotelReport> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属酒店id（酒店基础信息表主键）
     */
    private Integer subHotelId;

    /**
     * 所属景区id
     */
    private String subScenicId;

    /**
     * 所属酒店名称
     */
    private String subHotelName;

    /**
     * 进入人数
     */
    private String inPeople;

    /**
     * 出去人数
     */
    private String outPeople;

    /**
     * 收入
     */
    private String income;

    /**
     * 总房间数
     */
    private String roomNum;

    /**
     * 总床位数
     */
    private String bedNum;

    /**
     * 上报事项
     */
    private String reportMatters;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除
     */
    private String idDelete;

    /**
     * 当前登录用户id
     */
    private Integer userId;

    /**
     * 剩余房间数
     */
    private String surplusRoomNum;

    /**
     * 剩余床位数
     */
    private String surplusBedNum;

    /**
     * 上报时间
     */
    private Date reportTime;

    /**
     * 逗留1天省内人数
     */
    private Integer stayOneIn;

    /**
     * 逗留2天省内人数
     */
    private Integer stayTwoIn;

    /**
     * 逗留3天省内人数
     */
    private Integer stayThreeIn;

    /**
     * 逗留4天省内人数
     */
    private Integer stayFourIn;

    /**
     * 逗留5天省内人数
     */
    private Integer stayFiveIn;

    /**
     * 逗留6天及以上省内人数
     */
    private Integer staySixIn;

    /**
     * 逗留1天省外人数
     */
    private Integer stayOneOut;

    /**
     * 逗留2天省外人数
     */
    private Integer stayTwoOut;

    /**
     * 逗留3天省外人数
     */
    private Integer stayThreeOut;

    /**
     * 逗留4天省外人数
     */
    private Integer stayFourOut;

    /**
     * 逗留5天省外人数
     */
    private Integer stayFiveOut;

    /**
     * 逗留6天及以上省外人数
     */
    private Integer staySixOut;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
