package com.zzy.api.service.reportbase;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.reportbase.ReportBaseScenic;
import com.zzy.security.lib.entity.RoleInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区上报基础信息表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface IReportBaseScenicService extends IService<ReportBaseScenic> {

    PageInfo<ReportBaseScenic> getReportBaseScenicList(Integer pagNumber, Integer pagSize, String keyword);


    PageInfo<Map<String, Object>> getBaseSpotListByPara(Integer pagNumber, Integer pagSize, Map<String, Object> para);

    RoleInfo getRoleInfoBymerchantId(Integer merchantId);
}
