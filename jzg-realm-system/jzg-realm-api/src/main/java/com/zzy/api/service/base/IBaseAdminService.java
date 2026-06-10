package com.zzy.api.service.base;

import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzy
 * @since 2020-07-16
 */
public interface IBaseAdminService extends IService<BaseAdmin> {

    PageInfo<Map<String,Object>> getBaseAdminList(Integer pageNo,Integer pageSize,String keyWord);
}
