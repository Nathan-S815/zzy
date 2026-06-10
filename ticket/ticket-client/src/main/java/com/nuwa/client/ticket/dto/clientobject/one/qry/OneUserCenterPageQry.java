package com.nuwa.client.ticket.dto.clientobject.one.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 一码通用户中心 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-08-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通用户中心PageQry")
public class OneUserCenterPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID，主键，自动增长")
    private Integer userId;

    @ApiModelProperty(value = "用户昵称")
    private String userNike;

    @ApiModelProperty(value = "用户姓名")
    private String userRealName;

    @ApiModelProperty(value = "创建时间开始")
    private Date createTimeStart;

    @ApiModelProperty(value = "创建时间结束")
    private Date createTimeEnd;

    @ApiModelProperty("用户手机")
    private String userPhone;

    @ApiModelProperty("用户身份证ID")
    private String userIdCard;

}
