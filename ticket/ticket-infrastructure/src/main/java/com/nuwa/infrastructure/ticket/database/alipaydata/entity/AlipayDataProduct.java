package com.nuwa.infrastructure.ticket.database.alipaydata.entity;

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
 * 支付宝-景区产品
 *
 * @author huyonghack@163.com
 * @since 2022-05-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlipayDataProduct对象")
public class AlipayDataProduct extends Model<AlipayDataProduct> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("支付宝appId")
    private String alipayAppId;

    @ApiModelProperty("服务商id")
    private String isvId;

    @ApiModelProperty("产品id")
    private Integer productId;

    @ApiModelProperty("状态 init:初始  sync_success:同步成功 sync_failed:同步失败")
    private String status;

    @ApiModelProperty("服务商名称")
    private String sourceSystem;

    @ApiModelProperty("景区id")
    private String outerScenicId;

    @ApiModelProperty("门票类型 NORMAL(\"NORMAL\",\"普通\"), GROUP(\"GROUP\",\"套票\"), PERIOD(\"PERIOD\",\"时段票\"), REGION(\"REGION\",\"区域票\")")
    private String ticketType;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("支付宝产品id")
    private String alipayProductId;

    @ApiModelProperty("失败原因")
    private String remark;

    @ApiModelProperty("扩展参数")
    private String extJson;

    @ApiModelProperty("创建时间")
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    private Date gmtModified;


    public static final String ID = "id";

    public static final String ALIPAY_APP_ID = "alipay_app_id";

    public static final String ISV_ID = "isv_id";

    public static final String PRODUCT_ID = "product_id";

    public static final String STATUS = "status";

    public static final String SOURCE_SYSTEM = "source_system";

    public static final String OUTER_SCENIC_ID = "outer_scenic_id";

    public static final String TICKET_TYPE = "ticket_type";

    public static final String PRODUCT_NAME = "product_name";

    public static final String REMARK = "remark";

    public static final String EXT_JSON = "ext_json";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

    public static final String ALIPAY_PRODUCT_ID = "alipay_product_id";

}
