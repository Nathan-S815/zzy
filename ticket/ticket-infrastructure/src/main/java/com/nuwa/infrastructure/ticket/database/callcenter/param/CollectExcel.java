package com.nuwa.infrastructure.ticket.database.callcenter.param;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class CollectExcel {
    @ColumnWidth(15)
    @ExcelProperty("序号")
    private Long id;

    /**
     * 类别
     */
    @ColumnWidth(15)
    @ExcelProperty("类别")
    private String category;

    /**
     * 类型
     */
    @ColumnWidth(15)
    @ExcelProperty("类型")
    private String type;

    /**
     * 问题
     */
    @ColumnWidth(15)
    @ExcelProperty("问题")
    private String problem;

    /**
     * 问题结果
     */
    @ColumnWidth(15)
    @ExcelProperty("问题结果")
    private String result;
    @ColumnWidth(30)
    @ExcelProperty("提交时间")
    private Date createTime;
}
