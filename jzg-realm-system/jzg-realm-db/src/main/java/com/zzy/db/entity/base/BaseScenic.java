package com.zzy.db.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 景区基础信息
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseScenic extends Model<BaseScenic> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String scenicName;

    /**
     * 等级
     */
    private String scenicLevel;

    /**
     * 统一信用代码
     */
    private String creditCode;

    /**
     * 行政区划
     */
    private String areaCode;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 经度
     */
    private String lng;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 类型
     */
    private String siteType;

    /**
     * 宜游月份
     */
    private String appropriateMonth;

    /**
     * 咨询电话
     */
    private String zxPhone;

    /**
     * 投诉电话
     */
    private String tsPhone;

    /**
     * 应急救援电话
     */
    private String emergencyPhone;

    /**
     * 瞬时承载量
     */
    private String instantCapacity;

    /**
     * 日承载量
     */
    private String dayCapacity;

    /**
     * 停车位数量
     */
    private String carParkNum;

    /**
     * 门票说明
     */
    private String ticketExplain;

    /**
     * 开放时间说明
     */
    private String openTime;

    /**
     * 优惠政策
     */
    private String favouredPolicy;

    /**
     * 温馨提示
     */
    private String remind;

    /**
     * 网址
     */
    private String website;

    /**
     * 微信名称
     */
    private String wechatName;

    /**
     * 微博名称
     */
    private String microblogName;

    /**
     * VR 地址
     */
    private String vrAddress;

    /**
     * 称号(多个用|分隔)
     */
    private String scenicTitle;

    /**
     * 线路建议
     */
    private String lineSuggest;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 是否删除
     */
    private Integer isDelete;

    private Integer userId;

    private Integer warning;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
