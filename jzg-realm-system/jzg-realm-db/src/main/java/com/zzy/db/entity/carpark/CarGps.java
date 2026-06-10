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
 * 车辆gps信息表
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CarGps extends Model<CarGps> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer apiId;

    /**
     * 车牌号
     */
    private String vehicleNo;

    /**
     * 经度
     */
    private String lon;

    /**
     * 纬度
     */
    private String lat;

    private String date;

    private String altitude;

    private String vehicleColor;

    private String dataLength;

    private String vec1;

    private String vec2;

    private String vec3;

    private String encrypt;

    private String dataType;

    private String alarm;

    private String time;

    private String state;

    private String direction;

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
