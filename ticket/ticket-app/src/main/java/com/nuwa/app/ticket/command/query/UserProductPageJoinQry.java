package com.nuwa.app.ticket.command.query;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户景区分页PageQry")
public class UserProductPageJoinQry extends NuwaPageQry {

    @ApiModelProperty("所属景点id")
    @NotNull(message = "景区Id不可能为空")
    private Long scenicspotId;

    @ApiModelProperty("所属商户id")
    @NotNull(message = "所属商户id不可能为空")
    private String mchId;
}
