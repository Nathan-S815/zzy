package com.nuwa.infrastructure.ticket.database.mall.param;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.UserMallProductPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProduct;
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
public class UserMallProductPageParam extends PageQry<MallProduct> {
    private static final long serialVersionUID = 1L;

    private UserMallProductPageQry qry;

    public UserMallProductPageParam(UserMallProductPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MallProduct> toQueryWrapper() {
        LambdaQueryWrapper<MallProduct> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(MallProduct.class,
                t -> !t.getColumn().endsWith("_editor")
                        && !t.getColumn().equalsIgnoreCase(MallProduct.MCH_ID)
                        && !t.getColumn().equalsIgnoreCase(MallProduct.PROVINCE_ID)
                        && !t.getColumn().equalsIgnoreCase(MallProduct.CITY_ID)
                        && !t.getColumn().equalsIgnoreCase(MallProduct.COUNTY_ID)
                        && !t.getColumn().equalsIgnoreCase(MallProduct.CREATE_BY)
                        && !t.getColumn().equalsIgnoreCase(MallProduct.UPDATE_BY)
                        && !t.getColumn().equalsIgnoreCase(MallProduct.CREATE_TIME)
                        && !t.getColumn().equalsIgnoreCase(MallProduct.UPDATE_TIME)
                        && !t.getColumn().equalsIgnoreCase(MallProduct.DELETE_FLAG)
        );
        queryWrapper.eq(Objects.nonNull(qry.getId()), MallProduct::getId, qry.getId());
        queryWrapper.eq(true, MallProduct::getAppId, qry.getUserAware().getMchAppId());
        queryWrapper.eq(true, MallProduct::getMchId, qry.getUserAware().getMchId());
        queryWrapper.like(!StrUtil.isBlankOrUndefined(qry.getProductName()), MallProduct::getProductName, qry.getProductName());
        queryWrapper.like(BeanUtil.isNotEmpty(qry.getClassificationFirstId()), MallProduct::getClassificationFirstId, qry.getClassificationFirstId());
        queryWrapper.eq(MallProduct::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        queryWrapper.eq(MallProduct::getPublishStatus, 1);
        switch (qry.getOrderType()){
            case 1:{
                queryWrapper.orderByAsc(MallProduct::getSales);
                break;
            }
            case 2:{
                queryWrapper.orderByDesc(MallProduct::getSales);
                break;
            }
            case 3:{
                queryWrapper.orderByAsc(MallProduct::getLowPrice);
                break;
            }
            case 4:{
                queryWrapper.orderByDesc(MallProduct::getLowPrice);
                break;
            }
            case 5:{
                queryWrapper.orderByDesc(MallProduct::getCreateTime);
                break;
            }
            default:{
                queryWrapper.orderByDesc(MallProduct::getCreateTime);
            }

        }
        return queryWrapper;
    }
}
