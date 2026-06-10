package com.nuwa.client.attract.dto.clientobject.travel.qry;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @date 2022-09-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PageQry")
public class TravelTeamPageQry extends NuwaPageQry {
    private static final long         serialVersionUID = 1L;
    @ApiModelProperty("团队id")
    private              Long         teamId;
    @ApiModelProperty("团队ids")
    private              List<String> teamIds;
    @ApiModelProperty("领队人姓名")
    private              String       leaderName;
    @ApiModelProperty("团队类型 1-老年团 2-亲子团 3-购物团 4-纯玩团 5-学生团 6-研学团 7-疗休养团")
    private              Integer      teamType;
    @ApiModelProperty("团队状态 0-未提交 1-景区酒店审核中 2-景区酒店审核失败 3-待文旅局审核 4-文旅局审核成功 5-文旅局审核失败")
    private              Integer      teamStatus;
    @ApiModelProperty("抵达单位名称")
    private              String       refMch;
    @ApiModelProperty("旅行社id")
    private              Long         userId;
    @ApiModelProperty(value = "排序,0-默认走id,1-奖励详情按照统计", hidden = true)
    private              Integer      orderBy;
    @ApiModelProperty("申报开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private              Date         applyBeginTime;
    @ApiModelProperty("申报结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private              Date         applyEndTime;
}
