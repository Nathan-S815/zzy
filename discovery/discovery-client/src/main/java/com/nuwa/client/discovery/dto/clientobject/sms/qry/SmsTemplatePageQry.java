package com.nuwa.client.discovery.dto.clientobject.sms.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 商户短信模板 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-11-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户短信模板PageQry")
public class SmsTemplatePageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("业务类型")
    private String bizCode;

    @ApiModelProperty("模板标题")
    private String title;

}
