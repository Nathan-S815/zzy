package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseScenic;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区基础信息 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface IBaseScenicService extends IService<BaseScenic> {

    Page<BaseScenic> getValueList(Page<BaseScenic> page, String typeCode);

    PageInfo<BaseScenic> selectPageList(Integer pagNumber, Integer pagSize, Map<String, Object> para);

}
