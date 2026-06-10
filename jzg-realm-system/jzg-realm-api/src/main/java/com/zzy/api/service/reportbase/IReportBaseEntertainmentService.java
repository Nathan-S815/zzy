package com.zzy.api.service.reportbase;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.reportbase.ReportBaseEntertainment;
import com.zzy.db.entity.reportbase.ReportBaseScenic;

/**
 * <p>
 * 娱乐上报基础信息表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface IReportBaseEntertainmentService extends IService<ReportBaseEntertainment> {

    PageInfo<ReportBaseEntertainment> getReportBaseEntertainmentList(Integer pagNumber, Integer pagSize, String keyword);

}
