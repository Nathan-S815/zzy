package com.zzy.db.entity.reportbase;

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
 * 景区上报基础信息表
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("report_base_scenic")
public class ReportBaseScenic extends Model<ReportBaseScenic> {

    private static final long serialVersionUID = 1L;

    /**
     * 景区id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 景区名称
     */
    private String scenicName;

    /**
     * 地址
     */
    private String address;

    /**
     * 景区联系电话
     */
    private String scenicPhone;

    /**
     * 联系人
     */
    private String contactPeople;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 营业执照
     */
    private String businessLicense;

    /**
     * 法人
     */
    private String legalPerson;

    /**
     * 法人身份证
     */
    private String legalIdcard;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 审核状态
     */
    private Integer auditState;

    /**
     * 当前登录用户id
     */
    private Integer userId;

    /**
     * 等级
     */
    private String grade;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 经度
     */
    private String lng;

    /**
     * 最大承载量
     */
    private String maxBearing;

    /**
     * 预警值
     */
    private String warningValue;

    /**
     * 当前客流人数
     */
    private String nowPeople;

    /**
     * 法人照片
     */
    private String legalPhoto;
    /**
     *简称
     */
    private String abbreviation;
    /**
     *报警时间
     */
    private String warningTime;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
