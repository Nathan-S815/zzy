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
 * 一码通会员 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-10-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通会员PageQry")
public class OneMemberPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID，主键，自动增长")
    private Integer userId;

    @ApiModelProperty(value = "用户昵称")
    private String userNike;


    @ApiModelProperty(value = "注册时间开始")
    private Date createTimeStart;

    @ApiModelProperty(value = "注册时间结束")
    private Date createTimeEnd;

}
