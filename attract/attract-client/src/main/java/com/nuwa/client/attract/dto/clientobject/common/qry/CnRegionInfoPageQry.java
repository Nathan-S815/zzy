package com.nuwa.client.attract.dto.clientobject.common.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 中国地区信息 PageQry参数对象
 * </pre>
 *
 * @author nanhuang @南皇
 * @date 2022-09-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "中国地区信息PageQry")
public class CnRegionInfoPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

}
