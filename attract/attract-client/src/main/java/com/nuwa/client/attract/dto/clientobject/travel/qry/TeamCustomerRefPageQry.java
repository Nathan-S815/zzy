package com.nuwa.client.attract.dto.clientobject.travel.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 团队-客户关联表 PageQry参数对象
 * </pre>
 *
 * @author nanhuang @南皇
 * @date 2022-09-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "团队-客户关联表PageQry")
public class TeamCustomerRefPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

}
