package com.zzy.api.service.eventdepart;

import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.eventdepart.ComplainInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 小程序投诉表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-07-15
 */
public interface IComplainInfoService extends IService<ComplainInfo> {

    PageInfo<Map<String,Object>> listComplainInfo(Integer pageNo, Integer pageSize,Integer state,String creatTime,String startTime,String endTime);

    int updateComplainInfoState(Integer id,Integer state);

    int insertReplenish(Integer id,String replenish);

}
