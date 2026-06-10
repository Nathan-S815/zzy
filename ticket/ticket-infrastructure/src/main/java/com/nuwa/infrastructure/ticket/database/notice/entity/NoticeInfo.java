package com.nuwa.infrastructure.ticket.database.notice.entity;

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
 * 公告表
 *
 * @author huyonghack@163.com
 * @since 2022-03-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "NoticeInfo对象")
public class NoticeInfo extends Model<NoticeInfo> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商户Id")
    private Long mchId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("浏览次数")
    private Long views;

    @ApiModelProperty("上下架 0:下架 1:上架")
    private Integer status;

    @ApiModelProperty("置顶 0:普通 1:置顶")
    private Integer recommendStatus;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String TITLE = "title";

    public static final String CONTENT = "content";

    public static final String VIEWS = "views";

    public static final String STATUS = "status";

    public static final String RECOMMEND_STATUS = "recommend_status";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
