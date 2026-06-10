package com.zzy.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.client.service.ABaWenLvClientService;
import com.zzy.client.service.JzgApiClientService;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.dao.base.BaseTicketMapper;
import com.zzy.db.dao.hotmap.PandaFlowMapper;
import com.zzy.db.dao.reportbase.ReportBaseScenicMapper;
import com.zzy.db.entity.base.BaseTicket;
import com.zzy.db.entity.carpark.CarGps;
import com.zzy.db.entity.hotmap.PandaFlow;
import com.zzy.db.entity.reportbase.ReportBaseScenic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class ABaWenLvTask {

    @Autowired
    private BaseTicketMapper baseTicketMapper;
    @Autowired
    private PandaFlowMapper pandaFlowMapper;
    @Autowired
    private ReportBaseScenicMapper reportBaseScenicMapper;

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void getXmgTicketTask() {
        log.info("XmgTicket定时器开始调用");
        try {
            String date = TimeDateUtil.getFormatDate(new Date());
            Integer peopleNum = pandaFlowMapper.getInPeople(date);
            if (peopleNum != null) {
                List<BaseTicket> xmgTicket = new ArrayList<>();
                BaseTicket baseTicket = new BaseTicket();
                ReportBaseScenic reportBaseScenic = reportBaseScenicMapper.getIdByName("大熊猫");
                baseTicket.setScenicId(reportBaseScenic.getId());
                baseTicket.setScenicName("甲勿海大熊猫保护研究园");
                baseTicket.setInPeople(peopleNum);
                baseTicket.setIncome(169 * peopleNum);
                baseTicket.setReportTime(date);
                baseTicket.setCreateTime(date);
                xmgTicket.add(baseTicket);
                if (xmgTicket == null || xmgTicket.isEmpty()) {
                    log.info("XmgTicket定时器api数据为空");
                    return;
                }
                log.info("XmgTicket定时器更新数量:{}", baseTicketMapper.batchInsert(xmgTicket));
                Thread.sleep(1500L);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("XmgTicket定时器异常", e);
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void getJzgTicketTask() {
        log.info("JzgTicket定时器开始调用");
        try {
            List<BaseTicket> jzgTicket = ABaWenLvClientService.getJzgTicket();
            ReportBaseScenic reportBaseScenic = reportBaseScenicMapper.getIdByName("九寨沟");
            for (BaseTicket baseTicket : jzgTicket) {
                baseTicket.setScenicId(reportBaseScenic.getId());
            }
            if (jzgTicket == null || jzgTicket.isEmpty()) {
                log.info("JzgTicket定时器api数据为空");
                return;
            }
            log.info("JzgTicket定时器更新数量:{}", baseTicketMapper.batchInsert(jzgTicket));
            Thread.sleep(1500L);
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("JzgTicket定时器异常", e);
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void getHlTicketTask() {
        log.info("HlTicket定时器开始调用");
        try {
            List<BaseTicket> hlTicket = ABaWenLvClientService.getHlTicket();
            ReportBaseScenic reportBaseScenic = reportBaseScenicMapper.getIdByName("黄龙");
            for (BaseTicket baseTicket : hlTicket) {
                baseTicket.setScenicId(reportBaseScenic.getId());
            }
            if (hlTicket == null || hlTicket.isEmpty()) {
                log.info("HlTicket定时器api数据为空");
                return;
            }
            log.info("HlTicket定时器更新数量:{}", baseTicketMapper.batchInsert(hlTicket));
            Thread.sleep(1500L);
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("HlTicket定时器异常", e);
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void getSgnsTicketTask() {
        log.info("SgnsTicket定时器开始调用");
        try {
            List<BaseTicket> sgnsTicket = ABaWenLvClientService.getSgnsTicket();
            ReportBaseScenic reportBaseScenic = reportBaseScenicMapper.getIdByName("四姑娘山");
            for (BaseTicket baseTicket : sgnsTicket) {
                baseTicket.setScenicId(reportBaseScenic.getId());
            }
            if (sgnsTicket == null || sgnsTicket.isEmpty()) {
                log.info("SgnsTicket定时器api数据为空");
                return;
            }
            log.info("SgnsTicket定时器更新数量:{}", baseTicketMapper.batchInsert(sgnsTicket));
            Thread.sleep(1500L);
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("SgnsTicket定时器异常", e);
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void getDgbcTicketTask() {
        log.info("DgbcTicket定时器开始调用");
        try {
            List<BaseTicket> dgbcTicket = ABaWenLvClientService.getDgbcTicket();
            ReportBaseScenic reportBaseScenic = reportBaseScenicMapper.getIdByName("达古冰川");
            for (BaseTicket baseTicket : dgbcTicket) {
                baseTicket.setScenicId(reportBaseScenic.getId());
            }
            if (dgbcTicket == null || dgbcTicket.isEmpty()) {
                log.info("DgbcTicket定时器api数据为空");
                return;
            }
            log.info("DgbcTicket定时器更新数量:{}", baseTicketMapper.batchInsert(dgbcTicket));
            Thread.sleep(1500L);
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("DgbcTicket定时器异常", e);
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void getSxcTicketTask() {
        log.info("SxcTicket定时器开始调用");
        try {
            List<BaseTicket> sxcTicket = ABaWenLvClientService.getSXCTicket();
            ReportBaseScenic reportBaseScenic = reportBaseScenicMapper.getIdByName("嫩恩桑措(神仙池)");
            for (BaseTicket baseTicket : sxcTicket) {
                baseTicket.setScenicId(reportBaseScenic.getId());
            }
            if (sxcTicket == null || sxcTicket.isEmpty()) {
                log.info("SxcTicket定时器api数据为空");
                return;
            }
            log.info("SxcTicket定时器更新数量:{}", baseTicketMapper.batchInsert(sxcTicket));
            Thread.sleep(1500L);
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("SxcTicket定时器异常", e);
        }
    }
}
