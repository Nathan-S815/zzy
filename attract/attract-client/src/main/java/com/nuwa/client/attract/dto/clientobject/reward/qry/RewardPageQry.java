package com.nuwa.client.attract.dto.clientobject.reward.qry;

import java.util.List;

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
 * @date 2022-09-21
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PageQry")
public class RewardPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("旅行社名称")
    private String mchName;

    @ApiModelProperty("旅行社id")
    private List<Long> userIds;

    @ApiModelProperty("0-未发放 1-已发放")
    private Integer reviewStatus;
}
