package com.zzy.datawarehouse.display.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.datawarehouse.display.entity.ScenicSpot;
import com.zzy.datawarehouse.display.mapper.ScenicSpotMapper;
import com.zzy.datawarehouse.display.service.ScenicSpotService;
import org.springframework.stereotype.Service;

@Service("scenicSpotService")
public class ScenicSpotServiceImpl extends ServiceImpl<ScenicSpotMapper, ScenicSpot> implements ScenicSpotService {


}