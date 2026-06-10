package com.nuwa.client.ticket.dto.clientobject.callcenter.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 客服基础信息表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客服基础信息表PageQry")
public class CustomerServicePageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

}
