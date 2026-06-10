package com.nuwa.attract.start.api.controller.travel.vo;

import com.nuwa.infrastructure.attract.database.invoice.entity.Invoice;
import com.nuwa.infrastructure.vo.InvoiceVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: WangXh
 * @DateTime: 2022/11/14
 * @Description: TODO
 */
@Data
public class AddInvoiceParam {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("新增团队列表")
    List<Invoice> invoiceList;
    @ApiModelProperty("删除团队信息")
    List<Long> deleteInvoiceIds;
}
