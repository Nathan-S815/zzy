package com.zzy.api.service.base;

import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseGuideReport;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzy
 * @since 2020-07-16
 */
public interface IBaseGuideReportService extends IService<BaseGuideReport> {

    PageInfo<Map<String, Object>> selectPageListBaseGuideReport(Integer pagNumber, Integer pagSize, String keyword);

    PageInfo<BaseGuideReport> getBaseGuideReportByGuideId(Integer pagNumber, Integer pagSize, int guideId);
}
