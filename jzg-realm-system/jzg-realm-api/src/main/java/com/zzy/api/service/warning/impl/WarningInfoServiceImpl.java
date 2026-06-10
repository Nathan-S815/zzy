package com.zzy.api.service.warning.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.zzy.api.service.warning.IWarningInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.core.utils.MinioUtil;
import com.zzy.db.dao.warning.WarningInfoMapper;
import com.zzy.db.entity.warning.NzFileModel;
import com.zzy.db.entity.warning.WarningInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-27
 */
@Service
public class WarningInfoServiceImpl extends ServiceImpl<WarningInfoMapper, WarningInfo> implements IWarningInfoService {

    @Autowired
    private WarningInfoMapper warningInfoMapper;

    @Override
    public boolean sendMessage( String address, String addTime, String describe,String longitude,String latitude, List<NzFileModel> fileList) {
        String path = "http://112.44.67.32:8101/zhzf.manage/api/ed/dockingData/create";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Map<String, Object> param = new HashMap<>();
        param.put("address", address);
        param.put("addTime", addTime);
        param.put("describe", describe);
        param.put("longitude", longitude);
        param.put("latitude", latitude);
        param.put("type","1");
        param.put("fileList", fileList);
        String body = HttpUtil.createPost(path).timeout(1000 * 60).headerMap(headers, true).body(JSON.toJSONString(param)).execute().body();
        if (body.contains("添加成功")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean saveMessage(String address, String addTime, String describe,String longitude,String latitude,List<NzFileModel> fileList) {
        NzFileModel nzFileModel1 = null;
        NzFileModel nzFileModel2 = null;
        NzFileModel nzFileModel3 = null;
        if (fileList != null){
            if (fileList.size()>0){
                nzFileModel2 = fileList.get(0);
            }
            if (fileList.size()>1){
                nzFileModel2 = fileList.get(1);
            }
            if (fileList.size()>2){
                nzFileModel3 = fileList.get(2);
            }
        }
        Integer i = warningInfoMapper.saveMessage(address, addTime, describe,longitude,latitude, nzFileModel1, nzFileModel2, nzFileModel3, DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return i > 0 ? true : false;
    }

}
