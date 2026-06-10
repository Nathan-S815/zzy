package com.nuwa.app.ticket.command.mall.query;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.MallTradeQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.express.entity.Express;
import com.nuwa.infrastructure.ticket.database.express.service.ExpressService;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProduct;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProductSkuStock;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductSkuStockService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import com.nuwa.infrastructure.ticket.json.serializer.DictJson;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import com.nuwa.infrastructure.ticket.util.PriceJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class MallTradeQryExe extends AbstractQryExe<MallTradeQry, SingleResponse> {

    @Autowired
    private MallTradeService tradeService;

    @Autowired
    private MallProductService productService;

    @Autowired
    private MallProductSkuStockService productSkuStockService;

    @Autowired
    private ExpressService expressService;

    @Override
    protected SingleResponse handle(MallTradeQry cmd) {
        if(Objects.isNull(cmd.getId())){
            return ErrorEnum.MISSING_REQUIRED_PARAMS.buildFailure();
        }
        TradeVo vo = new TradeVo();
        MallTrade trade = tradeService.getById(cmd.getId());
        MallProduct product = productService.getById(trade.getProductId());
        BeanUtil.copyProperties(product,vo);
        MallProductSkuStock productSkuStock = productSkuStockService.getById(trade.getSpecificationsId());
        BeanUtil.copyProperties(productSkuStock,vo);
        if (BeanUtil.isNotEmpty(trade.getExpressId())){
            Express express = expressService.getById(trade.getExpressId());
            BeanUtil.copyProperties(express,vo);
        }
        BeanUtil.copyProperties(trade,vo);
        vo.setTotalAmount(vo.getSellPrice()*vo.getProductNum());
        vo.setProductId(product.getId());
        return SingleResponse.of(vo);
    }

    @Data
    public static class TradeVo {

        @ApiModelProperty(value = "主键")
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        @ApiModelProperty(value = "订单号")
        private String tradeNo;

        @ApiModelProperty(value = "订单类型(10直销)")
        private Integer orderType;

        @ApiModelProperty(value = "支付方式(10在线支付)")
        private Integer payType;

        @ApiModelProperty(value = "下单渠道(100微信,101支付宝)")
        @JsonIgnore
        private Integer payChannel;

        @ApiModelProperty(value = "下单时间")
        private Date createTime;

        @ApiModelProperty(value = "支付时间")
        private Date paySuccessTime;

        @ApiModelProperty(value = "包邮或到付")
        private Integer expressType;

        @ApiModelProperty(value = "交易流水号")
        private String orderNo;

        @ApiModelProperty(value = "产品编号")
        private Long productId;

        @ApiModelProperty(value = "产品名称")
        private String productName;

        @ApiModelProperty(value = "规格名称")
        private String skuStockName;

        @ApiModelProperty(value = "规格图")
        @JsonSerialize(using = MaterialJson.class)
        private String skuStockImg;

        @ApiModelProperty(value = "单价(分)")
        @JsonSerialize(using = PriceJson.class)
        private Long sellPrice;

        @ApiModelProperty(value = "购买数量")
        private Integer productNum;

        @ApiModelProperty(value = "优惠金额(分)")
        @JsonSerialize(using = PriceJson.class)
        private Long discount = 0l;

        @ApiModelProperty(value = "订单总价(分)")
        @JsonSerialize(using = PriceJson.class)
        private Long totalAmount;

        @ApiModelProperty(value = "收获方式 (10线上发货 11线下门店取货)")
        private Integer receivingMethod;

        @ApiModelProperty(value = "门店地址")
        private String storeAddress;

        @ApiModelProperty(value = "门店经度")
        private String longitude;

        @ApiModelProperty(value = "门店纬度")
        private String latitude;

        @ApiModelProperty(value = "收货人姓名")
        private String consigneeName;

        @ApiModelProperty(value = "收货人手机号")
        private String consigneeTel;

        @ApiModelProperty(value = "收货人地址  ")
        private String consigneeAddr;

        @ApiModelProperty(value = "物流公司")
        @JsonSerialize(using = DictJson.class)
        private Integer expressCompany;

        @ApiModelProperty(value = "物流编号")
        private String expressNo;

        @ApiModelProperty(value = "物流备注")
        private String remarks;

        @ApiModelProperty(value = "支付有效期")
        private Date expireTime;

        @ApiModelProperty(value = "退款理由")
        private String refundReason;

        @ApiModelProperty(value = "退款时间")
        private Date refundTime;

        @ApiModelProperty(value = "订单备注")
        private String remark;

        @ApiModelProperty(value = "退款金额(单位分)")
        @JsonSerialize(using = PriceJson.class)
        private Long refundAmount;

        @ApiModelProperty(value = "退款流水号")
        private String refundOrderNo;

        @ApiModelProperty(value = "订单状态(10待支付 11代发货 12待收货 13已完成 14退款审核 15退款中 16已取消 17已退款 18退款失败)")
        private Integer orderStatus;

        @ApiModelProperty(value = "客服电话")
        private String servicePhone;

    }
}
