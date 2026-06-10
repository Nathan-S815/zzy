package com.nuwa.ticket.start.api.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * 添加产品到我的分销商
 *
 * @author hy
 */
@Data
public class AppendProductToDistributeDTO {
    private List<AppendProduct> productList;

    @Data
    public static class AppendProduct {
        private Long productId;
        private Long supplierMerchantId;
        private String supplierMerchantName;
        private Long distributeMerchantId;
        private String distributeMerchantName;
    }
}
