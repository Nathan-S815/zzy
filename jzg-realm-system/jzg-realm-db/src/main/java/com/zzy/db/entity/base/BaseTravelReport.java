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
 * 旅行社客流收入
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseTravelReport extends Model<BaseTravelReport> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属旅行社id
     */
    private String subTravelId;

    /**
     * 所属旅行社名称
     */
    private String subTravelName;

    /**
     * 进入人数
     */
    private String inPeople;

    /**
     * 出去人数
     */
    private String outPeople;

    /**
     * 收入
     */
    private String income;

    /**
     * 上报事项
     */
    private String reportMatters;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除
     */
    private String idDelete;


    private Integer userId;

    /**
     * 所属景区id
     */
    private Integer subScenicId;

    private Date reportTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
