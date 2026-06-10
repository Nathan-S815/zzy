package com.zzy.api.service.reportbase;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.reportbase.ReportBaseRestaurant;
import com.zzy.db.entity.reportbase.ReportBaseShopping;

/**
 * <p>
 * 购物上报基础信息表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface IReportBaseShoppingService extends IService<ReportBaseShopping> {

    PageInfo<ReportBaseShopping> getReportBaseShoppingList(Integer pagNumber, Integer pagSize, String keyword);
}
