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
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseNotice extends Model<BaseNotice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 来源
     */
    private String source;

    /**
     * 类别
     */
    private String type;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 图片
     */
    private String pic;

    /**
     * 内容
     */
    private String content;

    private String createTime;

    private Integer isDelete;

    private String updateTime;

    private String reportTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
