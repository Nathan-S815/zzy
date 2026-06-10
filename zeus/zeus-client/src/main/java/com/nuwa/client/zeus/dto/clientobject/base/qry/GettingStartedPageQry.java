package com.nuwa.client.zeus.dto.clientobject.base.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 新手入门 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-07-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新手入门PageQry")
public class GettingStartedPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

}
