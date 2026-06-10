package com.nuwa.app.zeus.client.feign;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.api.apppage.AppPageInfoClientI;
import com.nuwa.infrastructure.zeus.database.mch.entity.AppPageInfo;
import com.nuwa.infrastructure.zeus.database.mch.mapper.AppPageInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AppPageInfoClientImpl implements AppPageInfoClientI {
    @Autowired
    private AppPageInfoMapper appPageInfoMapper;
    @Override
    public SingleResponse getUrlById(Long id) {
        AppPageInfo appPageInfo = appPageInfoMapper.selectById(id);
        return SingleResponse.of(appPageInfo.getPageUri());
    }
}
