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
 * 餐饮场所收入表
 * </p>
 *
 * @author zzy
 * @since 2020-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseRestaurantReport extends Model<BaseRestaurantReport> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属餐饮场所id（餐饮场所基础信息表主键）
     */
    private Integer subRestaurantId;

    /**
     * 所属餐饮场所名称
     */
    private String subRestaurantName;

    /**
     * 消费人数
     */
    private String inPeople;

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
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除
     */
    private String idDelete;

    /**
     * 当前登录用户id
     */
    private Integer userId;

    /**
     * 上报时间
     */
    private Date reportTime;

    /**
     * 所属景区id
     */
    private Integer subScenicId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
