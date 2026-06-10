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
 * 餐厅基础信息表
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseRestaurant extends Model<BaseRestaurant> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 餐厅名称
     */
    private String restaurantName;

    /**
     * 等级
     */
    private String restaurantLevel;

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
     * 咨询电话
     */
    private String zxPhone;

    /**
     * 投诉电话
     */
    private String tsPhone;

    /**
     * 包间数
     */
    private String roomNum;

    /**
     * 停车位数量
     */
    private Integer carParkNum;

    /**
     * 营业时间
     */
    private String businessTime;

    /**
     * 网址
     */
    private String website;

    /**
     * 预定网址
     */
    private String reservationWebsite;

    /**
     * 微信名称
     */
    private String wechatName;

    /**
     * 微博名称
     */
    private String microblogName;

    /**
     * 房价说明
     */
    private String priceDescript;

    /**
     * 酒店说明
     */
    private String hotelDescript;

    /**
     * 优惠政策
     */
    private String favouredPolicy;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
