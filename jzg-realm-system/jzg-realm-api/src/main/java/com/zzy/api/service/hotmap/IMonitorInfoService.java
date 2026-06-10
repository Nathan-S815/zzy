package com.zzy.api.service.hotmap;

import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.hotmap.MonitorInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 监控信息表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-27
 */
public interface IMonitorInfoService extends IService<MonitorInfo> {

    PageInfo<MonitorInfo> listMonitorInfo(Integer pagNumber, Integer pagSize,Integer type);

    List<Map<String,Object>> getInAndOutPeople(String dayTime);

}
