package com.nuwa.infrastructure.attract.database.user.entity;

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
 * 
 *
 * @author nanhuang @南皇
 * @since 2022-09-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AttractUser对象")
public class AttractUser extends Model<AttractUser> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("商户名称（酒店、景区、旅行社）")
    private String mchName;

    @ApiModelProperty("省份名")
    private String province;

    @ApiModelProperty("省份编号")
    private String provinceId;

    @ApiModelProperty("城市名")
    private String city;

    @ApiModelProperty("城市编码")
    private String cityId;

    @ApiModelProperty("地区名")
    private String area;

    @ApiModelProperty("地区编码")
    private String areaId;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("电话")
    private String tel;

    @ApiModelProperty("联系人姓名")
    private String linkName;

    @ApiModelProperty("联系人电话")
    private String linkPhone;

    @ApiModelProperty("营业执照图片id")
    private String licenseImageId;

    @ApiModelProperty("统一社会信用代码")
    private String socialCreditCode;

    @ApiModelProperty("账号类型 0-文旅局 1-景区 2-酒店 3-旅行社")
    private Integer accountType;

    @ApiModelProperty("审核状态 0-未审核 1-审核通过 2-审核拒绝 3-禁用")
    private Integer reviewStatus;

    @ApiModelProperty("创建时间")
    private Date createTime;

    private String createByName;

    private Long createById;

    private String lastUpdateByName;

    private Long lastUpdateById;

    private Date lastUpdateTime;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;


    public static final String USER_ID = "user_id";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String EMAIL = "email";

    public static final String MCH_NAME = "mch_name";

    public static final String PROVINCE = "province";

    public static final String PROVINCE_ID = "province_id";

    public static final String CITY = "city";

    public static final String CITY_ID = "city_id";

    public static final String AREA = "area";

    public static final String AREA_ID = "area_id";

    public static final String ADDRESS = "address";

    public static final String TEL = "tel";

    public static final String LINK_NAME = "link_name";

    public static final String LINK_PHONE = "link_phone";

    public static final String LICENSE_IMAGE_ID = "license_image_id";

    public static final String SOCIAL_CREDIT_CODE = "social_credit_code";

    public static final String ACCOUNT_TYPE = "account_type";

    public static final String REVIEW_STATUS = "review_status";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
