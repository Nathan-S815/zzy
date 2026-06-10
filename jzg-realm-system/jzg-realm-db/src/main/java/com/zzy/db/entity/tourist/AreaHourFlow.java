package com.zzy.db.entity.tourist;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 区县小时客流量统计
 * </p>
 *
 * @author zzy
 * @since 2020-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AreaHourFlow extends Model<AreaHourFlow> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 数据进表时间
     */
    private LocalDateTime entTime;

    /**
     * 账期YYYYMM
     */
    private String accTime;

    /**
     * 日期格式：DD
     */
    private String dd;

    /**
     * 小时时段格式：HH24，00-23
     */
    private String hh;

    /**
     * 省份编码
     */
    private String provinceId;

    /**
     * 地市编码
     */
    private String cityId;

    /**
     * 区县编码
     */
    private String countyId;

    /**
     * 前1小时时段用户数
     */
    private Integer befOneHh;



    @Override
    protected Serializable pkVal() {
        return null;
    }

}
