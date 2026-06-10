package com.nuwa.discovery.start.api.controller.user;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.discovery.dto.clientobject.user.qry.MemberPageQry;
import com.nuwa.discovery.start.api.controller.dto.MemberTabBindDTO;
import com.nuwa.discovery.start.api.controller.dto.MemberTabUnbindDTO;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTag;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberTagVO;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.infrastructure.discovery.database.user.param.MemberPageParam;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTagBindService;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTagService;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberTagBindMemberVO;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberTagBindTagVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member/tag")
@Api(tags = {"达人标签相关"})
public class MemberTagController {

    @Autowired
    private MemberTagService memberTagService;

    @Autowired
    private MemberTagBindService memberTagBindService;


    @ApiOperation(value = "新增达人标签")
    @PostMapping(value = "/add")
    public SingleResponse<?> addMemberTag(@RequestBody MemberTag memberTag, UserAware userAware) {
        memberTagService.addMemberTag(memberTag);
        return SingleResponse.buildSuccess();
    }


    @ApiOperation(value = "删除达人标签")
    @PostMapping(value = "/del/{id}")
    public SingleResponse<?> delMemberTag(@PathVariable("id") Long id, UserAware userAware) {
        memberTagService.delMemberTag(id);
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "修改达人标签")
    @PostMapping(value = "/update")
    public SingleResponse<?> updateMemberTag(@RequestBody MemberTag memberTag, UserAware userAware) {
        memberTagService.updateMember(memberTag);
        return SingleResponse.buildSuccess();
    }


    @ApiOperation(value = "根据id获取达人标签")
    @GetMapping(value = "/get")
    public SingleResponse<?> getMember(Long id, UserAware userAware) {
        MemberTag member = memberTagService.getMember(id);
        return SingleResponse.of(member);
    }

    @ApiOperation(value = "获取达人标签列表")
    @GetMapping(value = "/page")
    public SingleResponse<?> getMemberPage(Long pageSize, Long pageNum, UserAware userAware) {
        IPage<MemberTagVO> memberTagVOPage = memberTagService.getMemberTagVOPage(pageSize, pageNum);
        return SingleResponse.of(memberTagVOPage);
    }


    @ApiOperation(value = "绑定达人标签")
    @PostMapping(value = "/bind")
    public SingleResponse<?> BindMemberTag(@RequestBody MemberTabBindDTO memberTabBindDTO, UserAware userAware) {
        memberTagBindService.addMemberTabBind(memberTabBindDTO.getMemberTagBindList());
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "解除达人标签绑定")
    @PostMapping(value = "/unbind")
    public SingleResponse<?> UnbindMemberTag(@RequestBody MemberTabUnbindDTO memberTabUnbindDTO, UserAware userAware) {
        memberTagBindService.delMemberTagBind(memberTabUnbindDTO.getIds());
        return SingleResponse.buildSuccess();
    }


    @ApiOperation(value = "根据达人id获取标签绑定情况")
    @GetMapping(value = "/bind/tag/page/{memberId}")
    public SingleResponse<?> getMemberTagBindTagPage(Long pageSize, Long pageNum, @PathVariable("memberId") Long memberId) {
        IPage<MemberTagBindTagVO> memberTagBindTagVO = memberTagBindService.getMemberTagBindTagVO(pageSize, pageNum, memberId);
        return SingleResponse.of(memberTagBindTagVO);

    }


    @ApiOperation(value = "根据达人标签id获取绑定的达人")
    @GetMapping(value = "/bind/member/page/{memberTagId}")
    public SingleResponse<?> getMemberTagBindMemberPage(Long pageSize, Long pageNum, @PathVariable("memberTagId") Long memberTagId) {
        IPage<MemberTagBindMemberVO> memberTagBindMemberVO = memberTagBindService.getMemberTagBindMemberVO(pageSize, pageNum, memberTagId);
        return SingleResponse.of(memberTagBindMemberVO);
    }



}
