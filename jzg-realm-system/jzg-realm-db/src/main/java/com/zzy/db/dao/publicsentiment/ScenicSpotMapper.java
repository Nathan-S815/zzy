package com.zzy.db.dao.publicsentiment;

import com.zzy.db.entity.publicsentiment.ScenicNews;
import com.zzy.db.entity.publicsentiment.ScenicSpot;

import java.util.List;

public interface ScenicSpotMapper {

    public List<ScenicSpot>getScenicSpotList(Integer serviceNumber);

    public List<ScenicNews>getScenicNewsList();
}
