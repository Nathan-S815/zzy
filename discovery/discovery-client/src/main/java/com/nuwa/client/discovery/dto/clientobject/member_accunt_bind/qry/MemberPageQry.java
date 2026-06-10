package com.nuwa.client.discovery.dto.clientobject.member_accunt_bind.qry;

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


    @ApiModelProperty(value = "创建时间IM开始")
    private Date createTimeStart;

    @ApiModelProperty(value = "创建时间IM结束")
    private Date createTimeEnd;

}
