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
 * 车辆入场纪录
 * </p>
 *
 * @author zzy
 * @since 2020-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GetEnterCar extends Model<GetEnterCar> {

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
     * 入场时间
     */
    private String enterTime;

    /**
     * 入口车道名称
     */
    private String enterGateName;

    /**
     * 入口操作员
     */
    private String enterOperatorName;

    /**
     * 车辆状态（0-未锁 1-锁定）
     */
    private String lockCar;

    /**
     * 入场图片
     */
    private String enterImgPath;

    private Date createTime;

    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.indexId;
    }

}
