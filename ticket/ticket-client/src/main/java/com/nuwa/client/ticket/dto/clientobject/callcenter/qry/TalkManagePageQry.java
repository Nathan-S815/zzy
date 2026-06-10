package com.nuwa.client.ticket.dto.clientobject.callcenter.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 会话管理 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "会话管理PageQry")
public class TalkManagePageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

}
