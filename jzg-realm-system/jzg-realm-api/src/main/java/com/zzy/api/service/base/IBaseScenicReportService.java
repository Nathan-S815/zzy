package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseHotelReport;
import com.zzy.db.entity.base.BaseScenicReport;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 景区人数收入上报表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface IBaseScenicReportService extends IService<BaseScenicReport> {

    PageInfo<Map<String, Object>> selectPageListBaseScenicReport(Integer pagNumber, Integer pagSize, String keyword);

    PageInfo<Map<String, Object>> getBaseScenicReportByScenicId(Integer pagNumber, Integer pagSize, Integer id, String startTime, String endTime);

    Map<String, Object> getInPeopleAndIncome(String userId, String nowDate);
}
