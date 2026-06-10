package com.nuwa.client.ticket.dto.clientobject.scenicspot.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 景区分类表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-10-20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "景区分类表PageQry")
public class ScenicspotTypePageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

}
