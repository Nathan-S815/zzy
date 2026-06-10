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
 * 旅行社
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseTravel extends Model<BaseTravel> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 旅行社名称
     */
    private String travelName;

    /**
     * 等级
     */
    private String travelLevel;

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
     * 许可证号
     */
    private String licenseNo;

    /**
     * 法人姓名
     */
    private String legalPerson;

    /**
     * 出资人信息
     */
    private String contributor;

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
