package com.nuwa.client.discovery.dto.clientobject.user.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 达人用户表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-11-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "达人用户表PageQry")
public class MemberPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID，主键，自动增长")
    private Integer userId;

    @ApiModelProperty(value = "用户昵称")
    private String userNike;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "创建时间IM开始")
    private Date createTimeStart;

    @ApiModelProperty(value = "创建时间IM结束")
    private Date createTimeEnd;

    @ApiModelProperty(value = "第三方平台昵称")
    private String thirdPartyNick;

    @ApiModelProperty(value = "第三方平台账号")
    private String thirdPartyAccount;

    @ApiModelProperty(value = "第三方平台类型")
    private String thirdPartyType;

    @ApiModelProperty(value = "达人等级")
    private Integer level;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty("mchId")
    private String mchId;
}
