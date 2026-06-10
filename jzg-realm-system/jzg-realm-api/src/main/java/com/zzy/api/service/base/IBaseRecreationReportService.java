package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseRecreationReport;

import java.util.Map;

/**
 * <p>
 * 娱乐客流收入表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-13
 */
public interface IBaseRecreationReportService extends IService<BaseRecreationReport> {

    PageInfo<Map<String,Object>> selectPageListBaseRecreationReport(Integer pagNumber, Integer pagSize, String keyword);

    PageInfo<Map<String,Object>> getBaseRecreationReportByRecreationId(Integer pagNumber, Integer pagSize, Integer id,String startTime,String endTime);
}
