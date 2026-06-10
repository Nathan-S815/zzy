package com.zzy.db.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 导游基础信息表
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseGuide extends Model<BaseGuide> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 导游姓名
     */
    private String guideName;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 出生年月 
     */
    private String birthday;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 民族
     */
    private String nation;

    /**
     * 学历 
     */
    private Integer educational;

    /**
     * 专业
     */
    private String major;

    /**
     * 毕业院校
     */
    private String graduationUniversity;

    /**
     * 所属旅行社
     */
    private String subTravel;

    /**
     * 导游证书
     */
    private String guideCertificate;

    /**
     * 等级证号
     */
    private Integer gradeNum;

    /**
     * 导游等级
     */
    private String guideGrade;

    /**
     * 语言
     */
    private String languageGrade;

    /**
     * 资格证书
     */
    private String seniorityCertificate;

    /**
     * 工作性质 0-全职 1-兼职
     */
    private Integer natureWork;

    /**
     * 注册机构名称
     */
    private String registeOrganName;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 审批时间
     */
    private Date approvalTime;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
