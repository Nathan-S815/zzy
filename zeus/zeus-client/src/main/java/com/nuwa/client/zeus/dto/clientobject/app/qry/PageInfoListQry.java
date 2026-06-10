package com.nuwa.client.zeus.dto.clientobject.app.qry;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * GetPageNavQry
 *
 * @author hy
 * @date 2021/4/28 19:51
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "页面链接查询参数")
public class PageInfoListQry extends NuwaCommand {

    private Long appId;
}
