package com.nuwa.ticket.start.api.controller.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 游客信息
 *
 * @author huyonghack@163.com
 * @since 2021-12-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TouristDTO")
public class TouristDTO {

    @ApiModelProperty("联系人姓名")
    private String name;

    @ApiModelProperty("联系人手机号")
    private String mobile;

    @ApiModelProperty("身份信息")
    private String idCard;

    @ApiModelProperty("证件类型[IdCard|Passport|TaiWanese|HongKongMacaoPass|Dltwtxz]")
    private String certificateType;

}
