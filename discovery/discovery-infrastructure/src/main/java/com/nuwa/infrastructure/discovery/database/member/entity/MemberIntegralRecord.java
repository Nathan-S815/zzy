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
 * 达人积分记录表
 *
 * @author huyonghack@163.com
 * @since 2022-08-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberIntegralRecord对象")
public class MemberIntegralRecord extends Model<MemberIntegralRecord> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id，主键，自动增长")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("积分数")
    private Integer integralCount;

    @ApiModelProperty("积分快照（事件发生后积分数）")
    private Integer integralSnapshot;

    @ApiModelProperty("事件id")
    private Integer eventId;

    @ApiModelProperty("事件内容")
    private String eventContent;

    @ApiModelProperty("事件类型 1：成长值")
    private Integer eventType;

    @ApiModelProperty("创建时间IM")
    private Date createTime;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String INTEGRAL_COUNT = "integral_count";

    public static final String INTEGRAL_SNAPSHOT = "integral_snapshot";

    public static final String EVENT_ID = "event_id";

    public static final String EVENT_CONTENT = "event_content";

    public static final String EVENT_TYPE = "event_type";

    public static final String CREATE_TIME = "create_time";

}
