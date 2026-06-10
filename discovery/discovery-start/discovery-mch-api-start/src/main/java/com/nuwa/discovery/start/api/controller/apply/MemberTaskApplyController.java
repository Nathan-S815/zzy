package com.nuwa.discovery.start.api.controller.apply;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.discovery.start.api.controller.task.param.ModifyTaskWeightParam;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskApply;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTaskApplyService;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberTaskApplyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;

/**
 * @Author: WangXh
 * @DateTime: 2022/11/15
 * @Description: 达人任务报名后台
 */
@Slf4j
@Controller
@RequestMapping("apply")
@Api(tags = {"达人任务报名后台"})
public class MemberTaskApplyController {

    @Autowired
    private MemberTaskApplyService memberTaskApplyService;

    @ApiOperation(value = "视频中心")
    @RequestMapping(value = "/listVideoList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MemberTaskApplyVO>> listVideoList(Long pageSize, Long pageNum, Long taskId, String name, String beginDate, String endDate) {
        IPage<MemberTaskApplyVO> page = memberTaskApplyService.listVideoList(pageSize,pageNum,taskId, name, beginDate, endDate);
        return SingleResponse.of(page);
    }

    @ApiOperation(value = "视频权重修改")
    @RequestMapping(value = "/weight/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> weightModify(@RequestBody @Valid ModifyTaskWeightParam form, UserAware userAware) {
        boolean update = memberTaskApplyService.lambdaUpdate()
                .set(MemberTaskApply::getWeight, form.getWeight())
                .set(MemberTaskApply::getLastUpdateTime, new Date())
                .set(MemberTaskApply::getLastUpdateById, userAware.getMchUserId())
                .set(MemberTaskApply::getLastUpdateByName, userAware.getUserName())
                .eq(MemberTaskApply::getId, form.getId())
                .update();
        if (!update) {
            log.error("weightModify  task[id:{}] failed.", form.getId());
            return SingleResponse.buildFailure("904", "任务权重修改失败");
        }
        log.info("weightModify task[id:{}] success.", form.getId());
        return SingleResponse.buildSuccess();
    }
}
