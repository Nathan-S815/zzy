package com.nuwa.zeus.start.api.controller.merchant.vo;

import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckAppServerStatusVO {
    private AppInfo appInfo;

    @ApiModelProperty("有效天数")
    private Long validDay;

    @ApiModelProperty("服务状态  1:未设置 2:已过期  3:正常 4:即将过期 5:不在服务周期")
    private Integer status;
}
