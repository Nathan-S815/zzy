package com.zzy.db.entity.hotmap;

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
 * 熊猫馆客流统计表
 * </p>
 *
 * @author zzy
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PandaFlow extends Model<PandaFlow> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 查询开始时间
     */
    private String startTime;

    /**
     * 查询结束时间
     */
    private String endTime;

    /**
     * 进入人数
     */
    private String inPeople;

    /**
     * 出去人数
     */
    private String outPeople;

    /**
     * 创建时间
     */
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
