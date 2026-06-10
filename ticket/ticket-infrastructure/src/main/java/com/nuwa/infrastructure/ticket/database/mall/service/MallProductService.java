package com.nuwa.infrastructure.ticket.database.mall.service;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.UserMallProductPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.supper.SuperService;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProduct;

/**
 *  服务类
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
public interface MallProductService extends SuperService<MallProduct> {


    /***
     * 良渚页面接口返回
     * @param cmd
     * @param userAware
     * @return
     */
    SingleResponse<?> pageListByOtherWay(UserMallProductPageQry cmd, UserAware userAware);
    /***
     * 良渚页面接口返回
     * @param id
     * @return
     */
    SingleResponse<?> getProductsById(Long id);

    SingleResponse<?> getProductSkuStockById(Long id);

}
