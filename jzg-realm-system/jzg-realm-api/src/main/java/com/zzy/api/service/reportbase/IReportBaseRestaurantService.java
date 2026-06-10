package com.zzy.api.service.reportbase;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.reportbase.ReportBaseEntertainment;
import com.zzy.db.entity.reportbase.ReportBaseRestaurant;

/**
 * <p>
 * 餐饮基础信息表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface IReportBaseRestaurantService extends IService<ReportBaseRestaurant> {

    PageInfo<ReportBaseRestaurant> getReportBaseRestaurantList(Integer pagNumber, Integer pagSize, String keyword);
}
