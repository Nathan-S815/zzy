package com.nuwa.app.ticket.command.one.query;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户景区权益分页参数")
public class MerchantScenicsportRightsPageQuery extends NuwaPageQry {
    @ApiModelProperty(value = "景区id", required = true)
    @NotNull
    private Long scenicspotId;
}
