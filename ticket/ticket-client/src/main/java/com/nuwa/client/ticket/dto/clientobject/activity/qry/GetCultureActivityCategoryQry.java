package com.nuwa.client.ticket.dto.clientobject.activity.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "C端查询所有活动分类")
public class GetCultureActivityCategoryQry extends NuwaPageQry {

    private String appId;
    private String mchId;
}
