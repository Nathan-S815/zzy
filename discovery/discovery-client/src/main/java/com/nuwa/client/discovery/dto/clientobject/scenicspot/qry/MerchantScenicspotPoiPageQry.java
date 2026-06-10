package com.nuwa.client.discovery.dto.clientobject.scenicspot.qry;

import io.swagger.annotations.ApiModel;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 商户关联景区POI表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-10-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户关联景区POI表PageQry")
public class MerchantScenicspotPoiPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

}
