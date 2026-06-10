package com.nuwa.infrastructure.attract.database.teamuserref.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 *
 * @author nanhuang @南皇
 * @since 2022-11-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "团队商家关联对象")
public class TeamUserRef extends Model<TeamUserRef> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "team_user_ref_id", type = IdType.AUTO)
    private Long teamUserRefId;

    @ApiModelProperty("团队ID")
    private Long teamId;

    @ApiModelProperty("关联商户ID")
    private Long userId;

    @ApiModelProperty("商户名称")
    private String mchName;

    @ApiModelProperty("实到人数")
    private Integer attendance;

    @ApiModelProperty("酒店房间数")
    private Integer hotelRooms;

    @ApiModelProperty("行程日期")
    private Date travelDate;

    @ApiModelProperty("状态 0-未提交,1-景区酒店审核中 2-景区酒店审核失败 3-景区酒店审核成功 4-文旅局审核中 5-文旅局审核失败  6-文旅局审核成功  9-已删除")
    private Integer status;

    @ApiModelProperty("商家审核时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date mchReviewTime;

    @ApiModelProperty("商家审核信息")
    private String mchReviewReason;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty("1-酒店 2-景区 3-自由行")
    private Integer type;

    @ApiModelProperty("是否删除 0-正常 1-已删除")
    private Integer deleteFlag;

    public static final String ID = "id";

    public static final String TEAM_ID = "team_id";

    public static final String USER_ID = "user_id";

    public static final String MCH_NAME = "mch_name";

    public static final String TEAM_PERSON = "team_person";

    public static final String ATTENDANCE = "attendance";

    public static final String TRAVEL_TIME = "travel_time";

    public static final String STATUS = "status";

    public static final String MCH_REVIEW_TIME = "mch_review_time";

    public static final String MCH_REVIEW_REASON = "mch_review_reason";

    public static final String CREATE_TIME = "create_time";

}
