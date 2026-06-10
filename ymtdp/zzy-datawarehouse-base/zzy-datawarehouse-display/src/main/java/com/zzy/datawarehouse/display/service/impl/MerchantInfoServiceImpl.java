package com.zzy.datawarehouse.display.service.impl;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.datawarehouse.display.entity.MerchantInfo;
import com.zzy.datawarehouse.display.mapper.MerchantInfoMapper;
import com.zzy.datawarehouse.display.service.MerchantInfoService;
import com.zzy.datawarehouse.display.vo.ScenicMerchantsVO;
import org.springframework.stereotype.Service;

;import java.util.ArrayList;
import java.util.List;


@Service("merchantInfoService")
public class MerchantInfoServiceImpl extends ServiceImpl<MerchantInfoMapper, MerchantInfo> implements MerchantInfoService {


    @Override
    public List<ScenicMerchantsVO> getMerchantEnterNum() {
        List<ScenicMerchantsVO> scenicMerchantsVOS = new ArrayList<>();
        return scenicMerchantsVOS;
    }
}