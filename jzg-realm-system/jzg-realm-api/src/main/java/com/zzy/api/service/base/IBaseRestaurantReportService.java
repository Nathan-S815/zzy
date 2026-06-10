package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseRestaurantReport;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 餐饮场所收入表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-13
 */
public interface IBaseRestaurantReportService extends IService<BaseRestaurantReport> {

    PageInfo<Map<String,Object>> selectPageListBaseRestaurantReport(Integer pagNumber, Integer pagSize, String keyword);

    PageInfo<Map<String,Object>> getBaseRestaurantReportByRestaurantId(Integer pagNumber, Integer pagSize, Integer id,String startTime,String endTime);

}
