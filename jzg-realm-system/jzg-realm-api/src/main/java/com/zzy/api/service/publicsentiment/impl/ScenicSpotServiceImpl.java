package com.zzy.api.service.publicsentiment.impl;

import com.zzy.api.service.publicsentiment.ScenicSpotService;
import com.zzy.db.dao.publicsentiment.ScenicSpotMapper;
import com.zzy.db.entity.publicsentiment.ScenicNews;
import com.zzy.db.entity.publicsentiment.ScenicSpot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScenicSpotServiceImpl implements ScenicSpotService {

    @Autowired
    private ScenicSpotMapper scenicSpotMapper;

    @Override
    public List<ScenicSpot> getScenicSpotList(Integer serviceNumber) {
        if(serviceNumber == null){
            return new ArrayList<>();
        }
        List<ScenicSpot> scenicSpotList = scenicSpotMapper.getScenicSpotList(serviceNumber);
        return scenicSpotList;
    }

    @Override
    public List<ScenicNews> getScenicNewsList() {
        List<ScenicNews> scenicNewsList = scenicSpotMapper.getScenicNewsList();
        return scenicNewsList;
    }
}
