package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseHotel;
import com.zzy.db.entity.base.BaseRecreation;

import java.util.Map;

/**
 * <p>
 * 休闲娱乐 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface IBaseRecreationService extends IService<BaseRecreation> {
    PageInfo<BaseRecreation> selectPageList(Integer pagNumber, Integer pagSize, Map<String, Object> para);
}
