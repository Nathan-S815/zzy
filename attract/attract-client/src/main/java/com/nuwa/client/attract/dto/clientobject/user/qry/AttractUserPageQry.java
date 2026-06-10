package com.nuwa.client.attract.dto.clientobject.user.qry;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  PageQry参数对象
 * </pre>
 *
 * @author nanhuang @南皇
 * @date 2022-09-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PageQry")
public class AttractUserPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户名称（酒店、景区、旅行社的名字）")
    private String mchName;

    @ApiModelProperty(value = "账号类型 0-文旅局 1-景区 2-酒店 3-旅行社", required = true)
    @NotNull(message = "账号类型不能为空")
    private Integer accountType;

    @ApiModelProperty("审核状态 0-未审核 1-审核通过 2-审核拒绝  3-禁用 不传就是全部")
    private Integer reviewStatus;

}
