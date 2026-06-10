package com.zzy.api.service.reportbase;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.reportbase.ReportBaseShopping;
import com.zzy.db.entity.reportbase.ReportBaseTraffic;

/**
 * <p>
 * 交通上报基础信息表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface IReportBaseTrafficService extends IService<ReportBaseTraffic> {

    PageInfo<ReportBaseTraffic> getReportBaseTrafficList(Integer pagNumber, Integer pagSize, String keyword);
}
