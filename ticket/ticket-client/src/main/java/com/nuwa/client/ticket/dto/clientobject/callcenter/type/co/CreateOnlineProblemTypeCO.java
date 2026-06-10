package com.nuwa.client.ticket.dto.clientobject.callcenter.type.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新增问题类别，类型")
public class CreateOnlineProblemTypeCO  extends NuwaCO {
    @ApiModelProperty(value = "类别")
    private Long id;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id （类别的父id默认为0，类型的父id为类别的id）")
    private Long parentId;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别")
    private String category;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String type;
}
