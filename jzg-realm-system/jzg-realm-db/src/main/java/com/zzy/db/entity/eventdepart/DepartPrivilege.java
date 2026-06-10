package com.zzy.db.entity.eventdepart;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DepartPrivilege extends Model<DepartPrivilege> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * department_info表主键
     */
    private Integer departId;

    /**
     * 权限名称
     */
    private String privilegeName;

    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
