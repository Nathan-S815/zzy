package com.zzy.db.entity.hotmap;

import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @since 2020-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class News extends Model<News> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 内容
     */
    private String content;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
