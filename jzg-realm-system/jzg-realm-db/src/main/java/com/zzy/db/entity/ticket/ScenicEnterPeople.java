package com.zzy.db.entity.ticket;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 景区当日入园人数表
 * </p>
 *
 * @author zzy
 * @since 2020-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ScenicEnterPeople extends Model<ScenicEnterPeople> {

    private static final long serialVersionUID = 1L;

    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    /**
     * api数据Id
     */
    private Integer apiId;

    private String name;

    /**
     * api记录时间
     */
    private LocalDateTime recordDate;

    /**
     * 入园人数
     */
    private Long enterNumber;

    /**
     * 剩余可容纳人数
     */
    private Long restNumber;

    /**
     * 收入
     */
    private Long income;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
