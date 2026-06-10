package com.nuwa.app.ticket.command.mall.query;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.UserMallTradePageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProduct;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProductSkuStock;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.param.UserMallTradePageParam;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductSkuStockService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import com.nuwa.infrastructure.ticket.util.PriceJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class UserMallTradePageQryExe extends AbstractQryExe<UserMallTradePageQry, SingleResponse> {

    @Autowired
    private MallTradeService mallTradeService;
    @Autowired
    private MallProductSkuStockService productSkuStockService;
    @Autowired
    private MallProductService productService;

    @Override
    protected SingleResponse handle(UserMallTradePageQry cmd) {
        IPage<TradeExtVo> tradeExtVoIPage = mallTradeService.paginateAndConvert(new UserMallTradePageParam(cmd), TradeExtVo::toVo);
        tradeExtVoIPage.getRecords().forEach(x -> {
            MallProductSkuStock sku = productSkuStockService.getById(x.getSpecificationsId());
            x.setSkuStockName(sku.getSkuStockName());
            MallProduct product = productService.getById(x.getProductId());
            x.setCoverImg(product.getCoverImg());
        });
        return SingleResponse.of(tradeExtVoIPage);
    }

    @Data
    public static class TradeExtVo {
        @ApiModelProperty(value = "主键")
        private Long id;

        @ApiModelProperty(value = "订单号")
        private String tradeNo;

        @ApiModelProperty(value = "商品Id")
        private String productId;

        @ApiModelProperty(value = "规格Id")
        private String specificationsId;

        @ApiModelProperty(value = "商品名称")
        private String productName;

        @ApiModelProperty(value = "规格名称")
        private String skuStockName;

        @ApiModelProperty(value = "购买数量")
        private String productNum;

        @ApiModelProperty(value = "总金额")
        @JsonSerialize(using = PriceJson.class)
        private Long payAmount;

        @ApiModelProperty(value = "下单时间")
        private Date createTime;

        @ApiModelProperty(value = "产品图")
        @JsonSerialize(using = MaterialJson.class)
        private String coverImg;

        @ApiModelProperty(value = "订单状态 10待支付 11代发货 12待收货 13已完成 14退款审核 15退款中 16已取消 17已退款 18退款失败")
        private Integer orderStatus;

        public static TradeExtVo toVo(MallTrade trade) {
            TradeExtVo vo = new TradeExtVo();
            BeanUtil.copyProperties(trade, vo);
            return vo;
        }
    }
}
