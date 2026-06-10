package com.nuwa.infrastructure.ticket.database.mall.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.MallTradePageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class MallTradePageParam extends PageQry<MallTrade> {
    private static final long serialVersionUID = 1L;

    private MallTradePageQry qry;

    public MallTradePageParam(MallTradePageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MallTrade> toQueryWrapper() {
        LambdaQueryWrapper<MallTrade> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(MallTrade.class,
                t -> !t.getColumn().endsWith("_editor")
                        && !t.getColumn().equalsIgnoreCase(MallTrade.MCH_ID)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.PROVINCE_ID)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.CITY_ID)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.COUNTY_ID)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.CREATE_BY)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.UPDATE_BY)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.MEMBER_ID)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.PAY_ACCOUNT)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.SPECIFICATIONS_ID)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.CONSIGNEE_NAME)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.CONSIGNEE_TEL)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.CONSIGNEE_ADDR)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.TOTAL_AMOUNT)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.PAY_CHANNEL)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.REMARK)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.EXPIRE_TIME)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.CANCEL_TIME)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.REFUND_REASON)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.REFUND_TIME)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.REFUND_AMOUNT)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.REFUND_ORDER_NO)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.UPDATE_TIME)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.DELETE_FLAG)
        );
       // queryWrapper.eq(MallTrade::getAppId, qry.getAppId());
        queryWrapper.eq(MallTrade::getMchId,qry.getUserAware().getMchId());
        queryWrapper.eq(!StrUtil.isBlankOrUndefined(qry.getTradeNo()), MallTrade::getTradeNo, qry.getTradeNo());
        queryWrapper.eq(Objects.nonNull(qry.getOrderStatus()), MallTrade::getOrderStatus, qry.getOrderStatus());
        queryWrapper.eq(Objects.nonNull(qry.getClassificationFirstId()), MallTrade::getClassificationFirstId, qry.getClassificationFirstId());
        queryWrapper.like(!StrUtil.isBlankOrUndefined(qry.getProductName()), MallTrade::getProductName, qry.getProductName());
        queryWrapper.between(Objects.nonNull(qry.getFinishTimeStart()) && Objects.nonNull(qry.getFinishTimeEnd()), MallTrade::getFinishTime, qry.getFinishTimeStart(),qry.getFinishTimeEnd());
        queryWrapper.between(Objects.nonNull(qry.getCreateTimeStart()) && Objects.nonNull(qry.getCreateTimeEnd()), MallTrade::getCreateTime, qry.getCreateTimeStart(),qry.getCreateTimeEnd());
        queryWrapper.eq(MallTrade::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        queryWrapper.orderByDesc(MallTrade::getCreateTime);
        return queryWrapper;
    }
}
