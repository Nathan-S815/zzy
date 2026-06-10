package com.nuwa.attract.start.api.controller.travel.param;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * 新增旅行团队
 *
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.travel.param:AddTravelTeamParam.java,v1.0.0 2022-09-14 13:45:43
 * nanHuang Exp $
 */
@Data
public class AddTravelTeamParam {
    private static final long    serialVersionUID = 1L;

    @ApiModelProperty(value = "团队ID")
    private Long                          teamId;
    @ApiModelProperty(value = "团队名", required = true)
    @NotBlank(message = "团队名不能为空")
    private String                        teamName;
    @ApiModelProperty(value = "领队人姓名", required = true)
    @NotBlank(message = "领队人姓名不能为空")
    private String                        leaderName;
    @ApiModelProperty(value = "领队人电话", required = true)
    @NotBlank(message = "领队人电话不能为空")
    private String                        leaderPhone;
    @ApiModelProperty(value = "领队人身份证", required = true)
    @NotBlank(message = "领队人身份证不能为空")
    private String                        leaderIdcard;
    @ApiModelProperty(value = "团队性质 1-境内游客团 2-境外游客团", required = true)
    @NotNull(message = "团队性质不能为空")
    private Integer                       teamNature;
    @ApiModelProperty(value = "团队类型 1-老年团 2-亲子团 3-购物团 4-纯玩团 5-学生团 6-研学团 7-疗休养团", required = true)
    @NotNull(message = "团队类型不能为空")
    private Integer                       teamType;
    @ApiModelProperty(value = "行程开始时间 yyyy-MM-dd", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date                          beginDate;
    @ApiModelProperty(value = "团队人数", required = true)
    @NotNull(message = "团队人数不能为空")
    private Integer                       teamPerson;
    @ApiModelProperty(value = "行程结束时间 yyyy-MM-dd", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date                          endDate;
    @ApiModelProperty(value = "团队游客（这里是文件里全部的用户）", required = true)
    private List<TravelTeamCustomerParam> teamCustomerParams;
    @ApiModelProperty(value = "行程详细安排", required = true)
    @NotEmpty(message = "行程详细安排不能为空")
    private List<TravelTeamInfo>          travelTeamInfoList;
}
