package com.zzy.db.entity.carpark;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 车辆报警信息表
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CarAlarm extends Model<CarAlarm> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer apiId;

    /**
     * 车牌号
     */
    private String vehicleNo;

    /**
     * 报警信息
     */
    private String infoContent;

    private LocalDateTime warnTypeTime;

    private String warnType;

    /**
     * 静态信息id
     */
    private String infoId;

    private String dataType;

    private String infoLength;

    private String dataLength;

    private String vehicleColor;

    private String warnSrc;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
