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
 * 部员表
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DepartmentMember extends Model<DepartmentMember> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属部门ID
     */
    private Integer departmentId;

    private Integer isLoginEnable;

    private String headIcon;

    private String loginName;

    private Integer userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 职称
     */
    private String title;

    /**
     * 性别(0:女,1:男)
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phoneNumber;

    private Date birth;

    private Date createTime;

    private Date updateTime;

    /**
     * 邮件地址
     */
    private String email;

    /**
     * 是否删除(1:是,0:否)
     */
    private Integer isDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
