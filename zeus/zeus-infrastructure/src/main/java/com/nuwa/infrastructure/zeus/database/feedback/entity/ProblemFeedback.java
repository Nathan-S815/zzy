package com.nuwa.infrastructure.zeus.database.feedback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.zeus.util.MaterialJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商户问题反馈信息
 *
 * @author huyonghack@163.com
 * @since 2021-07-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProblemFeedback对象")
public class ProblemFeedback extends Model<ProblemFeedback> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商户ID")
    private Long mchId;

    @ApiModelProperty("商户信息")
    private String mchName;

    @ApiModelProperty("注册手机号")
    private String registPhone;

    @ApiModelProperty("联系人")
    private String contactPeople;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("反馈内容")
    private String contentBack;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("删除标志  0正常1删除")
    private Integer deleteFlag;

    @ApiModelProperty("图片")
    @JsonSerialize(using = MaterialJson.class)
    private String image;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String MCH_NAME = "mch_name";

    public static final String REGIST_PHONE = "regist_phone";

    public static final String CONTACT_PEOPLE = "contact_people";

    public static final String CONTACT_PHONE = "contact_phone";

    public static final String STATUS = "status";

    public static final String CONTENT_BACK = "content_back";

    public static final String REMARK = "remark";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";
    public static final String IMAGE = "image";

}
