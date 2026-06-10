package com.nuwa.client.zeus.dto.clientobject.material.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 *  PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-04-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PageQry")
public class MaterialPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    private Long typeId;

    @ApiModelProperty("文件类型  1:文章 2:图片 3:视频,4其他")
    private Integer fileType;

}
