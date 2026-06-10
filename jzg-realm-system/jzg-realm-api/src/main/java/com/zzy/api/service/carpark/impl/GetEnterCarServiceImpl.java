package com.zzy.api.service.carpark.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.api.service.carpark.IGetEnterCarService;
import com.zzy.db.dao.carpark.GetEnterCarMapper;
import com.zzy.db.entity.carpark.GetEnterCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-24
 */
@Service
public class GetEnterCarServiceImpl extends ServiceImpl<GetEnterCarMapper, GetEnterCar> implements IGetEnterCarService {
    @Autowired
    private GetEnterCarMapper getEnterCarMapper;
    @Override
    public List<Map<Object,Object>> getCarPlaceCount(String entertime) {
        List<Map<Object, Object>> carPlaceCount = getEnterCarMapper.getCarPlaceCount(entertime);
        for (Map<Object, Object> objectObjectMap : carPlaceCount) {
            String licensePlate[]={"冀","晋","蒙","辽","黑","吉","苏","沪","浙","皖","闽","赣","鲁"
                    ,"豫","鄂","湘","粤","桂","琼","渝","川","贵","云","陕","甘","青","宁","新","津","藏","京"};
            String region[]={"河北","山西","内蒙古","辽宁","黑龙江","吉林","江苏","上海","浙江","安徽"
                    ,"福建","江西","山东","河南","湖北","湖南","广东","广西","海南","重庆"
                    ,"四川","贵州","云南","陕西","甘肃","青海","宁夏","新疆","天津","西藏","北京"};
            for(int i = 0;i<licensePlate.length;i++){
                if(objectObjectMap.get("caron").toString().equalsIgnoreCase(licensePlate[i])){
                    objectObjectMap.put("caron",region[i]);
                }
            }
        }
        return carPlaceCount;
    }
}
