package com.zzy.db.entity.portrait;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 景区用户逗留时长分析(月)
 * </p>
 *
 * @author zzy
 * @since 2020-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TouristStayScenicMonth extends Model<TouristStayScenicMonth> {

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
     * 逗留1天游客人数
     */
    private Integer stayOne;

    /**
     * 逗留2天游客人数
     */
    private Integer stayTwo;

    /**
     * 逗留3天游客人数
     */
    private Integer stayThree;

    /**
     * 逗留4天游客人数
     */
    private Integer stayFour;

    /**
     * 逗留5天及以上游客人数
     */
    private Integer stayFive;

    /**
     * 逗留6天游客人数
     */
    private Integer staySix;

    /**
     * 逗留7天游客人数
     */
    private Integer staySeven;

    /**
     * 逗留8天及以上游客人数
     */
    private Integer stayEight;

    /**
     * 游客平均逗留小时数
     */
    private BigDecimal stayAvg;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
