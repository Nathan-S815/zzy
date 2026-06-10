package com.nuwa.client.ticket.dto.clientobject.user.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 用户分页查询PageQry
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户分页查询PageQry")
public class UserPageQry extends NuwaPageQry {

    @ApiModelProperty(value = "商户id", hidden = true)
    private Long mchId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String userNike;

    @ApiModelProperty("用户虚拟头像")
    private String userImg;

    @ApiModelProperty("用户注册ip")
    private String ip;

    @ApiModelProperty("uid")
    private String uid;

    @ApiModelProperty("创建时间-开始")
    private Date createTimeStart;

    @ApiModelProperty("创建时间-结束")
    private Date createTimeEnd;

}
