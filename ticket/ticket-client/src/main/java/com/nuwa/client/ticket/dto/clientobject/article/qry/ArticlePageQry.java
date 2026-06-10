package com.nuwa.client.ticket.dto.clientobject.article.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 资讯分页查询
 *
 * @author hy
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ArticlePageQry")
public class ArticlePageQry extends NuwaPageQry {

    @ApiModelProperty("一级分类Id")
    private Long categoryOne;

    @ApiModelProperty("二级分类Id")
    private Long categorySecond;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("mchId")
    private Long mchId;

    @ApiModelProperty("上下架 0:下架 1:上架")
    private Integer status;

    @ApiModelProperty("排序方式 new|fire")
    private String orderBy;
}
