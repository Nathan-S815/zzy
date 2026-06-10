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
 * 活动宣传
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InfoActivityPropagate extends Model<InfoActivityPropagate> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 活动宣传地址
     */
    private String activityPictureAddress;

    /**
     * 活动宣传描述
     */
    private String activtyDescriptio;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 当前登录用户名称
     */
    private String createName;

    /**
     * 当前登录用户id
     */
    private Integer createId;

    /**
     * 是否启用 0-启用 1-未启用
     */
    private Integer isEable;

    /**
     * 是否删除  0-未删除 1-已删除
     */
    private Integer isDelete;

    /**
     * 发布平台
     */
    private String releasePlace;

    /**
     * 使用位置
     */
    private String usePlace;

    /**
     * 活动标题
     */
    private String activityTitle;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
