package com.nuwa.client.zeus.dto.clientobject.mch.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 * <pre>
 * 商户信息 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户信息PageQry")
public class MerchantPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户名称")
    private String mchName;

    @ApiModelProperty("联系人姓名")
    private String contentName;

    @ApiModelProperty("具体地址")
    private String address;

    @ApiModelProperty("账号")
    private String userName;

    @ApiModelProperty("联系电话")
    private String contentPhone;

    @ApiModelProperty(value = "商户状态 0:停用, 1:启用")
    private Integer status;

}
