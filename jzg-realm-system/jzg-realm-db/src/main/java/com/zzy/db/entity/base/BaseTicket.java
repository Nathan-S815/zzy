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
 * @since 2020-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseTicket extends Model<BaseTicket> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 景区id
     */
    private Integer scenicId;

    /**
     * 景区名称
     */
    private String scenicName;

    /**
     * 入园人数
     */
    private Integer inPeople;

    /**
     * 收入
     */
    private Integer income;

    /**
     * 时间
     */
    private String reportTime;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
