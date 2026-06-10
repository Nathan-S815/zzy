package com.zzy.api.service.ota;

import com.zzy.db.entity.ota.ScenicCommentInfo;
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
public interface IScenicCommentInfoService extends IService<ScenicCommentInfo> {
    public List<Map<String,Object>> getScenicSpot(String startTime,String endTime);

    public Map<String,Object> getEntiretyAnalysis(String startTime,String endTime);

    public List<Map<String,Object>> getEachAnalysis(String startTime,String endTime);

    List<Map<String,Object>> findCommentKeyWordByArea();
}
