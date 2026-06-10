package com.nuwa.attract.start.api.controller.travel.param;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author nanhuang @南皇
 * @date 2022-08-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "团队人员景点分布情况")
public class TeamCustomerPageParam extends NuwaPageQry {

    @ApiModelProperty(value = "团队ID", required = true)
    @NotNull(message = "团队id不能为空")
    private Long teamId;

    @ApiModelProperty(value = "查全部传空,传userId必传travelDate 景点/景区userId")
    private Long userId;

    @ApiModelProperty(value = "查全部传空,传userId必传此项 游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date travelDate;
}
