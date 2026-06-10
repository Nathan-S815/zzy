package com.nuwa.infrastructure.ticket.database.dict.service;

import com.nuwa.framework.database.supper.SuperService;
import com.nuwa.infrastructure.ticket.database.dict.entity.SysDictData;

/**
 * 字典数据表 服务类
 *
 * @author huyonghack@163.com
 * @since 2021-04-27
 */
public interface SysDictDataService extends SuperService<SysDictData> {

    public boolean insertNewDict(SysDictData dictData);

}
