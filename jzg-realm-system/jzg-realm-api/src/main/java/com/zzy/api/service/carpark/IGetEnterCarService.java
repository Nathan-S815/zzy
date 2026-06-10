package com.zzy.api.service.carpark;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.db.entity.carpark.GetEnterCar;

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
public interface IGetEnterCarService extends IService<GetEnterCar> {

     List<Map<Object,Object>> getCarPlaceCount(String entertime);

}
