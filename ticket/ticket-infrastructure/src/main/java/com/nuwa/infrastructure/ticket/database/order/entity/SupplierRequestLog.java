package com.nuwa.infrastructure.ticket.database.order.entity;

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
 * 供应商请求日志
 *
 * @author huyonghack@163.com
 * @since 2021-12-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SupplierRequestLog对象")
public class SupplierRequestLog extends Model<SupplierRequestLog> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("请求url")
    private String url;

    @ApiModelProperty("请求报文")
    private String req;

    @ApiModelProperty("响应报文")
    private String resp;

    @ApiModelProperty("花费时间")
    private Double costTime;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("更新时间")
    private Date updateDate;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("日志类型 1:下单 2:支付 3:核销通知 4:退款通知 5:出票通知 6:退款")
    private Integer type;

    @ApiModelProperty("日志类型描述")
    private String typeName;

    @ApiModelProperty("业务订单id")
    private Long bizOrderId;

    @ApiModelProperty("请求流水号")
    private String requestNo;

    @ApiModelProperty("状态码")
    private Integer httpCode;

    @ApiModelProperty("结果描述")
    private String result;


    public static final String ID = "id";

    public static final String URL = "url";

    public static final String REQ = "req";

    public static final String RESP = "resp";

    public static final String COST_TIME = "cost_time";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    public static final String ORDER_ID = "order_id";

    public static final String ORDER_NO = "order_no";

    public static final String TYPE = "type";

    public static final String TYPE_NAME = "type_name";

    public static final String BIZ_ORDER_ID = "biz_order_id";

    public static final String REQUEST_NO = "request_no";

    public static final String HTTP_CODE = "http_code";

    public static final String RESULT = "result";

}
