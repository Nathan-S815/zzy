package com.nuwa.client.zeus.dto.clientobject.mch;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * CreateMerchantCmd 创建商户
 *
 * @author hy
 * @date 2021/6/2 16:17
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "创建商户配置-命令")
public class CreateMerchantSiteCmd extends NuwaCommand {

    @ApiModelProperty(value = "配置类型 1:登录页 2:管理首页", required = true)
    @NotNull(message = "配置类型不能为空")
    private Integer type;

//    @ApiModelProperty(value = "联系人姓名", required = true)
//    @NotBlank(message = "联系人姓名不能为空")
//    private String contactName;
//
//    @ApiModelProperty(value = "联系方式", required = true)
//    @NotBlank(message = "联系方式不能为空")
//    private String contactType;
//
//    @ApiModelProperty(value = "qq", required = true)
//    @NotBlank(message = "qq不能为空")
//    private String qq;

    @ApiModelProperty(value = "logo", required = true)
    @NotNull(message = "logo不能为空")
    private Long logo;

    @ApiModelProperty(value = "背景图片", required = true)
    @NotNull(message = "背景图片不能为空")
    private List<Long> bgImg;

    @ApiModelProperty(value = "域名", required = true)
    @NotBlank(message = "域名不能为空")
    private String domain;

    @ApiModelProperty(value = "备案号", required = true)
    @NotBlank(message = "备案号不能为空")
    private String websiteApproveNo;

//    @ApiModelProperty(value = "商户ID", required = true)
//    @NotNull(message = "商户ID不能为空")
//    private Long mchId;

}
