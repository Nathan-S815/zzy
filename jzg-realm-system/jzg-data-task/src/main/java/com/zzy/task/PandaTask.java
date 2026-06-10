package com.zzy.task;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;


import com.zzy.db.dao.hotmap.PandaFlowMapper;
import com.zzy.db.entity.hotmap.PandaFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PandaTask {

    @Autowired
    private PandaFlowMapper pandaFlowMapper;

    @Scheduled(cron = "0 1,59 * * * ?")
    public void  pandaFlowSave(){
        log.info("熊猫馆客流开始调用");
        try {
            String s = HttpUtil.get("http://101.71.28.52:8452/longIn");
            JSONObject jsonObject = JSONUtil.parseObj(s);
            PandaFlow pandaFlow = JSONUtil.toBean(jsonObject, PandaFlow.class);
            System.out.println(pandaFlow);
            int insert = pandaFlowMapper.insertPandan(pandaFlow);
            System.out.println(insert);
        } catch (Exception e) {
            log.error("熊猫馆客流数据异常",e);
        }

    }
}
