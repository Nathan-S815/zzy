
package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新增身份接口
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModifyUsableIdentityParam extends NuwaCommand {

    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("图标id")
    private String iconId;

    @ApiModelProperty("链接")
    private String linkUrl;
}
