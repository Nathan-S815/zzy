package com.nuwa.client.ticket.dto.clientobject.notice.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 公告表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-03-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "公告表PageQry")
public class NoticeInfoPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("mchId")
    private Long mchId;

    @ApiModelProperty("上下架 0:下架 1:上架")
    private Integer status;
}
