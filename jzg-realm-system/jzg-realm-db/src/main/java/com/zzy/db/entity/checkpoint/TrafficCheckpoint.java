package com.zzy.db.entity.checkpoint;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zzy
 * @since 2020-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TrafficCheckpoint extends Model<TrafficCheckpoint> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * id
     */
    private String rowId;

    /**
     * 机动车id
     */
    private String vehicleId;

    /**
     * 车辆类型
     */
    private String vehicleType;

    /**
     * 车身颜色
     */
    private String vehicleColor;

    /**
     * 车辆速度
     */
    private String speedValue;

    /**
     * 速度类型
     */
    private String speedType;

    /**
     * 车辆车标
     */
    private String vehicleBrand;

    /**
     * 车牌号码
     */
    private String plateNumber;

    /**
     * 车牌类型
     */
    private String plateType;

    /**
     * 行驶方向
     */
    private String moveDirection;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 是否是进城方向
     */
    private Integer isIntown;

    private Integer isDelete;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 告警描述
     */
    private String alarmDescription;

    /**
     * 告警时间
     */
    private String alarmTime;

    /**
     * 告警序号
     */
    private String alarmSequence;

    /**
     * 告警源名称
     */
    private String alarmName;

    /**
     * 通知类型
     */
    private String alarmType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
