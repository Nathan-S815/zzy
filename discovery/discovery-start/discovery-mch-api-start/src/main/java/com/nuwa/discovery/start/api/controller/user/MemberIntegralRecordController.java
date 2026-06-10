package com.nuwa.discovery.start.api.controller.user;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralLevel;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralRecord;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberAccountBindRecordVO;
import com.nuwa.infrastructure.discovery.database.user.service.MemberIntegralRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member/integral/record")
@Api(tags = {"达人积分记录"})
public class MemberIntegralRecordController {

    @Autowired
    private MemberIntegralRecordService memberIntegralRecordService;


    @ApiOperation(value = "根据达人id获取对应成长值记录")
    @GetMapping(value = "/page/by_user_id/{userId}")
    public SingleResponse<?> getMemberIntegralRecordPage(@PathVariable("userId") Long userId, Long pageSize, Long pageNum) {
        IPage<MemberIntegralRecord> memberIntegralRecordPage = memberIntegralRecordService.getMemberIntegralRecordPage(pageSize, pageNum, userId);
        return SingleResponse.of(memberIntegralRecordPage);
    }

    @ApiOperation(value = "新增达人等级")
    @PostMapping(value = "/level/add")
    public SingleResponse<?> addMemberIntegralLevel(@RequestBody MemberIntegralLevel memberIntegralLevel) {
        memberIntegralRecordService.addMemberIntegralLevel(memberIntegralLevel);
        return SingleResponse.buildSuccess();
    }


    @ApiOperation(value = "删除达人等级")
    @PostMapping(value = "/level/del/{id}")
    public SingleResponse<?> getMemberIntegralRecordPage(@PathVariable("id") Long id) {
        memberIntegralRecordService.delMemberIntegralLevel(id);
        return SingleResponse.buildSuccess();
    }


    @ApiOperation(value = "更新达人等级")
    @PostMapping(value = "/level/update")
    public SingleResponse<?> updateMemberIntegralLevel(@RequestBody MemberIntegralLevel memberIntegralLevel) {
        //将数量设置为null防止更新
        memberIntegralLevel.setCount(null);
        memberIntegralRecordService.updateMemberIntegralLevel(memberIntegralLevel);
        return SingleResponse.buildSuccess();
    }


    @ApiOperation(value = "根据达人等级id获取达人等级详情")
    @GetMapping(value = "/level/get/{id}")
    public SingleResponse<?> getMemberIntegralLevel(@PathVariable("id") Long id) {
        MemberIntegralLevel memberIntegralLevel = memberIntegralRecordService.getMemberIntegralLevel(id);
        return SingleResponse.of(memberIntegralLevel);
    }



    @ApiOperation(value = "获取达人等级分页数据")
    @GetMapping(value = "/page")
    public SingleResponse<?> getMemberIntegralLevelPage(Long pageSize, Long pageNum) {
        IPage<MemberIntegralLevel> memberIntegralLevelPage = memberIntegralRecordService.getMemberIntegralLevelPage(pageSize, pageNum);
        return SingleResponse.of(memberIntegralLevelPage);
    }

}
