package com.nuwa.client.ticket.dto.clientobject.callcenter.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 *  PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PageQry")
public class CallCenterPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    private Long appId;

}
