package com.nuwa.discovery.start.api.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * @author hy
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class UserTaskApplyExcelVO {
    @ColumnWidth(15)
    @ExcelProperty("用户id")
    private Long userId;

    @ColumnWidth(15)
    @ExcelProperty("抖音号")
    private String accountId;

    @ColumnWidth(15)
    @ExcelProperty("抖音昵称")
    private String nick;

    @ColumnWidth(15)
    @ExcelProperty("粉丝数")
    private String fansCount;

    @ColumnWidth(15)
    @ExcelProperty("性别")
    private String sex;

    @ColumnWidth(15)
    @ExcelProperty("地域")
    private String region;

    @ColumnWidth(15)
    @ExcelProperty("等级")
    private String level;

    @ColumnWidth(15)
    @ExcelProperty("申请时间")
    private Date applyTime;
}
