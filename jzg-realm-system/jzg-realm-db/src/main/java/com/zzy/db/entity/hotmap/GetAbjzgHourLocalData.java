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
 * 按小时指定查询时间，反馈阿坝九寨沟县等区域小时客流数据
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GetAbjzgHourLocalData extends Model<GetAbjzgHourLocalData> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 区域id
     */
    private String scenicId;

    /**
     * 区域名称
     */
    private String scenicName;

    /**
     * 人数
     */
    private Integer peopleNum;

    /**
     * 人员类型 0为游客
     */
    private Integer memberType;

    /**
     * 时间
     */
    private String time;

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
