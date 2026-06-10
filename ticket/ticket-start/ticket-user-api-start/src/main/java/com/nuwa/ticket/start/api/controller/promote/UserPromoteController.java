package com.nuwa.ticket.start.api.controller.promote;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.client.ticket.dto.clientobject.order.qry.UserPromoteSettlePageQry;
import com.nuwa.client.ticket.dto.clientobject.order.qry.UserPromoteSettleTotalPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.order.entity.UserPromoteSettleRecord;
import com.nuwa.infrastructure.ticket.database.order.param.UserPromoteSettleTotalPageParam;
import com.nuwa.infrastructure.ticket.database.order.service.UserPromoteSettleRecordService;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotService;
import com.nuwa.ticket.start.api.controller.promote.vo.UserPromotePageVO;
import com.nuwa.ticket.start.api.controller.promote.vo.UserPromoteTotalPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Api(tags = {"C端推广佣金相关"})
@Slf4j
@RestController
@RequestMapping("/promote")
public class UserPromoteController {

    @Autowired
    private UserPromoteSettleRecordService userPromoteSettleRecordService;

    @Autowired
    private ScenicspotService scenicspotService;


    @ApiOperation(value = "获取订单总数")
    @RequestMapping(value = "/totalOrderCount", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Integer> getTotalOrderCount(UserAware userAware) {
        Integer count = userPromoteSettleRecordService.lambdaQuery()
                .eq(UserPromoteSettleRecord::getUserId, userAware.getUserId())
                .count();
        return SingleResponse.of(count);
    }

    @ApiOperation(value = "待结算订单汇总分页查询")
    @RequestMapping(value = "/total/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<UserPromoteTotalPageVO>> totalPage(@Valid UserPromoteSettleTotalPageQry pageQry, UserAware userAware) {
        QueryWrapper<UserPromoteSettleRecord> queryWrapper = Wrappers.query();
        queryWrapper.select(UserPromoteSettleRecord.SCENICSPOT_ID, "count(1) as orderCount", "sum(" + UserPromoteSettleRecord.AMOUNT + ") as totalAmount");
        queryWrapper.groupBy(UserPromoteSettleRecord.SCENICSPOT_ID);
        queryWrapper.eq(UserPromoteSettleRecord.USER_ID, userAware.getUserId());
        IPage<Map<String, Object>> mapsPage = userPromoteSettleRecordService.pageMaps(new Page<>((long) pageQry.getPage(), (long) pageQry.getLimit()), queryWrapper);

        List<UserPromoteTotalPageVO> itemsVO = mapsPage.getRecords().stream().map(x -> {
            UserPromoteTotalPageVO vo = new UserPromoteTotalPageVO();
            String scenicId = x.get(UserPromoteSettleRecord.SCENICSPOT_ID) + "";
            vo.setScenicId(scenicId);

            Integer orderCount = Integer.parseInt(x.get("orderCount") + "");
            vo.setOrderCount(orderCount);

            BigDecimal totalAmount = new BigDecimal(x.get("totalAmount") + "");
            vo.setTotalAmount(totalAmount);
            Scenicspot scenicspot = scenicspotService.getById(scenicId);
            if (Objects.nonNull(scenicspot)) {
                vo.setScenicName(scenicspot.getName());
                vo.setMainPicture(scenicspot.getMainPicture());
            }
            return vo;
        }).collect(Collectors.toList());

        Page<UserPromoteTotalPageVO> pageVO = new Page<>((long) pageQry.getPage(), (long) pageQry.getLimit());
        pageVO.setRecords(itemsVO);

        return SingleResponse.of(pageVO);
    }

    @ApiOperation(value = "待结算订单分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<UserPromotePageVO>> page(@Valid UserPromoteSettlePageQry pageQry, UserAware userAware) {
        pageQry.setUserId(userAware.getUserId());
        UserPromoteSettleTotalPageParam pageParam = new UserPromoteSettleTotalPageParam(pageQry);
        IPage<UserPromotePageVO> pageVO = userPromoteSettleRecordService.paginateAndConvert(pageParam, this::toPageVO);
        return SingleResponse.of(pageVO);
    }

    public UserPromotePageVO toPageVO(UserPromoteSettleRecord record) {
        UserPromotePageVO vo = new UserPromotePageVO();
        BeanUtils.copyProperties(record, vo);
        return vo;
    }

}
