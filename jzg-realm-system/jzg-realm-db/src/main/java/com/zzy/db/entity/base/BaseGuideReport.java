package com.zzy.db.entity.base;

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
 * 
 * </p>
 *
 * @author zzy
 * @since 2020-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseGuideReport extends Model<BaseGuideReport> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 导游id
     */
    private Integer guideId;

    /**
     * 信用分
     */
    private Integer credit;

    /**
     * 理由
     */
    private String reason;

    /**
     * 0加1减
     */
    private Integer type;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 上报时间
     */
    private LocalDateTime reportTime;

    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
