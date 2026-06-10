package com.nuwa.discovery.start.api.controller.task.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "账号修改参数")
public class ModifyAccountParam {
    private Long id;

    @ApiModelProperty("达人平台用户id")
    private String accountId;

    @ApiModelProperty("昵称")
    private String nick;

    @ApiModelProperty("粉丝数")
    private Integer fansCount;

    @ApiModelProperty("用户性别")
    private Integer sex;

    @ApiModelProperty("用户所在地区，城市-区县")
    private String region;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("uId")
    private String uId;

}
