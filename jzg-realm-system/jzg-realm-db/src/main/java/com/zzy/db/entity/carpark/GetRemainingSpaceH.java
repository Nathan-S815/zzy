package com.zzy.db.entity.carpark;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 停车场剩余车位(历史)
 * </p>
 *
 * @author zzy
 * @since 2020-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GetRemainingSpaceH extends Model<GetRemainingSpaceH> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 剩余车位
     */
    private String remaiSpaces;

    /**
     * 总车位
     */
    private String totalSpaces;

    /**
     * 车场唯一编号
     */
    private String parkKey;

    /**
     * 对应时间
     */
    private LocalDateTime getTime;


    private Date createTime;

    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
