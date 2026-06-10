package com.nuwa.ticket.start.api.controller.supplier.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author hy
 */
@Data
public class MchSupplierConfPageVO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("供应商类别id")
    private Long supplierId;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty("供应商商户id")
    private String channelMerchantId;

    @ApiModelProperty("供应商秘钥")
    private String channelSecretKey;

    @ApiModelProperty("1:正常,0：停用")
    private Integer status;

    @ApiModelProperty("供应商渠道过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("接口地址")
    private String apiUrl;

    public static MchSupplierConfPageVO toVO(MerchantSupplierConfig config) {
        MchSupplierConfPageVO vo = new MchSupplierConfPageVO();
        BeanUtils.copyProperties(config, vo);
        return vo;
    }
}
