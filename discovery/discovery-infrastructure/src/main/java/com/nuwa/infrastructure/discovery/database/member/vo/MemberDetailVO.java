package com.nuwa.infrastructure.discovery.database.member.vo;



import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: WangXh
 * @DateTime: 2022/12/6
 * @Description: 达人详细数据
 */
@Data
public class MemberDetailVO {

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户昵称")
    private String userNick;

    @ApiModelProperty("用户头像")
    private String userImg;

    @ApiModelProperty("等级")
    private String level;

    @ApiModelProperty("粉丝数")
    private Integer fansCount;

    @ApiModelProperty("用户所在地区，城市-区县")
    private String region;
}
