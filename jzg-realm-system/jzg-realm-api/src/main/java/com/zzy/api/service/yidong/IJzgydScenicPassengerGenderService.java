package com.zzy.api.service.yidong;

import com.zzy.db.entity.yidong.JzgydScenicPassengerGender;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 反馈阿坝九寨沟县及景区客流性别数据(日月) 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
public interface IJzgydScenicPassengerGenderService extends IService<JzgydScenicPassengerGender> {

    List<Map<String,Integer>> getTouristPortraitSexCount(String startTime,String endTime);
}
