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
 * 疏导分流
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InfoEvacuation extends Model<InfoEvacuation> {

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
     * 当前登录用户id
     */
    private Integer createId;

    /**
     * 当前登录用户
     */
    private String createName;

    /**
     * 疏导分流图片地址
     */
    private String evacuationpictureAddress;

    /**
     * 疏导分流描述
     */
    private String evacuationDescription;

    /**
     * 景区名称
     */
    private String scenicName;

    /**
     * 是否删除 0-未删除 1-已删除
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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
