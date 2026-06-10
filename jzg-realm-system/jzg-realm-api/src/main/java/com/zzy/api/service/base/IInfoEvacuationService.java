package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.InfoActivityPropagate;
import com.zzy.db.entity.base.InfoEvacuation;

import java.util.Map;

/**
 * <p>
 * 疏导分流 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface IInfoEvacuationService extends IService<InfoEvacuation> {
    PageInfo<InfoEvacuation> selectPageListInfoEvacuation(Integer pagNumber, Integer pagSize, Map<String, Object> para);
}
