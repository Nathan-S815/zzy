package com.nuwa.infrastructure.zeus.database.other.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * app试用申请
 *
 * @author huyonghack@163.com
 * @since 2022-07-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AppApply对象")
public class AppApply extends Model<AppApply> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("页面名称")
    private String appName;

    @ApiModelProperty("申请企业名称")
    private String applyCompanyName;

    @ApiModelProperty("申请企业类型")
    private String applyCompanyType;

    @ApiModelProperty("申请企业行业")
    private String applyCompanyIndustry;

    @ApiModelProperty("联系人")
    private String linkman;

    @ApiModelProperty("企业痛点")
    private String applyContent;

    @ApiModelProperty("其他需求")
    private String otherContent;

    @ApiModelProperty("联系电话")
    private String linkMobile;

    @ApiModelProperty("申请时间")
    private Date createTime;


    public static final String ID = "id";

    public static final String APP_NAME = "app_name";

    public static final String APPLY_COMPANY_NAME = "apply_company_name";

    public static final String APPLY_COMPANY_TYPE = "apply_company_type";

    public static final String APPLY_COMPANY_INDUSTRY = "apply_company_industry";

    public static final String LINKMAN = "linkman";

    public static final String APPLY_CONTENT = "apply_content";

    public static final String OTHER_CONTENT = "other_content";

    public static final String LINK_MOBILE = "link_mobile";

    public static final String CREATE_TIME = "create_time";

}
