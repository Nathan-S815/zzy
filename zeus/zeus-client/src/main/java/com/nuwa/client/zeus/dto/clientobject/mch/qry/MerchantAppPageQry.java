package com.nuwa.client.zeus.dto.clientobject.mch.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 商户应用 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户应用PageQry")
public class MerchantAppPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

}
