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
public class BaseAdmin extends Model<BaseAdmin> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 管理员姓名
     */
    private String adminName;

    /**
     * 管理员电话
     */
    private String adminPhone;

    /**
     * 管理员账号id
     */
    private Integer userId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
    private int isDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
