package com.nuwa.infrastructure.discovery.database.sms.entity;

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
 * 商户短信模板
 *
 * @author huyonghack@163.com
 * @since 2021-11-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SmsTemplate对象")
public class SmsTemplate extends Model<SmsTemplate> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("模板内容")
    private String content;

    @ApiModelProperty("模板标题")
    private String title;

    @ApiModelProperty("业务类型")
    private String bizCode;

    @ApiModelProperty("创建时间")
    private Date createTime;

    private String createByName;

    private String createById;

    private String lastUpdateByName;

    private String lastUpdateById;

    private Date lastUpdateTime;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String CONTENT = "content";

    public static final String TITLE = "title";

    public static final String BIZ_CODE = "biz_code";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
