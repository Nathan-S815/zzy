package com.zzy.db.entity.base;

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
 * 欢迎词设置
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InfoInformationMessage extends Model<InfoInformationMessage> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 登录用户名称
     */
    private String createName;

    /**
     * 登录用户id
     */
    private Integer createId;

    /**
     * 欢迎词
     */
    private String welcomeContent;

    /**
     * 是否启用 0-启用（默认）1-未启用
     */
    private Integer isEnable;

    /**
     * 是否删除  0-未删除 1-已删除
     */
    private Integer isDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
