package com.zzy.datawarehouse.display.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.datawarehouse.display.entity.MerchantType;
import com.zzy.datawarehouse.display.mapper.MerchantTypeMapper;
import com.zzy.datawarehouse.display.service.MerchantTypeService;
import org.springframework.stereotype.Service;

@Service("merchantTypeService")
public class MerchantTypeServiceImpl extends ServiceImpl<MerchantTypeMapper, MerchantType> implements MerchantTypeService {

}