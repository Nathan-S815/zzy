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
 * 政务宣传
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InfoPromotionVideo extends Model<InfoPromotionVideo> {

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
     * 视频名称
     */
    private String videoName;

    /**
     * 视频地址
     */
    private String videoAddress;

    /**
     * 有效=1；无效=2（只能有一个视频是有效的）
     */
    private Integer videoStatus;

    /**
     * 登录用户名称
     */
    private String createName;

    /**
     * 视频描述
     */
    private String descriptio;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
