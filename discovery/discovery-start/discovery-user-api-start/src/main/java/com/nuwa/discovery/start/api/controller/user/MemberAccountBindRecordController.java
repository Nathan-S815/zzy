package com.nuwa.discovery.start.api.controller.user;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.framework.base.UserAware;
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
    @GetMapping(value = "/get/current")
    public SingleResponse<?> getMemberAccountBindRecordByBindId(UserAware userAware, Integer status) {
        MemberAccountBindRecordVO memberAccountBindRecordVO = memberAccountBindRecordService.getMemberAccountBindRecordVOByUserId(userAware.getUserId(),status);
        return SingleResponse.of(memberAccountBindRecordVO);
    }


}
