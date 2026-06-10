package com.zzy.db.entity.tourist;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 景区游客来源地市分析(日)
 * </p>
 *
 * @author zzy
 * @since 2020-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TouristSourceScenicDay extends Model<TouristSourceScenicDay> {

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
    private Long accTime;

    /**
     * 省份编码
     */
    private String provinceId;

    /**
     * 地市编码
     */
    private String cityId;

    /**
     * 景区编码
     */
    private String sceneId;

    /**
     * 来源省份编码
     */
    private String soProvince;

    /**
     * 来源地市编码
     */
    private String soCity;

    /**
     * 游客人数
     */
    private Integer num;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
