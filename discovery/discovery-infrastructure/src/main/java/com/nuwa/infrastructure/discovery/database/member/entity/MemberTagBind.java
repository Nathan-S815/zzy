package com.nuwa.infrastructure.discovery.database.member.entity;

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
 * 达人标签绑定表
 *
 * @author huyonghack@163.com
 * @since 2022-08-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberTagBind对象")
public class MemberTagBind extends Model<MemberTagBind> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id，主键，自动增长")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("达人id")
    private Long memberId;

    @ApiModelProperty("达人标签id")
    private Long memberTagId;

    @ApiModelProperty("更新时间IM")
    private Date updateTime;

    @ApiModelProperty("创建时间IM")
    private Date createTime;


    public static final String ID = "id";

    public static final String MEMBER_ID = "member_id";

    public static final String MEMBER_TAG_ID = "member_tag_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

}
