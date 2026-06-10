package com.zzy.api.service.reportbase;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.reportbase.ReportBaseHotel;
import com.zzy.db.entity.reportbase.ReportBaseScenic;

/**
 * <p>
 * 酒店上报基础信息表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface IReportBaseHotelService extends IService<ReportBaseHotel> {
    PageInfo<ReportBaseHotel> getReportBaseHotelList(Integer pagNumber, Integer pagSize, String keyword);

}
