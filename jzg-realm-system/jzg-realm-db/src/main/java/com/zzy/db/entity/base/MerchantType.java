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
 * @since 2020-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MerchantType extends Model<MerchantType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String typeName;

    private String typeCode;

    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
