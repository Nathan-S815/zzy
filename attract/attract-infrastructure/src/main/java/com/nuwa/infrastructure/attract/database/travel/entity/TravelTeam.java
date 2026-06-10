package com.nuwa.infrastructure.attract.database.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 旅行团
 *
 * @author nanhuang @南皇
 * @since 2022-09-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TravelTeam对象")
public class TravelTeam extends Model<TravelTeam> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("团队id")
    @TableId(value = "team_id", type = IdType.AUTO)
    private Long teamId;

    @ApiModelProperty("团队名")
    private String teamName;

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
    private Date beginDate;

    @ApiModelProperty("行程结束时间")
    private Date endDate;

    @ApiModelProperty("旅行社id")
    private Long userId;

    @ApiModelProperty("团队状态 0-未提交 1-景区酒店审核中 2-景区酒店审核失败 3-待文旅局审核 4-文旅局审核成功 5-文旅局审核失败")
    private Integer teamStatus;

    @ApiModelProperty("团队人数 （总）")
    private Integer teamPerson;

    @ApiModelProperty("奖励人数 （县外）")
    private Integer rewardPerson;

    @ApiModelProperty("商家审核信息")
    private String mchReviewReason;

    @ApiModelProperty("官方审核信息")
    private String officialReviewReason;

    @ApiModelProperty("关联酒店景点名称,分隔")
    private String refMch;

    @ApiModelProperty("关联酒店景点id,分隔")
    private String refId;


    @ApiModelProperty("是否已统计奖励 0-否 1-是")
    private Integer reward;

    @ApiModelProperty("申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date applyTime;

    @ApiModelProperty("酒店景区商家审核时间")
    private Date mchReviewTime;

    @ApiModelProperty("文旅局审核时间")
    private Date officialReviewTime;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private String createByName;

    private Long createById;

    private String lastUpdateByName;

    private Long lastUpdateById;

    private Date lastUpdateTime;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;

    public static final String TEAM_ID = "team_id";

    public static final String TEAM_NAME = "team_name";

    public static final String LEADER_NAME = "leader_name";

    public static final String LEADER_PHONE = "leader_phone";

    public static final String LEADER_IDCARD = "leader_idcard";

    public static final String TEAM_NATURE = "team_nature";

    public static final String TEAM_TYPE = "team_type";

    public static final String BEGIN_DATE = "begin_date";

    public static final String END_DATE = "end_date";

    public static final String USER_ID = "user_id";

    public static final String TEAM_STATUS = "team_status";

    public static final String TEAM_PERSON = "team_person";

    public static final String REWARD_PERSON = "reward_person";

    public static final String MCH_REVIEW_REASON = "mch_review_reason";

    public static final String OFFICIAL_REVIEW_REASON = "official_review_reason";

    public static final String APPLY_TIME = "apply_time";

    public static final String REF_MCH = "ref_mch";

    public static final String REF_ID = "ref_id";

    public static final String REWARD = "reward";

    public static final String MCH_REVIEW_TIME = "mch_review_time";

    public static final String OFFICIAL_REVIEW_TIME = "official_review_time";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
