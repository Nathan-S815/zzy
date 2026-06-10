package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseShoppingReport;

import java.util.Map;

/**
 * <p>
 * 购物场所收入表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-13
 */
public interface IBaseShoppingReportService extends IService<BaseShoppingReport> {

    PageInfo<Map<String,Object>> selectPageListBaseShoppingReport(Integer pagNumber, Integer pagSize, String keyword);

    PageInfo<Map<String,Object>> getBaseShoppingReportByShoppingId(Integer pagNumber, Integer pagSize, Integer id,String startTime,String endTime);
}
