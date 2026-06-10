package com.zzy.api.service.ota;

import com.zzy.db.entity.ota.HotelCommentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 酒店OTA评论数据 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-22
 */
public interface IHotelCommentInfoService extends IService<HotelCommentInfo> {
    public List<Map<String,Object>> getHotel(String startTime,String endTime);
}
