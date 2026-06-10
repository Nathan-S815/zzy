package com.nuwa.client.attract.dto.clientobject.teamuseref.qry;


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
 * @author nanhuang @Wangxh
 * @date 2022-11-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PageQry")
public class TeamUserRefPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

}
