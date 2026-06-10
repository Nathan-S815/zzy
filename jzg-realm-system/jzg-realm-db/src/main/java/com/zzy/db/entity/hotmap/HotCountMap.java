package com.zzy.db.entity.hotmap;

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
 * 热力图
 * </p>
 *
 * @author zzy
 * @since 2020-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HotCountMap extends Model<HotCountMap> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 日期
     */
    private Integer dateLog;

    /**
     * 地点名称
     */
    private String locationName;

    /**
     * 经度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 数量
     */
    private Long count;

    /**
     * 地址
     */
    private String address;

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
