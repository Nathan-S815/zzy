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
 * 路线客流表
 * </p>
 *
 * @author zzy
 * @since 2020-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseTravelLineReport extends Model<BaseTravelLineReport> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属旅行社id（旅行社基础信息表主键）
     */
    private Integer subTravelId;

    /**
     * 所属旅行社名称
     */
    private String subTravelName;

    /**
     * 路线产品名称
     */
    private String travelLine;

    /**
     * 路线人数
     */
    private Integer peopleNum;

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
     * 上报时间
     */
    private Date reportTime;

    /**
     * 路线介绍
     */
    private String lineIntroduction;

    /**
     * 出发时间
     */
    private String startTime;

    /**
     * 到达时间
     */
    private String endTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
