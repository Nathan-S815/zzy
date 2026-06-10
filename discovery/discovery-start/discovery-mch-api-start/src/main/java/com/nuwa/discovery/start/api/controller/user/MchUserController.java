package com.nuwa.discovery.start.api.controller.user;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.discovery.dto.clientobject.user.qry.MemberPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.infrastructure.discovery.database.user.param.MemberPageParam;
import com.nuwa.infrastructure.discovery.database.user.service.MemberService;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberExaminePageVO;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberMerchantPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("user/")
@Api(tags = {"任务报名相关"})
public class MchUserController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "达人分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<Member>> prizePage(MemberPageQry qry, UserAware userAware) {
        MemberPageParam memberPageParam = new MemberPageParam(qry);
        IPage<Member> memberPageData = memberService.paginateByParam(memberPageParam);
        return SingleResponse.of(memberPageData);
    }

    @ApiOperation(value = "达人分页查询")
    @RequestMapping(value = "/member/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MemberExaminePageVO>> memberPage(MemberPageQry qry, UserAware userAware){
        IPage<MemberExaminePageVO> memberExaminePageVO = memberService.getMemberExaminePageVO(qry);
        return SingleResponse.of(memberExaminePageVO);
    }

    @ApiOperation(value = "达人分页查询(商户端)")
    @RequestMapping(value = "/member/merchant/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MemberMerchantPageVO>> memberMerchantPage(MemberPageQry qry, UserAware userAware){
        qry.setMchId(userAware.getMchId() + "");
        IPage<MemberMerchantPageVO> memberMerchantPageVO = memberService.getMemberMerchantPageVO(qry, true);
        return SingleResponse.of(memberMerchantPageVO);
    }

    @ApiOperation(value = "达人分页查询(达人库)")
    @RequestMapping(value = "/member/library/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MemberMerchantPageVO>> memberLibraryPage(MemberPageQry qry, UserAware userAware){
        IPage<MemberMerchantPageVO> memberMerchantPageVO = memberService.getMemberMerchantPageVO(qry, false);
        return SingleResponse.of(memberMerchantPageVO);
    }


    @ApiOperation(value = "达人详情-新")
    @RequestMapping(value = "/member/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<MemberMerchantPageVO> memberLibraryPage(@PathVariable("id") Long id){
        MemberMerchantPageVO memberMerchantVO = memberService.getMemberMerchantVO(id);
        return SingleResponse.of(memberMerchantVO);
    }

    @ApiOperation(value = "达人详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Member> detail(@PathVariable("id") Long id, UserAware userAware) {
        Member member = memberService.getById(id);
        return SingleResponse.of(member);
    }
}
