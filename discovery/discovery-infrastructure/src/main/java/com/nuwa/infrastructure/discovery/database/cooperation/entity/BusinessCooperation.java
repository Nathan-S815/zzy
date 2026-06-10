package com.nuwa.infrastructure.discovery.database.cooperation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商业合作
 *
 * @author huyonghack@163.com
 * @since 2022-09-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BusinessCooperation对象")
public class BusinessCooperation extends Model<BusinessCooperation> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id，主键，自动增长")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("所属行业 1：景区 2：酒店名宿 3：休闲娱乐 4：餐饮美食 5：其他")
    private Integer industry;

    @ApiModelProperty("营销目的 1：达人带货 2：宣传推广")
    private String objective;

    @ApiModelProperty("营销目的")
    private String objectiveContent;

    @ApiModelProperty("传播平台 1：抖音 2：腾讯视频号")
    private String communicationPlatform;

    @ApiModelProperty("传播平台")
    private String communicationPlatformContent;

    @ApiModelProperty("内容形式 1：带货短视频 2：图文 3：宣传片 4：视频素材")
    private Integer contentForm;

    @ApiModelProperty("发单模式 1：达人来投稿 ")
    private Integer pattern;

    @ApiModelProperty("其他需求")
    private String other;

    @ApiModelProperty("推广预算 1：无预算 2：1万以下 2：1-5万 3：5-10万 4：10-30万 5：30-50万 6：50万以上 ")
    private Integer budget;

    @ApiModelProperty("联系人姓名")
    private String contactsName;

    @ApiModelProperty("联系人电话")
    private String phone;

    @ApiModelProperty("关联任务id")
    private Long taskId;

    @ApiModelProperty("状态 1：待实施  2：实施中  3：已结束")
    private Integer status;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;

    @ApiModelProperty("更新时间IM")
    private Date updateTime;

    @ApiModelProperty("创建时间IM")
    private Date createTime;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String NAME = "name";

    public static final String INDUSTRY = "industry";

    public static final String OBJECTIVE = "objective";

    public static final String OBJECTIVE_CONTENT = "objective_content";

    public static final String COMMUNICATION_PLATFORM = "communication_platform";

    public static final String COMMUNICATION_PLATFORM_CONTENT = "communication_platform_content";

    public static final String CONTENT_FORM = "content_form";

    public static final String PATTERN = "pattern";

    public static final String OTHER = "other";

    public static final String BUDGET = "budget";

    public static final String CONTACTS_NAME = "contacts_name";

    public static final String PHONE = "phone";

    public static final String TASK_ID = "task_id";

    public static final String STATUS = "status";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

}
