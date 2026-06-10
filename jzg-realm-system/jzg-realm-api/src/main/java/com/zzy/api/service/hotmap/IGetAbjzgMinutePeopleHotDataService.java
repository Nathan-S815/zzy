package com.zzy.api.service.hotmap;

import com.zzy.db.entity.hotmap.GetAbjzgMinutePeopleHotData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 反馈阿坝九寨沟县等区域当前人流热力图 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
public interface IGetAbjzgMinutePeopleHotDataService extends IService<GetAbjzgMinutePeopleHotData> {

    List<Map<String,Object>> getLatest();
}
