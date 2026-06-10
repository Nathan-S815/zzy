package com.zzy.datawarehouse.display.service;


import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.datawarehouse.display.entity.MerchantInfo;
import com.zzy.datawarehouse.display.vo.ScenicMerchantsVO;

import java.util.List;

/**
 * 商户信息表
 *
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:46
 */
public interface MerchantInfoService extends IService<MerchantInfo> {


  List<ScenicMerchantsVO> getMerchantEnterNum();
}

