package com.nuwa.client.ticket.dto.clientobject.mall.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 *  PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PageQry")
public class UserMallProductPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "排序方式 1按销量升序 2按销量降序 3按价格升序 4按价格降序 5按创建时间降序")
    private Integer orderType;

    @ApiModelProperty(value = "一级分类ID 1文创 2土特产")
    private Long classificationFirstId;

}
