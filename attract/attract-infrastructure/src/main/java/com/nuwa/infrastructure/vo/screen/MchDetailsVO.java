package com.nuwa.infrastructure.vo.screen;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: WangXh
 * @DateTime: 2022/12/5
 * @Description: 首页列表商家详情数据
 */
@Data
public class MchDetailsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("商户名称（酒店、景区、旅行社）")
    private String mchName;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("联系人姓名")
    private String linkName;

    @ApiModelProperty("联系人电话")
    private String linkPhone;

    @ApiModelProperty("注册时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;
}
