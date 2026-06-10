package com.zzy.api.service.carpark.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.api.service.carpark.IGetRemainingSpaceHService;
import com.zzy.core.utils.NumberMathUtil;
import com.zzy.core.utils.SortUtil;
import com.zzy.db.dao.carpark.GetRemainingSpaceHMapper;
import com.zzy.db.entity.carpark.GetRemainingSpaceH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 停车场剩余车位(历史) 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-24
 */
@Service
public class GetRemainingSpaceHServiceImpl extends ServiceImpl<GetRemainingSpaceHMapper, GetRemainingSpaceH> implements IGetRemainingSpaceHService {

    @Autowired
    private GetRemainingSpaceHMapper getRemainingSpaceHMapper;

    @Override
    public List<Map<String, Object>> getAllScenicPark() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> allScenicPark = getRemainingSpaceHMapper.getAllScenicPark();
        for (Map<String, Object> scenicPark : allScenicPark) {
            double remaispaces = Double.parseDouble(scenicPark.get("remaispaces").toString());
            double totalspaces = Double.parseDouble(scenicPark.get("totalspaces").toString());
            scenicPark.put("remaispaces", new Double(remaispaces).intValue());
            scenicPark.put("totalspaces", new Double(totalspaces).intValue());
//            scenicPark.put("rate",100-Integer.parseInt(NumberMathUtil.getRate(scenicPark.get("remaispaces"), scenicPark.get("totalspaces"),0)));
            scenicPark.put("rate",Math.round((Integer.parseInt(scenicPark.get("totalspaces").toString())-Integer.parseInt(scenicPark.get("remaispaces").toString()))*1.0/Integer.parseInt(scenicPark.get("totalspaces").toString())*100));
            list.add(scenicPark);
        }
        return SortUtil.comparator(list,"rate",SortUtil.DESC);
    }

    @Override
    public List<Map<String, Object>> getParkByScenic(String scenicName) {
        List<Map<String, Object>> parks = getRemainingSpaceHMapper.getParkByScenic(scenicName);
        return parks;
    }

    @Override
    public List<Map<String, Object>> getAllScenicParkScreen() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> parkInfos = getRemainingSpaceHMapper.getAllScenicParkScreen();
        for (Map<String, Object> parkInfo : parkInfos) {
            double remaispaces = Double.parseDouble(parkInfo.get("remaispaces").toString());
            double totalspaces = Double.parseDouble(parkInfo.get("totalspaces").toString());
            parkInfo.put("remaispaces", new Double(remaispaces).intValue());
            parkInfo.put("totalspaces", new Double(totalspaces).intValue());
            parkInfo.put("rate",Math.round((Integer.parseInt(parkInfo.get("totalspaces").toString())-Integer.parseInt(parkInfo.get("remaispaces").toString()))*1.0/Integer.parseInt(parkInfo.get("totalspaces").toString())*100));
            list.add(parkInfo);
        }
        return SortUtil.comparator(list,"rate",SortUtil.DESC);
    }
}
