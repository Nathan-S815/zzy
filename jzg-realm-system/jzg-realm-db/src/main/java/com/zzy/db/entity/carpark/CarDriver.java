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
 * 驾驶员信息表
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CarDriver extends Model<CarDriver> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer apiId;

    /**
     * 驾驶员名字
     */
    private String driverName;

    /**
     * 电话
     */
    private String tel;

    /**
     * 驾驶车牌号
     */
    private String vehicleCode;

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
