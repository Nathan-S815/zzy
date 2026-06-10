package com.nuwa.client.discovery.dto.clientobject.task.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 用户景区任务表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-11-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户景区任务表PageQry")
public class UserScenicTaskPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务标题")
    private String title;

    @ApiModelProperty(value = "任务状态 1:未开始 2:进行中 3:已结束 4:暂停", hidden = true)
    private Integer status;

    @ApiModelProperty(value = "奖励类型 10:团购分佣 11:探店免票 12:豆荚助力 13:现金奖励")
    private Integer prizeType;

    @ApiModelProperty(value = "排序 1:最新发布 2:最高分佣 3:最高热度")
    private Integer orderBy;

    @ApiModelProperty(value = "首页推荐 0：普通 1：推荐", hidden = true)
    private Integer indexRecommend;

    @ApiModelProperty("经度")
    private String lon;

    @ApiModelProperty("纬度")
    private String lat;

    @ApiModelProperty("等级限制")
    private Integer limitLevel;

    @ApiModelProperty("商户id")
    private String mchId;

}
