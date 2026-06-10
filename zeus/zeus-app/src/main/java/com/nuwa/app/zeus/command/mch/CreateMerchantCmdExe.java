package com.nuwa.app.zeus.command.mch;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.service.MerchantBiz;
import com.nuwa.app.zeus.service.dto.MerchantUserDTO;
import com.nuwa.client.zeus.dto.clientobject.mch.CreateMerchantCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.enums.AuditStatusEnum;
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
public class CreateMerchantCmdExe extends AbstractCmdExe<CreateMerchantCmd, SingleResponse> {

    @Autowired
    private MerchantBiz merchantBiz;

    @Autowired
    private BaseUserService baseUserService;

    @Override
    protected SingleResponse handle(CreateMerchantCmd cmd) {

        Integer count = baseUserService.lambdaQuery()
                .eq(BaseUser::getUsername, cmd.getUsername())
                .or().eq(BaseUser::getMobilePhone, cmd.getUsername())
                .or().eq(BaseUser::getUsername, cmd.getContentPhone())
                .or().eq(BaseUser::getMobilePhone, cmd.getContentPhone())
                .count();
        if (count>0){
            return ErrorEnum.MOBILE_OR_USERNAME_IS_EXIST.buildFailure();
        }

        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(cmd, merchant);
        merchant.setCreateTime(new Date());
        MerchantUserDTO userDTO = new MerchantUserDTO();
        userDTO.setUserName(cmd.getUsername());
        userDTO.setPassword(cmd.getPassword());
        merchant.setStatus(1);
        merchant.setAuditStatus(AuditStatusEnum.PASS.getCode());
        Boolean flag = merchantBiz.createMerchant(merchant, userDTO);
        if (flag) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }
}
