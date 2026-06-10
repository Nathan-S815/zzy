package com.nuwa.infrastructure.zeus.database.mch.entity;

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
 * 商户信息
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Merchant对象")
public class Merchant extends Model<Merchant> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商户Id")
    @TableId(value = "mch_id", type = IdType.AUTO)
    private Long mchId;

    @ApiModelProperty("账号类型 1:个人,2企业,3:政府组织")
    private Integer mchType;

    @ApiModelProperty("商户名称")
    private String mchName;

    @ApiModelProperty("联系人姓名")
    private String contentName;

    @ApiModelProperty("登录账号名")
    private String userName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("logo")
    @JsonSerialize(using = MaterialJson.class)
    private Long logo;

    @ApiModelProperty("所在地省")
    private String province;

    @ApiModelProperty("所在地市")
    private String city;

    @ApiModelProperty("所在地区")
    private String county;

    @ApiModelProperty("具体地址")
    private String address;

    @ApiModelProperty("联系电话")
    private String contentPhone;

    @ApiModelProperty("审核状态 0:等待审核 1:审核通过 2:审核失败")
    private Integer auditStatus;

    @ApiModelProperty("商户状态 0:停用, 1:启用 ")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("删除标志  0正常1删除")
    private Integer deleteFlag;


    public static final String MCH_ID = "mch_id";

    public static final String MCH_TYPE = "mch_type";

    public static final String MCH_NAME = "mch_name";

    public static final String CONTENT_NAME = "content_name";

    public static final String EMAIL = "email";

    public static final String LOGO = "logo";

    public static final String PROVINCE = "province";

    public static final String CITY = "city";

    public static final String COUNTY = "county";

    public static final String ADDRESS = "address";

    public static final String CONTENT_PHONE = "content_phone";

    public static final String AUDIT_STATUS = "audit_status";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
