package com.nuwa.app.zeus.command.mch;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.service.MerchantBiz;
import com.nuwa.app.zeus.service.dto.MerchantUserDTO;
import com.nuwa.client.zeus.dto.clientobject.mch.CreateMerchantCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.ModifyMerchantCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * CreateMerchantCmdExe 创建商户
 *
 * @author hy
 * @date 2021/6/2 16:23
 * @since 1.0.0
 */
@Slf4j
@Component
public class ModifyMerchantCmdExe extends AbstractCmdExe<ModifyMerchantCmd, SingleResponse> {

    @Autowired
    private MerchantService merchantService;

    @Override
    protected SingleResponse handle(ModifyMerchantCmd cmd) {
        try {
            Merchant merchant = merchantService.getById(cmd.getMchId());
            BeanUtil.copyProperties(cmd, merchant);
            merchantService.updateById(merchant);
            return SingleResponse.of(merchant);
        } catch (Exception e) {
            return ErrorEnum.DATA_FAIL.buildSuccess();
        }
    }
}
