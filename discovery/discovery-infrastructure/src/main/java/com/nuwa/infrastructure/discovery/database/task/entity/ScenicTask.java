package com.nuwa.infrastructure.discovery.database.task.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 景区任务表
 *
 * @author huyonghack@163.com
 * @since 2021-11-29
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ScenicTask对象")
public class ScenicTask extends Model<ScenicTask> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("镜头要求")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("展示主图")
    private String picture;

    @ApiModelProperty("创建时间")
    private Date createTime;

    private String createByName;

    private String createById;

    private String lastUpdateByName;

    private String lastUpdateById;

    private Date lastUpdateTime;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;

    @ApiModelProperty("发单方式 1:达人投稿")
    private Integer billMode;

    @ApiModelProperty("营销目的 1:电商卖货")
    private Integer target;

    @ApiModelProperty("任务平台编码")
    private String platformCode;

    @ApiModelProperty("传播方式 1:短视频")
    private Integer broadcastMode;

    @ApiModelProperty("介绍")
    private String introduceText;

    @ApiModelProperty("联系人")
    private String linkman;

    @ApiModelProperty("联系人电话")
    private String linkmanTelephone;

    @ApiModelProperty("任务图片")
    private String pictures;

    @ApiModelProperty("最小佣金比例")
    private Integer chargeMin;

    @ApiModelProperty("最大佣金比例")
    private Integer chargeMax;

    @ApiModelProperty("最小粉丝数")
    private Long limitFansMin;

    @ApiModelProperty("最大报名人数")
    private Long limitApplyMax;

    @ApiModelProperty("报名人数")
    private Long applyTotal;

    @ApiModelProperty("开始时间")
    private Date beginDate;

    @ApiModelProperty("结束时间")
    private Date endDate;

    @ApiModelProperty("有效期模式 1:时间段 2：长久")
    private Integer validityMode;

    @ApiModelProperty("审核状态 1：待审核 2：审核成功 3：审核失败")
    private Integer auditStatus;

    @ApiModelProperty("任务状态 1:未开始 2:进行中 3:已结束 4:暂停")
    private Integer status;

    @ApiModelProperty("首页推荐 0：普通 1：推荐")
    private Integer indexRecommend;

    @ApiModelProperty("任务权益标签 [权益A][权益B]")
    private String prizeTypeTag;

    @ApiModelProperty("镜头要求")
    private String cameraRemark;

    @ApiModelProperty("口播要求")
    private String wordRemark;

    @ApiModelProperty("标题要求")
    private String titleRemark;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty("其他要求")
    private String otherRemarkList;

    @ApiModelProperty("特别提醒")
    private String specialTipList;

    @ApiModelProperty("官方话题 0：不添加 1:添加 ")
    private Integer topic;

    @ApiModelProperty("官方话题内容")
    private String topicContent;

    @ApiModelProperty("限制性别 0:不限 1:男 2:女")
    private Integer limitSex;

    @ApiModelProperty("等级限制")
    private Integer limitLevel;

    @ApiModelProperty("限制区域 0:不限 1:限制")
    private Integer limitArea;

    @ApiModelProperty("限制区域内容")
    private String limitAreaContent;

    @ApiModelProperty("经度")
    private String lon;

    @ApiModelProperty("纬度")
    private String lat;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("来源 1:平台 2:商户")
    private Integer source;

    @ApiModelProperty("报名比例（报名人数/最大报名人数）")
    private Double applyProportion;

    @ApiModelProperty("商户id")
    private String mchId;

    @ApiModelProperty("任务类型")
    private String industryCode;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String PICTURE = "picture";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String BILL_MODE = "bill_mode";

    public static final String TARGET = "target";

    public static final String PLATFORM_CODE = "platform_code";

    public static final String BROADCAST_MODE = "broadcast_mode";

    public static final String INTRODUCE_TEXT = "introduce_text";

    public static final String LINKMAN = "linkman";

    public static final String LINKMAN_TELEPHONE = "linkman_telephone";

    public static final String PICTURES = "pictures";

    public static final String CHARGE_MIN = "charge_min";

    public static final String CHARGE_MAX = "charge_max";

    public static final String LIMIT_FANS_MIN = "limit_fans_min";

    public static final String LIMIT_APPLY_MAX = "limit_apply_max";

    public static final String APPLY_TOTAL = "apply_total";

    public static final String BEGIN_DATE = "begin_date";

    public static final String END_DATE = "end_date";

    public static final String VALIDITY_MODE = "validity_mode";

    public static final String AUDIT_STATUS = "audit_status";

    public static final String STATUS = "status";

    public static final String INDEX_RECOMMEND = "index_recommend";

    public static final String PRIZE_TYPE_TAG = "prize_type_tag";

    public static final String CAMERA_REMARK = "camera_remark";

    public static final String WORD_REMARK = "word_remark";

    public static final String TITLE_REMARK = "title_remark";

    public static final String WEIGHT = "weight";

    public static final String OTHER_REMARK_LIST = "other_remark_list";

    public static final String SPECIAL_TIP_LIST = "special_tip_list";

    public static final String TOPIC = "topic";

    public static final String TOPIC_CONTENT = "topic_content";

    public static final String LIMIT_SEX = "limit_sex";

    public static final String LIMIT_AREA = "limit_area";

    public static final String LIMIT_AREA_CONTENT = "limit_area_content";

    public static final String LON = "lon";

    public static final String LAT = "lat";

    public static final String ADDRESS = "address";

    public static final String SOURCE = "source";

    public static final String APPLY_PROPORTION = "apply_proportion";

    public static final String LIMIT_LEVEL = "limit_level";

    public static final String MCH_ID = "mch_id";

    public static final String INDUSTRY_CODE = "industry_code";

}
