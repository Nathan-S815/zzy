package com.nuwa.infrastructure.ticket.database.diy.entity;

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
 * 商户渠道装修
 *
 * @author huyonghack@163.com
 * @since 2022-03-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantDiyTemplate对象")
public class MerchantDiyTemplate extends Model<MerchantDiyTemplate> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("模板标识")
    private String mark;

    @ApiModelProperty("类型 DIYVIEW_INDEX:主页 DIYVIEW_USER_CENTER:个人中心 DIYVIEW_BOTTOM_BAR:底部导航")
    private String type;

    @ApiModelProperty("模板数据")
    private String value;

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty("商户应用id")
    private Long appId;

    @ApiModelProperty("快照  ONLINE:线上  LOCAL:本地")
    private String snapshoot;

    @ApiModelProperty("发布时间")
    private Date publishTime;

    @ApiModelProperty("最后更新时间")
    private Date lastUpdateTime;

    @ApiModelProperty("最后更新人id")
    private String lastUpdateById;

    @ApiModelProperty("最后更新人姓名")
    private String lastUpdateByName;

    @ApiModelProperty("创建人")
    private String createById;

    @ApiModelProperty("创建人姓名")
    private String createByName;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("删除标记")
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String MARK = "mark";

    public static final String TYPE = "type";

    public static final String VALUE = "value";

    public static final String MERCHANT_ID = "merchant_id";

    public static final String APP_ID = "app_id";

    public static final String SNAPSHOOT = "snapshoot";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_TIME = "create_time";

    public static final String DELETE_FLAG = "delete_flag";

}
