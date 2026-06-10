package com.zzy.db.entity.carpark;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 车辆静态信息表
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CarInfo extends Model<CarInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * api数据id
     */
    private Integer apiId;

    /**
     * 公司名称
     */
    private String owersName;

    /**
     * 公司电话
     */
    private String owersTel;

    /**
     * 公司编号
     */
    private String owersId;

    /**
     * 国有车辆
     */
    private String vehicleNationalit;

    /**
     * 车牌号
     */
    private String vehicleNo;

    /**
     * 车辆颜色数字
     */
    private String vehicleColor;

    private String dataType;

    private String vehicleType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
