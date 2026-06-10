package com.nuwa.discovery.start.api.controller.user;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.infrastructure.discovery.database.dto.MemberAuthenticationDTO;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberAccountBindRecord;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberAccountBindRecordVO;
import com.nuwa.infrastructure.discovery.database.user.service.MemberAccountBindRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member/account/bind/record")
@Api(tags = {"达人账号绑定记录"})
public class MemberAccountBindRecordController {


    @Autowired
    private MemberAccountBindRecordService memberAccountBindRecordService;

    @ApiOperation(value = "根据达人账号绑定id获取对应绑定内容最新记录")
    @GetMapping(value = "/get/by_bind_id/{memberAccountBindId}")
    public SingleResponse<?> getMemberAccountBindRecordByBindId(@PathVariable("memberAccountBindId") Long memberAccountBindId, Integer status) {
        MemberAccountBindRecordVO memberAccountBindRecordVO = memberAccountBindRecordService.getMemberAccountBindRecordVOByBindId(memberAccountBindId, status);
        return SingleResponse.of(memberAccountBindRecordVO);
    }


    @ApiOperation(value = "根据id获取对应绑定内容记录")
    @GetMapping(value = "/get/{id}")
    public SingleResponse<?> getMemberAccountBindRecord(@PathVariable("id") Long id) {
        MemberAccountBindRecordVO memberAccountBindRecordVO = memberAccountBindRecordService.getMemberAccountBindRecordVO(id);
        return SingleResponse.of(memberAccountBindRecordVO);
    }


    @ApiOperation(value = "根据用户id获取对应绑定内容历史记录")
    @GetMapping(value = "/page/{userId}")
    public SingleResponse<?> getMemberAccountBindRecord(Long pageSize, Long pageNum, @PathVariable("userId") Long userId, Integer status) {
        IPage<MemberAccountBindRecord> memberAccountBindRecordPage = memberAccountBindRecordService.getMemberAccountBindRecordPage(pageSize, pageNum, userId, status);
        return SingleResponse.of(memberAccountBindRecordPage);
    }


    @ApiOperation(value = "达人认证")
    @PostMapping(value = "/authentication")
    public SingleResponse<?> authentication(@RequestBody MemberAuthenticationDTO memberAuthenticationDTO) {
        memberAccountBindRecordService.authentication(memberAuthenticationDTO);
        return SingleResponse.buildSuccess();
    }


}
