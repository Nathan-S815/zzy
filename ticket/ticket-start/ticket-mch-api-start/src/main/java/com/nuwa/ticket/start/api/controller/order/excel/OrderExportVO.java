package com.nuwa.ticket.start.api.controller.order.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.nuwa.ticket.start.api.controller.order.excel.convert.OrderStatusConverter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单导出VO
 * </p>
 *
 * @author ROOT
 * @since 2020-11-05
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class OrderExportVO {

    @ColumnWidth(15)
    @ExcelProperty("订单Id")
    private Long id;

    @ColumnWidth(20)
    @ExcelProperty("UID")
    private String promoterCode;

    @ColumnWidth(20)
    @ExcelProperty("下单时间")
    private Date createTime;

    @ColumnWidth(13)
    @ExcelProperty(value = "游玩日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date visitDate;

    @ColumnWidth(16)
    @ExcelProperty("联系人手机号")
    private String linkMobile;

    @ColumnWidth(16)
    @ExcelProperty("联系人姓名")
    private String linkName;

    @ColumnWidth(22)
    @ExcelProperty("联系人证件号")
    private String linkIdCard;

    @ColumnWidth(45)
    @ExcelProperty("产品内容")
    private String productName;

    @ColumnWidth(12)
    @ExcelProperty("总票数")
    private Integer quantity;

    @ColumnWidth(12)
    @ExcelProperty("已退款")
    private Integer refundedQuantity;

    @ColumnWidth(12)
    @ExcelProperty("已核销")
    private Integer alreadyConsumeQuantity;

    @ColumnWidth(10)
    @ExcelProperty("总金额")
    private BigDecimal amount;

    @ColumnWidth(16)
    @ExcelProperty(value = "状态", converter = OrderStatusConverter.class)
    private Integer status;

    @ColumnWidth(26)
    @ExcelProperty("供应商名称")
    private String supplierName;

    @ColumnWidth(18)
    @ExcelProperty("供应商产品编码")
    private String supplierProductCode;

    @ColumnWidth(22)
    @ExcelProperty("订单号")
    private String orderNo;
}
