package com.nuwa.ticket.start.api.controller.one.vo;

import com.nuwa.infrastructure.ticket.database.one.entity.OneOpenApiRecord;
import com.nuwa.infrastructure.ticket.database.one.entity.OneRightsConf;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 一码通调用记录
 *
 * @author huyonghack@163.com
 * @since 2022-10-29
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OneOpenApiRecordPageVO")
public class OneOpenApiRecordPageVO extends OneOpenApiRecord {

    private OneRightsConf chooseRights;
}
