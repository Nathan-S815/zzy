package com.nuwa.infrastructure.discovery.database.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hy
 */
@Data
public class UserTaskPrizeRecordPageVO {
    private Long id;
    private Long prizeTypeId;
    private String prizeTitle;
    private Date createTime;
    private Date submitTime;
    private Date auditTime;
    private String userPhone;
    private String nick;
    private String taskName;
    private String weixinId;

    @ApiModelProperty("状态 1:待认领 2:已认领，待发放 3:已发放 4:审核失败")
    private Integer status;

    private String extData;
}
