package com.nuwa.client.attract.dto.clientobject.travel.qry;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @author nanhuang @南皇
 * @date 2022-09-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PageQry")
public class OfficialTravelTeamPageQry extends NuwaPageQry {
    private static final long       serialVersionUID = 1L;
    @ApiModelProperty("团队id")
    private              Long       teamId;
    @ApiModelProperty("旅行社名称")
    private              String     mchName;
    @ApiModelProperty("旅行社ids")
    private              List<Long> userIds;
    @ApiModelProperty("1-待审核 2-审核通过 3-审核失败")
    private              Integer    status;
    @ApiModelProperty("申报时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private              Date       applyTime;
    @ApiModelProperty(value = "关联酒店id", hidden = true)
    private              Long       refId;
}
