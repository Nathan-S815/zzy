package com.nuwa.ticket.start.api.controller.diy;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.infrastructure.ticket.database.diy.entity.LinkInfo;
import com.nuwa.infrastructure.ticket.database.diy.service.LinkInfoService;
import com.nuwa.ticket.start.api.controller.diy.vo.DiyLinkTreeVO;
import com.nuwa.ticket.start.api.controller.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("diy/link")
@Api(tags = {"装修链接"})
public class DiyLinkController {

    @Autowired
    private LinkInfoService linkInfoService;

    @ApiOperation(value = "获取链接树")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<DiyLinkTreeVO>> tree() {
        List<LinkInfo> linkInfoList = linkInfoService.lambdaQuery().list();
        return SingleResponse.of(getGroupTrees(linkInfoList));
    }

    private List<DiyLinkTreeVO> getGroupTrees(List<LinkInfo> linkInfoList) {
        List<DiyLinkTreeVO> trees = new ArrayList<DiyLinkTreeVO>();
        DiyLinkTreeVO node = null;
        for (LinkInfo link : linkInfoList) {
            node = new DiyLinkTreeVO();
            BeanUtils.copyProperties(link, node);
            node.setUrl(link.getWapUrl());
            node.setLabel(link.getTitle());
            trees.add(node);
        }
        return TreeUtil.bulid(trees, -1);
    }
}
