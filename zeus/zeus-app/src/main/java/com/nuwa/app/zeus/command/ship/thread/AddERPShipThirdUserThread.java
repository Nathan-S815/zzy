package com.nuwa.app.zeus.command.ship.thread;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
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
import java.util.concurrent.Callable;

@Data
@Component
public class AddERPShipThirdUserThread implements Callable {

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
            if(StrUtil.hasBlank(merchantAppUrl.getLoginSubmitUrl())){
                return null;
            }
            String url = merchantAppUrl.getLoginSubmitUrl();
            boolean flag=false;
            HashMap<String, Object> map = new HashMap<>();
            map.put("username",cmd.getUsername());
            map.put("password",cmd.getPassword());
            map.put("companycode",merchantAppUrl.getScenicCode());
            String result = HttpRequest.post(url+"/home/Loginverify").body(JSONUtil.toJsonStr(map)).execute().body();
            System.out.println("result = " + result);
            JSONObject erp = JSONUtil.parseObj(result);
            if(erp.getStr("IsLogin").equals("true")){
                flag=true;
            }
            if(flag) {
                ShipThirdUser shipThirdUser = new ShipThirdUser();
                BeanUtils.copyProperties(cmd, shipThirdUser);
                ShipThirdUser one = shipThirdUserService.lambdaQuery()
                        .eq(ShipThirdUser::getMerchantId,  cmd.getMerchantId())
                        .eq(ShipThirdUser::getMerchantAppId, cmd.getMerchantAppId())
                        .eq(ShipThirdUser::getUsername, cmd.getUsername()).one();
                if(one==null) {
                    boolean save = shipThirdUserService.save(shipThirdUser);
                    if(save){
                        String shipUrl = url + "/Home/SASSLogin?token=" + erp.getStr("Token");
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
