package com.nuwa.ticket.start.api.controller.supplier;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.supplier.qry.SupplierConfPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotSupplier;
import com.nuwa.infrastructure.ticket.database.product.service.MerchantSupplierConfigService;
import com.nuwa.infrastructure.ticket.database.product.service.ScenicspotSupplierService;
import com.nuwa.infrastructure.ticket.database.supplier.param.MchSupplierConfPageParam;
import com.nuwa.ticket.start.api.controller.supplier.param.AddSupplierConfParam;
import com.nuwa.ticket.start.api.controller.supplier.vo.MchSupplierConfPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("supplier")
@Api(tags = {"供应商渠道管理接口"})
public class SupplierController {

    @Autowired
    private MerchantSupplierConfigService merchantSupplierConfigService;

    @Autowired
    private ScenicspotSupplierService scenicspotSupplierService;

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> add(@RequestBody AddSupplierConfParam param, UserAware userAware) {
        MerchantSupplierConfig merchantSupplierConfig = new MerchantSupplierConfig();
        BeanUtils.copyProperties(param, merchantSupplierConfig);
        merchantSupplierConfig.setMerchantId(userAware.getMchId());
        merchantSupplierConfig.setStatus(1);
        merchantSupplierConfig.setCreateTime(new Date());
        merchantSupplierConfig.insert();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "修改")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> update(@PathVariable("id") Long id, @RequestBody AddSupplierConfParam param, UserAware userAware) {
        MerchantSupplierConfig merchantSupplierConfig = merchantSupplierConfigService.getById(id);
        BeanUtils.copyProperties(param, merchantSupplierConfig);
        merchantSupplierConfig.updateById();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "启用")
    @RequestMapping(value = "/{id}/on", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> on(@PathVariable("id") Long id, UserAware userAware) {
        merchantSupplierConfigService.lambdaUpdate()
                .set(MerchantSupplierConfig::getStatus, 1)
                .eq(MerchantSupplierConfig::getId, id)
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "关闭")
    @RequestMapping(value = "/{id}/off", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> off(@PathVariable("id") Long id, UserAware userAware) {
        merchantSupplierConfigService.lambdaUpdate()
                .set(MerchantSupplierConfig::getStatus, 0)
                .eq(MerchantSupplierConfig::getId, id)
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MchSupplierConfPageVO>> page(@Valid SupplierConfPageQry pageQry, UserAware userAware) {
        MchSupplierConfPageParam pageParam = new MchSupplierConfPageParam(pageQry);
        IPage<MchSupplierConfPageVO> pageData = merchantSupplierConfigService.paginateAndConvert(pageParam, MchSupplierConfPageVO::toVO);
        pageData.getRecords().forEach(x->{
            ScenicspotSupplier scenicspotSupplier = scenicspotSupplierService.getById(x.getSupplierId());
            if (Objects.nonNull(scenicspotSupplier)) {
                x.setSupplierName(scenicspotSupplier.getName());
            }
        });
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<MchSupplierConfPageVO> detail(@PathVariable("id") Integer id, UserAware userAware) {
        MerchantSupplierConfig merchantSupplierConfig = merchantSupplierConfigService.getById(id);
        MchSupplierConfPageVO mchSupplierConfPageVO = MchSupplierConfPageVO.toVO(merchantSupplierConfig);
        ScenicspotSupplier scenicspotSupplier = scenicspotSupplierService.getById(merchantSupplierConfig.getSupplierId());
        if (Objects.nonNull(scenicspotSupplier)) {
            mchSupplierConfPageVO.setSupplierName(scenicspotSupplier.getName());
        }
        return SingleResponse.of(mchSupplierConfPageVO);
    }

    @ApiOperation(value = "获取供应商类别列表")
    @RequestMapping(value = "/listSupplierType", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ScenicspotSupplier>> listSupplierType() {
        List<ScenicspotSupplier> list = scenicspotSupplierService.lambdaQuery().list();
        return SingleResponse.of(list);
    }
}
