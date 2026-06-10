package com.nuwa.infrastructure.discovery.database.user.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.discovery.util.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hy
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class UserTaskPrizePageVO {
    @ColumnWidth(15)
    @ExcelProperty("id")
    private Long id;

    @ColumnWidth(15)
    @ExcelProperty("权益类型id")
    private Long prizeTypeId;

    @ColumnWidth(15)
    @ExcelProperty("用户id")
    private Long userId;

    @ColumnWidth(15)
    @ExcelProperty("权益类型名称")
    private String prizeTypeName;

    @ColumnWidth(15)
    @ExcelProperty("权益名称")
    private String prizeTitle;

    @ColumnWidth(15)
    @ExcelProperty("权益内容")
    private String prizeContent;

    @ColumnWidth(15)
    @ExcelProperty("创建时间")
    private Date createTime;

    @ColumnWidth(15)
    @ExcelProperty("提交时间")
    private Date submitTime;

    @ColumnWidth(15)
    @ExcelProperty("处理时间")
    private Date lastUpdateTime;

    @ColumnWidth(15)
    @ExcelProperty("手机号")
    private String userPhone;

    @ColumnWidth(15)
    @ExcelProperty("昵称")
    private String nick;

    @ColumnWidth(15)
    @ExcelProperty("账号")
    private String accountId;

    @ColumnWidth(15)
    @ExcelProperty("任务名称")
    private String taskName;

    @ColumnWidth(15)
    @ExcelProperty("状态")
    @ApiModelProperty("状态 1:待认领 2:已认领，待发放 3:已发放 4:审核失败")
    private Integer status;

    @ColumnWidth(15)
    @ExcelProperty("扩展参数")
    private String extData;

    @ColumnWidth(15)
    @ExcelProperty("图片")
    @JsonSerialize(using = MaterialJson.class)
    private String pictures;

    @ColumnWidth(15)
    @ExcelProperty("任务接取时达人标签")
    private String memberTag;

    @ColumnWidth(15)
    @ExcelProperty("任务接取时达人性别")
    private Integer sex;

    @ColumnWidth(15)
    @ExcelProperty("任务接取时达人地区")
    private String region;


    @ColumnWidth(15)
    @ExcelProperty("任务接取时达人等级")
    private Integer userLevelId;

    @ColumnWidth(15)
    @ExcelProperty("任务接取时达人等级")
    private String levelName;

    @ColumnWidth(15)
    @ExcelProperty("任务id")
    private Long taskId;
}
