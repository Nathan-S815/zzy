package com.zzy.datawarehouse.display.service.impl;


import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.datawarehouse.display.entity.MerchantInfo;
import com.zzy.datawarehouse.display.entity.OneOpenApiRecord;
import com.zzy.datawarehouse.display.mapper.MerchantInfoMapper;
import com.zzy.datawarehouse.display.mapper.OneOpenApiRecordMapper;
import com.zzy.datawarehouse.display.service.MerchantInfoService;
import com.zzy.datawarehouse.display.service.OneOpenApiRecordService;
import com.zzy.datawarehouse.display.vo.OneCodeUseInfoVO;
import com.zzy.datawarehouse.display.vo.OneOpenApiMemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Member;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("oneOpenApiRecordService")
public class OneOpenApiRecordServiceImpl extends ServiceImpl<OneOpenApiRecordMapper, OneOpenApiRecord> implements OneOpenApiRecordService {

    @Autowired
    private MerchantInfoService merchantInfoService;


    @Override
    public SingleResponse<OneOpenApiMemberVO> getOpenApiUseServiceNum() {
        OneOpenApiMemberVO oneOpenApiMemberVO = new OneOpenApiMemberVO();
        // 商家数量
        List<MerchantInfo> merchantInfoList = merchantInfoService.list();
        if (!CollectionUtils.isEmpty(merchantInfoList)) {
            oneOpenApiMemberVO.setMerchantsNum(merchantInfoList.size());
        }

        List<MerchantInfo> merchantInfoOneApiList = merchantInfoList.stream().filter(item -> item.getIsOneClient().equals("1")).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(merchantInfoOneApiList)) {
            double d1 = merchantInfoOneApiList.size() * 1.0;
            double d2 = merchantInfoList.size() * 1.0;
            DecimalFormat decimalFormat = new DecimalFormat("##.00%");
            oneOpenApiMemberVO.setMerchantsCoverage(String.valueOf(decimalFormat.format(d1 / d2)));

        }
        oneOpenApiMemberVO.setVisitorNum(1022);
        oneOpenApiMemberVO.setVisitorOneOpenNum(982);
        oneOpenApiMemberVO.setOneOpenUsageRate("69.00%");
        return SingleResponse.of(oneOpenApiMemberVO);
    }

    @Override
    public  SingleResponse<List<OneCodeUseInfoVO>> getOneCodeUseInfoVOList() {
       List<OneCodeUseInfoVO> oneCodeUseInfoVOList = new ArrayList<OneCodeUseInfoVO>();
        return SingleResponse.of(oneCodeUseInfoVOList);
    }

}