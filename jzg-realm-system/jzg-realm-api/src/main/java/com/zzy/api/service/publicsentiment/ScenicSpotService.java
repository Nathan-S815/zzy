package com.zzy.api.service.publicsentiment;

import com.zzy.db.entity.publicsentiment.ScenicNews;
import com.zzy.db.entity.publicsentiment.ScenicSpot;

import java.util.List;

public interface ScenicSpotService {

    public List<ScenicSpot> getScenicSpotList(Integer serviceNumber);

    public List<ScenicNews>getScenicNewsList();
}
