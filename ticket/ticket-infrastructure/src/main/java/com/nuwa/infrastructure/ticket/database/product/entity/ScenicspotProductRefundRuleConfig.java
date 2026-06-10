package com.nuwa.infrastructure.ticket.database.product.entity;

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
 * 景区产品退款规则配置
 *
 * @author huyonghack@163.com
 * @since 2021-12-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ScenicspotProductRefundRuleConfig对象")
public class ScenicspotProductRefundRuleConfig extends Model<ScenicspotProductRefundRuleConfig> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date createTime;

    private Integer deleteFlag;

    private String createByName;

    private String createById;

    private String lastUpdateByName;

    private String lastUpdateById;

    private Date lastUpdateTime;

    @ApiModelProperty("退款模式 0:自动退 1：不可退款")
    private Integer refundMode;

    @ApiModelProperty("审核模式 0:人工 1:系统自动")
    private Integer auditMode;

    @ApiModelProperty("自动处理天数")
    private Integer autoProcessDay;

    @ApiModelProperty("景区产品id")
    private Long scenicspotProductId;

    @ApiModelProperty("退款补充说明")
    private String instruction;

    @ApiModelProperty("版本号")
    @Version
    private Long version;


    public static final String ID = "id";

    public static final String CREATE_TIME = "create_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String REFUND_MODE = "refund_mode";

    public static final String AUDIT_MODE = "audit_mode";

    public static final String AUTO_PROCESS_DAY = "auto_process_day";

    public static final String SCENICSPOT_PRODUCT_ID = "scenicspot_product_id";

    public static final String INSTRUCTION = "instruction";

    public static final String VERSION = "version";

}
