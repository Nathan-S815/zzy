package com.nuwa.app.zeus.command.ship.thread;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.ship.AddShipThirdUserCmd;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppUrl;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppUrlService;
import com.nuwa.infrastructure.zeus.database.ship.entity.ShipThirdUser;
import com.nuwa.infrastructure.zeus.database.ship.service.ShipThirdUserService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Data
@Component
public class AddB2BShipThirdUserThread implements Callable {

    private AddShipThirdUserCmd cmd;

    @Autowired
    private MerchantAppUrlService merchantAppUrlService;

    @Autowired
    private ShipThirdUserService shipThirdUserService;

    @Override
    public String call() throws Exception {
        try {
            MerchantAppUrl merchantAppUrl = merchantAppUrlService.lambdaQuery().eq(MerchantAppUrl::getMchId, cmd.getMerchantId())
                    .eq(MerchantAppUrl::getAppId, cmd.getMerchantAppId()).one();
            if (StrUtil.hasBlank(merchantAppUrl.getLoginSubmitUrl())) {
                return null;
            }
            String url = merchantAppUrl.getLoginSubmitUrl();
            boolean flag = false;
            Map<String, Object> map = new HashMap<>();
            map.put("userName", cmd.getUsername());
            map.put("password", cmd.getPassword());
            String s = HttpUtil.get(url + "/Home/LoginVerify", map);
            if (s.equals("True")) {
                flag = true;
            }
            if (flag) {
                ShipThirdUser shipThirdUser = new ShipThirdUser();
                BeanUtils.copyProperties(cmd, shipThirdUser);
                ShipThirdUser one = shipThirdUserService.lambdaQuery()
                        .eq(ShipThirdUser::getMerchantId, cmd.getMerchantId())
                        .eq(ShipThirdUser::getMerchantAppId, cmd.getMerchantAppId())
                        .eq(ShipThirdUser::getUsername, cmd.getUsername()).one();
                if (one == null) {
                    boolean save = shipThirdUserService.save(shipThirdUser);
                    if (save) {
                        String shipUrl = url + "/Home/SASSLogin?userName=" + shipThirdUser.getUsername() + "&password=<REDACTED>
                        return shipUrl;
                    }
                }
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
