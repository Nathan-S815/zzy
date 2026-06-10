package com.nuwa.infrastructure.ticket.database.member.entity;

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
 * 联系人信息
 *
 * @author huyonghack@163.com
 * @since 2022-03-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ContactsInfo对象")
public class ContactsInfo extends Model<ContactsInfo> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("联系人姓名")
    private String name;

    @ApiModelProperty("联系人手机号")
    private String mobile;

    @ApiModelProperty("身份信息")
    private String idCard;

    @ApiModelProperty("是否默认 1:默认")
    private Integer defaultFlag;

    @ApiModelProperty("证件类型")
    private String certificateType;

    @ApiModelProperty("其他")
    private String other;

    @ApiModelProperty("创建时间")
    private Date createTime;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String NAME = "name";

    public static final String MOBILE = "mobile";

    public static final String ID_CARD = "id_card";

    public static final String DEFAULT_FLAG = "default_flag";

    public static final String CERTIFICATE_TYPE = "certificate_type";

    public static final String OTHER = "other";

    public static final String CREATE_TIME = "create_time";

}
