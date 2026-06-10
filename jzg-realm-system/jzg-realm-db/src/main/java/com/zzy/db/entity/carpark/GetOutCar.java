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
 * 车辆出场纪录
 * </p>
 *
 * @author zzy
 * @since 2020-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GetOutCar extends Model<GetOutCar> {

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
     * 车牌号码
     */
    private String carNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 车辆类型
     */
    private String carTypeNo;

    /**
     * 出场时间
     */
    private String outTime;

    /**
     * 出口车道名称
     */
    private String outGateName;

    /**
     * 出口操作员
     */
    private String outOperatorName;

    /**
     * 车辆状态（0-未锁 1-锁定）
     */
    private String lockCar;

    /**
     * 出场图片
     */
    private String outImgPath;

    /**
     * 免费放行原因
     */
    private String freeReason;

    /**
     * 订单总金额（单位元）
     */
    private String totalAmount;

    /**
     * 入场时间
     */
    private String enterTime;

    private Date createTime;

    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.indexId;
    }

}
