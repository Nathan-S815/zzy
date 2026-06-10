package com.nuwa.infrastructure.zeus.database.base.entity;

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
 * 新手入门
 *
 * @author huyonghack@163.com
 * @since 2021-07-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "GettingStarted对象")
public class GettingStarted extends Model<GettingStarted> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("父ID")
    private Long pid;

    @ApiModelProperty("标签")
    private String label;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("类型  path(目录) content(内容)")
    private String type;

    @ApiModelProperty("正文内容")
    private String contentDetail;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("删除标志[0正常 1删除]")
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String PID = "pid";

    public static final String LABEL = "label";

    public static final String TITLE = "title";

    public static final String CONTENT_DETAIL = "content_detail";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String TYPE = "type";

}
