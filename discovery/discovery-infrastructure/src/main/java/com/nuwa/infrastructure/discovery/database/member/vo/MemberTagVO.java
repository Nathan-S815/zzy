package com.nuwa.infrastructure.discovery.database.member.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MemberTagVO {


    @ApiModelProperty("id，主键，自动增长")
    private Long id;

    @ApiModelProperty("标签名称")
    private String name;

    @ApiModelProperty("标签排序")
    @TableField("`order`")
    private Integer order;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("绑定人数")
    private Integer bindCount;

    @ApiModelProperty("创建时间IM")
    private Date createTime;
}
