package com.nuwa.infrastructure.ticket.database.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 文化活动
 *
 * @author huyonghack@163.com
 * @since 2021-08-16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CultureActivity extends Model<CultureActivity> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商户ID
     */
    private Long mchId;

    /**
     * AppID
     */
    private Long appId;

    /**
     * 活动名称
     */
    private String activityTitle;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 活动开始时间
     */
    private Date holdTimeStart;

    /**
     * 活动结束时间
     */
    private Date holdTimeEnd;

    /**
     * 图片地址,多个逗号隔开
     */
    @JsonSerialize(using = MaterialJson.class)
    private String imageList;

    /**
     * 是否支持报名(0不支持 1支持)
     */
    private Integer isSignable;

    /**
     * 活动票数
     */
    private Integer ticketsNum;

    /**
     * 已报名人数
     */
    private Integer applyNum;

    /**
     * 活动地址
     */
    private String address;

    /**
     * 举办方
     */
    private String organizer;

    /**
     * 活动详情
     */
    private String detailContentEditor;

    /**
     * 温馨提示
     */
    private String tipContentEditor;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志  [0正常,1删除]
     */
    private Integer deleteFlag;
    private Integer liked;

    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String APP_ID = "app_id";

    public static final String ACTIVITY_TITLE = "activity_title";

    public static final String CATEGORY_ID = "category_id";

    public static final String HOLD_TIME_START = "hold_time_start";

    public static final String HOLD_TIME_END = "hold_time_end";

    public static final String IMAGE_LIST = "image_list";

    public static final String TICKETS_NUM = "tickets_num";

    public static final String APPLY_NUM = "apply_num";

    public static final String ADDRESS = "address";

    public static final String ORGANIZER = "organizer";

    public static final String DETAIL_CONTENT_EDITOR = "detail_content_editor";

    public static final String TIP_CONTENT_EDITOR = "tip_content_editor";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
