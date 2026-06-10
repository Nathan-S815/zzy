package com.nuwa.infrastructure.discovery.database.user.service.impl;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskPrizeRecord;
import com.nuwa.infrastructure.discovery.database.user.mapper.MemberTaskPrizeRecordMapper;
import com.nuwa.infrastructure.discovery.database.user.service.MemberService;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTaskPrizeRecordService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.discovery.database.user.vo.EquityManagerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;




/**
 * 达人任务权益提交表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-11-18
 */
@Slf4j
@Service
public class MemberTaskPrizeRecordServiceImpl extends SuperServiceImpl<MemberTaskPrizeRecordMapper, MemberTaskPrizeRecord> implements MemberTaskPrizeRecordService {

    @Autowired
    private MemberTaskPrizeRecordMapper memberTaskPrizeRecordMapper;
    @Autowired
    private MemberService memberService;

    /**
     * 权利管理(探店免票)
     * @param pageSize
     * @param pageNum
     * @param prizeTitle
     * @param beginDate
     * @param endDate
     * @return
     */
    @Override
    public IPage<EquityManagerVO> equityManagerList(Long pageSize, Long pageNum, String prizeTitle, String beginDate, String endDate) {
        Page<EquityManagerVO> page = new Page<>();
        if (pageSize != null && pageNum != null) {
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        if (StringUtils.isNotBlank(beginDate)) {
            beginDate = beginDate + " 00:00:00";
        }
        if (StringUtils.isNotBlank(endDate)) {
            endDate = endDate + " 23:59:59";
        }

        IPage<EquityManagerVO> record = memberTaskPrizeRecordMapper.selectEquityPage(page, prizeTitle, beginDate, endDate);
        return record;
    }
}
