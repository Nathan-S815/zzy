package com.nuwa.infrastructure.ticket.database.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.UserMallProductPageQry;
import com.nuwa.client.zeus.api.order.MaterialClientI;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.SpringContextKit;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProduct;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProductSkuStock;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallStore;
import com.nuwa.infrastructure.ticket.database.mall.mapper.MallProductMapper;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductSkuStockService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallStoreService;
import com.nuwa.infrastructure.ticket.database.mall.service.impl.liangzhuway.pro.DataVO;
import com.nuwa.infrastructure.ticket.database.mall.service.impl.liangzhuway.pro.Stores;
import com.nuwa.infrastructure.ticket.database.mall.service.impl.liangzhuway.sku.SkuDataVO;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Slf4j
@Service
public class MallProductServiceImpl extends SuperServiceImpl<MallProductMapper, MallProduct> implements MallProductService {

    @Autowired
    private MallProductMapper mallProductMapper;
    @Autowired
    private MallStoreService storeService;
    @Autowired
    private MallProductSkuStockService mallProductSkuStockService;

    @Override
    public SingleResponse<?> pageListByOtherWay(UserMallProductPageQry cmd, UserAware userAware) {


        LambdaQueryWrapper<MallProduct> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(Objects.nonNull(cmd.getId()), MallProduct::getId, cmd.getId());
        queryWrapper.eq(Objects.nonNull(userAware.getMchAppId()), MallProduct::getAppId, userAware.getMchAppId());
        queryWrapper.eq(Objects.nonNull(userAware.getMchId()), MallProduct::getMchId, userAware.getMchId());
        queryWrapper.like(!StrUtil.isBlankOrUndefined(cmd.getProductName()), MallProduct::getProductName, cmd.getProductName());
        queryWrapper.like(BeanUtil.isNotEmpty(cmd.getClassificationFirstId()), MallProduct::getClassificationFirstId, cmd.getClassificationFirstId());
        queryWrapper.eq(MallProduct::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        queryWrapper.eq(MallProduct::getPublishStatus, 1);
        switch (cmd.getOrderType()) {
            case 1: {
                queryWrapper.orderByAsc(MallProduct::getSales);
                break;
            }
            case 2: {
                queryWrapper.orderByDesc(MallProduct::getSales);
                break;
            }
            case 3: {
                queryWrapper.orderByAsc(MallProduct::getLowPrice);
                break;
            }
            case 4: {
                queryWrapper.orderByDesc(MallProduct::getLowPrice);
                break;
            }
            case 5: {
                queryWrapper.orderByDesc(MallProduct::getCreateTime);
                break;
            }
            default: {
                queryWrapper.orderByDesc(MallProduct::getCreateTime);
            }

        }
        Page<MallProduct> page = new Page<>();
        page.setCurrent(cmd.getPage());
        page.setSize(cmd.getLimit());
        IPage<MallProduct> mallProductIPage =
                mallProductMapper.selectPage(page, queryWrapper);
        return SingleResponse.of(mallProductIPage);
    }


    @Override
    public SingleResponse<?> getProductsById(Long id) {
        MaterialClientI materialClient = SpringContextKit.getBean(MaterialClientI.class);
        MallProduct byId = this.getById(id);
        if (byId == null) {
            return SingleResponse.buildFailure("9522", "未发现该商品");
        }
        //轮播图 封面图  店铺信息
        DataVO dataVO = new DataVO();
        BeanUtils.copyProperties(byId, dataVO);

        dataVO.setCarouselImgs(byId.getCarouselImgs());
        dataVO.setCoverImg(byId.getCoverImg());
        //设置门户
        List<Stores> stores = getStoreList(byId);
        dataVO.setStores(stores);

        return SingleResponse.of(dataVO);
    }

    @Override
    public SingleResponse<?> getProductSkuStockById(Long id) {
        MallProductSkuStock skuStock = mallProductSkuStockService.getById(id);
        SkuDataVO skuDataVO = new SkuDataVO();
        BeanUtils.copyProperties(skuStock, skuDataVO);
        skuDataVO.setSkuStockImg(skuStock.getSkuStockImg());
        return SingleResponse.of(skuDataVO);
    }

    private List<Stores> getStoreList(MallProduct product) {
        List<Stores> list = new ArrayList<>();
        Arrays.asList(product.getStoreId().split(",")).forEach(x -> {

            MallStore mallStore = storeService.getById(x);

            Stores stores = new Stores();
            BeanUtils.copyProperties(mallStore, stores);
            list.add(stores);

        });
        return list;
    }


    private <T> List<T> getMaterListByFanxing(String carouselImgs, MaterialClientI materialClient, Class<T> aclass) {

       /* List<MaterialJson.MaterialVO> materialsByIdList = getMaterialsByIdList(carouselImgs, materialClient);

        List<T> collect = materialsByIdList.stream().map(
                x -> {
                    T dataT = null;
                    try {
                        dataT = aclass.newInstance();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }

                    BeanUtils.copyProperties(x, dataT);

                    return dataT;
                }
        ).collect(Collectors.toList());

        return collect;*/
        return new ArrayList<>();
    }


//    private List<CarouselImgs> getCcarouselImgsListByCar(String carouselImgs, MaterialClientI materialClient) {
//
//        List<MaterialJson.MaterialVO> materialsByIdList = getMaterialsByIdList(carouselImgs, materialClient);
//
//
//        for (MaterialJson.MaterialVO materialVO : materialsByIdList) {
//            String url = materialVO.getUrl();
//            System.out.println(url);
//
//        }
//
//
//        List<CarouselImgs> collect = materialsByIdList.stream().map(
//                x -> {
//                    String url = x.getUrl();
//                    System.out.println(url);
//                    CarouselImgs carouselImgs1 = new CarouselImgs();
//                    BeanUtils.copyProperties(x, carouselImgs1);
//                    return carouselImgs1;
//                }
//        ).collect(Collectors.toList());
//
//        return collect;
//    }

    /*List<MaterialJson.MaterialVO> getMaterialsByIdList(String IdList, MaterialClientI materialClient) {
        List<Long> coverImgIdList = Arrays.stream(IdList.split(","))
                .map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        SingleResponse materialClientResult = materialClient.getMaterialByIds(coverImgIdList);


        //转换成 List<MaterialJson.MaterialVO> 格式
//        List<MaterialJson.MaterialVO> data = (List<MaterialJson.MaterialVO>) materialClientResult.getData();

        Object data = materialClientResult.getData();
        if (data instanceof List) {
            List<Map<Object, Object>> dataList = (List) data;

            List<MaterialJson.MaterialVO> result = new ArrayList<>();
            for (Map<Object, Object> objectObjectMap : dataList) {

                Material material = new Material();
                material.setUrl(objectObjectMap.get("url") + "");
                material.setFileType(Integer.valueOf(objectObjectMap.get("fileType") + ""));
                material.setId(Long.valueOf(objectObjectMap.get("id") + ""));
                MaterialJson.MaterialVO materialVO = MaterialJson.MaterialVO.toVO(material);
                result.add(materialVO);

            }
            return result;

        }*/
}