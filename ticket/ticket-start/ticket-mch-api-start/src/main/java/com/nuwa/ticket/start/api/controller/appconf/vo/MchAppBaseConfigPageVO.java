package com.nuwa.ticket.start.api.controller.appconf.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * @author hy
 */
@Data
public class MchAppBaseConfigPageVO {

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("小程序AppId")
    private String outAppId;

    @ApiModelProperty("小程序模板id")
    private String appTemplateId;

    @ApiModelProperty("小程序三方应用id")
    private String thirdAppId;

    @ApiModelProperty("应用类型 douyin")
    private String appType;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("所属省份id")
    private Long provinceId;

    @ApiModelProperty("所属省份名称")
    private String provinceName;

    @ApiModelProperty("所属地市id")
    private Long cityId;

    @ApiModelProperty("所属地市名称")
    private String cityName;

    @ApiModelProperty("抖音小程序的 APP Secret")
    private String secret;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("类型 SINGLE_SCENIC:单景点 PLATE:全域")
    private String type;

    @ApiModelProperty("poi id")
    private Long poiId;

    @ApiModelProperty("poiList 多个用逗号(,)隔开")
    private String poiList;

    @ApiModelProperty("默认小程序 1:默认 0:普通")
    private Integer defaultFlag;

    @ApiModelProperty("渠道公钥")
    @NotBlank(message = "渠道公钥不能为空")
    private String channelPublicKey;

    @ApiModelProperty("商户私钥")
    @NotBlank(message = "商户私钥不能为空")
    private String mchPrivateKey;

    @ApiModelProperty("商户授权码")
    @NotBlank(message = "商户授权码不能为空")
    private String appAuthCode;

    public static MchAppBaseConfigPageVO toVO(MerchantAppBaseConf config) {
        MchAppBaseConfigPageVO vo = new MchAppBaseConfigPageVO();
        BeanUtils.copyProperties(config, vo);
        return vo;
    }
}
