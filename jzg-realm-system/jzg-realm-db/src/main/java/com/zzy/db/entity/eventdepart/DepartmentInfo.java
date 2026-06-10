package com.zzy.db.entity.eventdepart;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DepartmentInfo extends Model<DepartmentInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门职责
     */
    private String duty;

    /**
     * 部门负责人
     */
    private Integer leader;


    /**
     * 负责人pc登录账号
     */
    private String leaderLoginName;

    private Integer leaderUserId;

    /**
     * 部门地址
     */
    private String address;


    /**
     * 部门联系电话
     */
    private String phone;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
