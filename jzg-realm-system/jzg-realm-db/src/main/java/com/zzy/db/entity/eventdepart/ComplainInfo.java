package com.zzy.db.entity.eventdepart;

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
 * 小程序投诉表
 * </p>
 *
 * @author zzy
 * @since 2020-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ComplainInfo extends Model<ComplainInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 投诉主题
     */
    private String complainName;

    /**
     * 投诉内容
     */
    private String complainContent;

    /**
     * 投诉类型
     */
    private String complainType;

    /**
     * 投诉状态
     */
    private Integer state;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 投诉结果描述
     */
    private String complainResult;
    /**
     * 投诉单位
     */
    private String complainUnit;
    /**
     * 相关图片
     */
    private String picUrl;
    /**
     * 相关视频
     */
    private String voideUrl;
    /**
     * 处理方式
     */
    private String handling;
    /**
     * 补充说明
     */
    private String replenish;
    /**
     * 投诉人
     */
    private String complainPeople;
    /**
     * 投诉人电话
     */
    private String phone;
    /**
     * 投诉人微信头像
     */
    private String peoplePic;
    /**
     * b2b投诉数据id
     */
    private Integer complainId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
