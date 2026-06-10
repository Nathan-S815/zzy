package com.nuwa.app.zeus.command.mch.vo;

import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SortAppInfo extends AppInfo {
    private Integer orderNum;
}
