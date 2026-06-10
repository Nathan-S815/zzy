package com.zzy.db.entity.eventdepart;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zzy
 * @since 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EventFeedbackFile extends Model<EventFeedbackFile> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    private Integer feedBackId;

    /**
     * 图片路径
     */
    private String pic1Path;

    /**
     * 图片路径
     */
    private String pic2Path;

    /**
     * 图片路径
     */
    private String pic3Path;

    /**
     * 其他文件路径
     */
    private String filePath;

    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.feedBackId;
    }

}
