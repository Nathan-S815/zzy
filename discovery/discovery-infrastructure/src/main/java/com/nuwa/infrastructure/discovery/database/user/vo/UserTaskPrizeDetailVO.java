package com.nuwa.infrastructure.discovery.database.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.discovery.util.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hy
 */
@Data
public class UserTaskPrizeDetailVO {
    private Long id;
    private Long prizeTypeId;
    private String prizeTypeName;
    private String prizeTitle;
    private Date createTime;
    private Date submitTime;
    private String userPhone;
    private String nick;
    private String taskName;
    private String remarkText;

    private String prizeContent;

    private String introduceText;

    @JsonSerialize(using = MaterialJson.class)
    private String remarkPictures;

    @JsonSerialize(using = MaterialJson.class)
    private String pictures;

    @ApiModelProperty("扩展字段")
    private String extData;

    @ApiModelProperty("状态 1:待认领 2:已认领，待发放 3:已发放 4:审核失败")
    private Integer status;
}
