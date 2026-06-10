package com.nuwa.infrastructure.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * C端酒店列表VO
 *
 * @author nanHuang @南皇
 * @version com.nuwa.hotel.start.api.controller.hotel.vo:HotelUserPageVO.java,v1.0.0 2022-08-09 16:06:57 nanHuang Exp $
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamCustomerPageQueryVO {
    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("身份证号")
    private String idcard;

    @ApiModelProperty("是否参与")
    private Boolean party;
}
