package com.nuwa.app.zeus.command.mch.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.app.zeus.vo.AppTree;
import com.nuwa.app.zeus.vo.TreeNode;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetAppTreeQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.app.entity.AppDependent;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppDependentService;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.param.MerchantPageParam;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import com.sun.jersey.spi.inject.PerRequestTypeInjectableProvider;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GetAppTreeQryExe
 *
 * @author hy
 * @date 2021/6/3 18:14
 * @since 1.0.0
 */
@Slf4j
@Component
public class MerchantPageQryExe extends AbstractQryExe<MerchantPageQry, SingleResponse<IPage<MerchantPageQryExe.MerchantPageVO>>> {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private BaseUserService baseUserService;

    @Override
    protected SingleResponse<IPage<MerchantPageVO>> handle(MerchantPageQry cmd) {
        IPage<MerchantPageVO> merchantPageVOIPage = merchantService.paginateAndConvert(new MerchantPageParam(cmd), MerchantPageVO::toVo);
        merchantPageVOIPage.getRecords().forEach(x->{
            BaseUser user = baseUserService.lambdaQuery().eq(BaseUser::getTenantId, x.getMchId()).eq(BaseUser::getType, 1).one();
            x.setUserName(user.getUsername());
        });
        return SingleResponse.of(merchantPageVOIPage);
    }

    @Data
    public static class MerchantPageVO{
        @ApiModelProperty("商户Id")
        private Long mchId;
        @ApiModelProperty("商户名称")
        private String mchName;
        @ApiModelProperty("联系人姓名")
        private String contentName;
        @ApiModelProperty("所在地省")
        private String province;
        @ApiModelProperty("所在地市")
        private String city;
        @ApiModelProperty("所在地区")
        private String county;
        @ApiModelProperty("具体地址")
        private String address;
        @ApiModelProperty("联系电话")
        private String contentPhone;
        @ApiModelProperty("审核状态 0:等待审核 1:审核通过 2:审核失败")
        private Integer auditStatus;
        @ApiModelProperty("商户状态 0:停用, 1:启用 ")
        private Integer status;
        @ApiModelProperty("用户名")
        private String userName;

        public static MerchantPageVO toVo(Merchant merchant){
            MerchantPageVO vo = new MerchantPageVO();
            BeanUtil.copyProperties(merchant,vo);
            return vo;
        }
    }
}
