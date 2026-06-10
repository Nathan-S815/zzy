package com.zzy.db.entity.portrait;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 景区旅客画像-游客性别、年龄统计(日)
 * </p>
 *
 * @author zzy
 * @since 2020-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TouristPortraitScenicDay extends Model<TouristPortraitScenicDay> {

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
     * 男性游客人数
     */
    private Integer menNum;

    /**
     * 女性游客人数
     */
    private Integer womanNum;

    /**
     * 少年人数年龄7－17岁
     */
    private Integer juvNum;

    /**
     * 青年人数年龄18-40岁
     */
    private Integer youngNum;

    /**
     * 中年人数年龄40-65岁
     */
    private Integer midAgeNum;

    /**
     * 老年人数年龄66岁以上
     */
    private Integer oldNum;

    /**
     * 未知年龄的游客人数
     */
    private Integer noAgeNum;

    /**
     * 未知性别的游客数量
     */
    private Integer noSexNum;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
