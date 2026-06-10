package com.zzy.api.service.reportbase;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.reportbase.ReportBaseTraffic;
import com.zzy.db.entity.reportbase.ReportBaseTravel;

/**
 * <p>
 * 旅行社上报基础信息表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface IReportBaseTravelService extends IService<ReportBaseTravel> {

    PageInfo<ReportBaseTravel> getReportBaseTravelList(Integer pagNumber, Integer pagSize, String keyword);

}
