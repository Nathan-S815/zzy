package com.nuwa.infrastructure.ticket.database.mall.param;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.UserMallTradePageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import com.nuwa.infrastructure.ticket.enums.PaymentStatusEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
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
public class UserMallTradePageParam extends PageQry<MallTrade> {
    private static final long serialVersionUID = 1L;

    private UserMallTradePageQry qry;

    public UserMallTradePageParam(UserMallTradePageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MallTrade> toQueryWrapper() {
        LambdaQueryWrapper<MallTrade> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(MallTrade.class,
                t -> !t.getColumn().endsWith("_editor")
                        && !t.getColumn().equalsIgnoreCase(MallTrade.PROVINCE_ID)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.CITY_ID)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.COUNTY_ID)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.CREATE_BY)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.UPDATE_BY)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.UPDATE_TIME)
                        && !t.getColumn().equalsIgnoreCase(MallTrade.DELETE_FLAG)
        );
        queryWrapper.eq(MallTrade::getMchId, qry.getUserAware().getMchId());
        queryWrapper.eq(Objects.nonNull(qry.getClassificationFirstId()), MallTrade::getClassificationFirstId, qry.getClassificationFirstId());
        queryWrapper.eq(Objects.nonNull(qry.getUserAware().getUserId()), MallTrade::getMemberId, qry.getUserAware().getUserId());
        queryWrapper.eq(!StrUtil.isBlankOrUndefined(qry.getTradeNo()), MallTrade::getTradeNo, qry.getTradeNo());
        queryWrapper.like(!StrUtil.isBlankOrUndefined(qry.getProductName()), MallTrade::getProductName, qry.getProductName());
        queryWrapper.eq(MallTrade::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        if (Objects.nonNull(qry.getOrderStatus())) {
            switch (qry.getOrderStatus()) {
                case 10: {
                    queryWrapper.eq(MallTrade::getOrderStatus, PaymentStatusEnum.WAIT_FOR_PAY.getCode());
                    break;
                }
                case 11: {
                    queryWrapper.eq(MallTrade::getOrderStatus, PaymentStatusEnum.WAIT_FOR_SEND.getCode());
                    break;
                }
                case 12: {
                    queryWrapper.eq(MallTrade::getOrderStatus, PaymentStatusEnum.WAIT_FOR_SIGN.getCode());
                    break;
                }
                case 13: {
                    queryWrapper.and(true, x -> x.eq(MallTrade::getOrderStatus, PaymentStatusEnum.REFUNDING.getCode())
                            .or().eq(MallTrade::getOrderStatus, PaymentStatusEnum.CANCLED_ORDER.getCode())
                            .or().eq(MallTrade::getOrderStatus, PaymentStatusEnum.REFUNDED.getCode())
                            .or().eq(MallTrade::getOrderStatus, PaymentStatusEnum.FINISH.getCode()));
                    break;
                }
                default:
                    break;
            }
        }
        queryWrapper.orderByDesc(MallTrade::getCreateTime);
        return queryWrapper;
    }

    public static void main(String[] args) {
        String yyMMddHHmmssSSS = DateUtil.format(new Date(), "yyMMddHHmmssSSS");
        System.out.println("101" + yyMMddHHmmssSSS+ RandomUtil.randomNumbers(3));
        //10000000000021000000000002
        //221008145631
    }
}
