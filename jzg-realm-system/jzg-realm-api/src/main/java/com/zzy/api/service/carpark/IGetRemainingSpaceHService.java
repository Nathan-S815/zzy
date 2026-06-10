package com.zzy.api.service.carpark;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.db.entity.carpark.GetRemainingSpaceH;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-24
 */
public interface IGetRemainingSpaceHService extends IService<GetRemainingSpaceH> {
    public List<Map<String,Object>> getAllScenicPark();

    public List<Map<String,Object>> getAllScenicParkScreen();

    public List<Map<String,Object>> getParkByScenic(String scenicName);
}
