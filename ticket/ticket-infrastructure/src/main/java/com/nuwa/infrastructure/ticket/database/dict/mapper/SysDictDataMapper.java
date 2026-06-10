package com.nuwa.infrastructure.ticket.database.dict.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.ticket.database.dict.entity.SysDictData;
import org.springframework.stereotype.Repository;


/**
 * 字典数据表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-04-27
 */
@Repository
public interface SysDictDataMapper extends SuperMapper<SysDictData> {

    public boolean insertNewDict(SysDictData dictData);

}
