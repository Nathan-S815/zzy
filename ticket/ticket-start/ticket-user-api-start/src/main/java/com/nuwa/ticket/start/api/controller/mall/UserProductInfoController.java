package com.nuwa.ticket.start.api.controller.mall;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.mall.query.MallProductQryExe;
import com.nuwa.app.ticket.command.mall.query.UserMallProductPageQryExe;
import com.nuwa.app.ticket.command.mall.query.UserMallProductQryExe;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.MallProductQry;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.UserMallProductPageQry;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.UserMallProductQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ROOT
 * @since 2020-11-20
 */
@Api(tags = {"文创产品-相关接口"})
@Slf4j
@RestController
@RequestMapping("/userProductInfoUser")
public class UserProductInfoController {

    @Autowired
    private UserMallProductPageQryExe userMallProductPageQryExe;

    @Autowired
    private UserMallProductQryExe userMallProductQryExe;

    @Autowired
    private MallProductService mallProductService;

    @Autowired
    private MallProductQryExe mallProductQryExe;

    @ApiOperation(value = "分页查询产品信息")
    @GetMapping(value = "/page")
    public SingleResponse<?> page(UserMallProductPageQry cmd, UserAware userAware) throws Exception {
        return userMallProductPageQryExe.execute(cmd);
    }


    @ApiOperation(value = "分页查询产品信息")
    @GetMapping(value = "/pageListByOtherWay")
    public SingleResponse<?> pageList(UserMallProductPageQry cmd, UserAware userAware) throws Exception {
        return mallProductService.pageListByOtherWay(cmd, userAware);
    }


    @ApiOperation(value = "根据id查询产品数据")
    @GetMapping(value = "/getById")
    public SingleResponse<?> getById(UserMallProductQry cmd, UserAware userAware) throws Exception {
        return userMallProductQryExe.execute(cmd);
    }


    @ApiOperation(value = "根据id查询数据")
    @GetMapping(value = "/getdetailById")
    public SingleResponse<?> getdetailById(MallProductQry cmd, UserAware userAware) throws Exception {
        return mallProductQryExe.execute(cmd);
    }

    @ApiOperation(value = "仿照良渚页面格式返回商品详情数据")
    @GetMapping(value = "/getProductsById")
    public SingleResponse<?> getProductsById(Long id) throws Exception {
        return mallProductService.getProductsById(id);
    }

    @ApiOperation(value = "仿照良渚页面格式返回商品详情数据")
    @GetMapping(value = "/getProductSkuStockById")
    public SingleResponse<?> getProductSkuStockById(Long id) throws Exception {
        return mallProductService.getProductSkuStockById(id);
    }

}
