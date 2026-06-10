package com.zzy.datawarehouse.display.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.datawarehouse.display.entity.VisitorInfo;
import com.zzy.datawarehouse.display.mapper.VisitorInfoMapper;
import com.zzy.datawarehouse.display.service.VisitorInfoService;
import org.springframework.stereotype.Service;

@Service("visitorInfoService")
public class VisitorInfoServiceImpl extends ServiceImpl<VisitorInfoMapper, VisitorInfo> implements VisitorInfoService {



}