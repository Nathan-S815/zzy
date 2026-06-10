package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseTrafficReport;

import java.util.Map;

/**
 * <p>
 * 交通客流收入表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-13
 */
public interface IBaseTrafficReportService extends IService<BaseTrafficReport> {
    PageInfo<Map<String,Object>> selectPageListBaseTrafficReport(Integer pagNumber, Integer pagSize, String keyword);

    PageInfo<Map<String,Object>> getBaseTrafficReportByTrafficId(Integer pagNumber, Integer pagSize, Integer id,String startTime,String endTime);
}
