package com.zzy.api.service.ota.impl;

import com.zzy.core.utils.NumberMathUtil;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.entity.ota.ScenicCommentInfo;
import com.zzy.db.dao.ota.ScenicCommentInfoMapper;
import com.zzy.api.service.ota.IScenicCommentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * <p>
 * 酒店OTA评论数据 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-22
 */
@Service
public class ScenicCommentInfoServiceImpl extends ServiceImpl<ScenicCommentInfoMapper, ScenicCommentInfo> implements IScenicCommentInfoService {

    @Autowired
    private ScenicCommentInfoMapper scenicCommentInfoMapper;

    @Override
    public List<Map<String, Object>> getScenicSpot(String startTime, String endTime) {
        List<ScenicCommentInfo> scenicCommentInfos = scenicCommentInfoMapper.selectNowScenicScore(startTime, endTime, 5);
        if (scenicCommentInfos.size() == 0) {
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        int i = 1;
        DecimalFormat df = new DecimalFormat("######0.00");
        for (ScenicCommentInfo scenicCommentInfo : scenicCommentInfos) {
            String commentPlaceName = scenicCommentInfo.getCommentPlaceName();
            Double commentScore = scenicCommentInfo.getCommentScore();
            String beforeTime = TimeDateUtil.getBeforeDate(startTime, endTime);
            Double before = scenicCommentInfoMapper.selectBeforeScenicScore(commentPlaceName,beforeTime,startTime);
            if (before == null) {
                before = 0.0;
            }
            Map<String, Object> value = new HashMap<>();
            value.put("commentPlaceName", commentPlaceName);
            value.put("score", df.format(commentScore * 20));
            value.put("rank", i);
            if (before > commentScore) {
                value.put("float", "down");
            } else {
                value.put("float", "up");
            }
            i++;
            list.add(value);
        }
        return list;
    }

    @Override
    public Map<String, Object> getEntiretyAnalysis(String startTime, String endTime) {
        DecimalFormat df = new DecimalFormat("######0.0");
        Double score = scenicCommentInfoMapper.selectNowEntiretyScenicScore(startTime,endTime);
        if (score == null) {
            score = 0.0;
        }
        Long goodNumber = scenicCommentInfoMapper.selectNowGoodNumber(startTime,endTime);
        if (goodNumber == null) {
            goodNumber = 0L;
        }
        Long badNumber = scenicCommentInfoMapper.selectNowBadNumber(startTime,endTime);
        if (badNumber == null) {
            badNumber = 0L;
        }
        String scoreformat = df.format(score * 20);
        Map<String, Object> value = new HashMap<>();
        if ((goodNumber + badNumber) == 0) {
            value.put("score", scoreformat);
            value.put("goodRatio", 0);
            value.put("badRatio", 0);
            return value;
        } else {
            int goodRatio = Integer.parseInt(NumberMathUtil.getRateDown(goodNumber,goodNumber + badNumber,0));
            value.put("score", scoreformat);
            value.put("goodRatio", goodRatio);
            value.put("badRatio", 100-goodRatio);
            return value;
        }
    }

    @Override
    public List<Map<String, Object>> getEachAnalysis(String startTime, String endTime) {
        List<Map<String, Object>> list = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("######0.0");
        List<String> l = new ArrayList<>();
        l.add("九寨沟");
        l.add("神仙池");
        l.add("甘海子");
        l.add("大熊猫保护研究园");
        l.add("四姑娘山风景名胜区");
        l.add("黄龙风景名胜区");
        l.add("达古冰川风景名胜区");
        for (String commentPlaceName : l) {
            Double score = scenicCommentInfoMapper.selectNowEntiretyScenicScoreByName(startTime,endTime,commentPlaceName);
            if (score == null) {
                score = 0.0;
            }
            Long goodNumber = scenicCommentInfoMapper.selectNowGoodNumberByName(startTime,endTime,commentPlaceName);
            if (goodNumber == null) {
                goodNumber = 0L;
            }
            Long badNumber = scenicCommentInfoMapper.selectNowBadNumberByName(startTime,endTime,commentPlaceName);
            if (badNumber == null) {
                badNumber = 0L;
            }
            String scoreformat = df.format(score);
            Map<String, Object> value = new HashMap<>();
            if (commentPlaceName.equals("神仙池")){
                commentPlaceName = "嫩恩桑措(神仙池)";
            }
            if (commentPlaceName.equals("甘海子")){
                commentPlaceName = "爱情海(甘海子)";
            }
            if (commentPlaceName.equals("四姑娘山风景名胜区")){
                commentPlaceName = "四姑娘山";
            }
            if (commentPlaceName.equals("黄龙风景名胜区")){
                commentPlaceName = "黄龙";
            }
            if (commentPlaceName.equals("达古冰川风景名胜区")){
                commentPlaceName = "达古冰川";
            }
            value.put("commentPlaceName", commentPlaceName);
            if ((goodNumber + badNumber) == 0) {
                value.put("score", scoreformat);
                value.put("goodRatio", 0);
                value.put("badRatio", 0);
            } else {
                int goodRatio = Integer.parseInt(NumberMathUtil.getRateDown(goodNumber,goodNumber + badNumber,0));
                value.put("score", scoreformat);
                value.put("goodRatio", goodRatio);
                value.put("badRatio", 100-goodRatio);
            }
            list.add(value);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findCommentKeyWordByArea() {
        return scenicCommentInfoMapper.selectCommentKeyWordTag();
    }

}
