package com.nuwa.client.ticket.dto.clientobject.activity.qry;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 文化活动 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "文化活动分类Qry")
public class CultureActivityCategoryQry extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    private Long id;
}
