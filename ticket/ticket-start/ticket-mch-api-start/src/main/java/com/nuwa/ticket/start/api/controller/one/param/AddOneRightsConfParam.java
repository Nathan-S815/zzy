package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 新增权益
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddOneRightsConfParam extends NuwaCommand {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("景区id(-1代表通用权益 其他代表景区特有权益)")
    private Long scenicspotId;

    @ApiModelProperty("有效期模式 range_date(日期范围) long_time(长期)")
    private String validityMode;

    @ApiModelProperty("开始日期")
    private Date validityBeginDate;

    @ApiModelProperty("结束日期")
    private Date validityEndDate;

    @ApiModelProperty("支持的身份列表(逗号隔开,-1代表全部角色可用)")
    private String identityCodeList;

    @ApiModelProperty("权益类型 discount(折扣)")
    private String rightsType;

    @ApiModelProperty("折扣值")
    private BigDecimal discountValue;

    @ApiModelProperty("备注信息")
    private String remark;

}
