package com.nuwa.infrastructure.vo;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * POI分页查询VO
 *
 * @author nanHuang @南皇
 * @version com.nuwa.infrastructure.hotel.database.hotelPoi.vo:HotelPoiPageVO.java,v1.0.0 2022-08-01 14:39:53
 * nanHuang Exp $
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "旅游局团队审核")
public class OfficialTravelTeamPageVO extends Model<OfficialTravelTeamPageVO> {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("团队id")
    private              Long teamId;

    @ApiModelProperty("领队人姓名")
    private String leaderName;

    @ApiModelProperty("领队人电话")
    private String leaderPhone;

    @ApiModelProperty("领队人身份证")
    private String leaderIdcard;

    @ApiModelProperty("团队性质 1-境内游客团 2-境外游客团")
    private Integer teamNature;

    @ApiModelProperty("团队类型 1-老年团 2-亲子团 3-购物团 4-纯玩团 5-学生团 6-研学团 7-疗休养团")
    private Integer teamType;

    @ApiModelProperty("行程开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @ApiModelProperty("行程结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty("旅行社id")
    private Long userId;

    @ApiModelProperty("申报时间")
    private Date applyTime;

    @ApiModelProperty("抵达单位名称")
    private String refMch;

    @ApiModelProperty("关联酒店景点id,分隔")
    private String refId;

    @ApiModelProperty("旅行社旅行社名字")
    private String mchName;

    @ApiModelProperty("团队状态 0-未提交 1-景区酒店审核中 2-景区酒店审核失败 3-待文旅局审核 4-文旅局审核成功 5-文旅局审核失败")
    private Integer teamStatus;

    @ApiModelProperty("团队人数 （总）")
    private Integer teamPerson;

    @ApiModelProperty("创建时间")
    private Date createTime;

    public static OfficialTravelTeamPageVO toVO(TravelTeam travelTeam) {
        OfficialTravelTeamPageVO vo = new OfficialTravelTeamPageVO();

        BeanUtils.copyProperties(travelTeam, vo);

        return vo;
    }
}
