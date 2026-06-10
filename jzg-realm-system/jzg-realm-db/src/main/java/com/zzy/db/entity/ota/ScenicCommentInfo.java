package com.zzy.db.entity.ota;

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
 * 酒店OTA评论数据
 * </p>
 *
 * @author zzy
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ScenicCommentInfo extends Model<ScenicCommentInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String placeKeyWord;

    /**
     * 评论对象
     */
    private String commentPlaceName;

    /**
     * 评论时间
     */
    private LocalDateTime commentTime;

    /**
     * 评论者
     */
    private String commentUser;

    /**
     * 评价类型(好评:1,差评:0,中评:2)
     */
    private Integer commentType;

    /**
     * 评分
     */
    private Double commentScore;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论数据来源
     */
    private String commentSource;

    /**
     * 数据入库时间
     */
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
