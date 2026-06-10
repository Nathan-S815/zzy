package com.nuwa.client.discovery.dto.clientobject.product.qry;

import io.swagger.annotations.ApiModel;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 景区产品退款规则配置 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "景区产品退款规则配置PageQry")
public class ScenicspotProductRefundRuleConfigPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

}
