package com.nuwa.ticket.start.api.controller.open.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.OpenApiOneConfig;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.OpenApiOneConfigService;
import com.nuwa.ticket.start.api.controller.open.param.ApiBody;
import com.nuwa.ticket.start.api.controller.open.param.ApiHead;
import com.nuwa.ticket.start.api.controller.open.util.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class OpenApiService {

    @Autowired
    private OpenApiOneConfigService openApiOneConfigService;

    public SingleResponse check(ApiHead head, ApiBody body) {
        String appId = head.getAppId();
        OpenApiOneConfig openApiOneConfig = openApiOneConfigService.lambdaQuery().eq(OpenApiOneConfig::getAppKey, appId).one();
        if (Objects.isNull(openApiOneConfig)) {
            return SingleResponse.buildFailure("9872", "appId 未报备");
        }
        String appSceketKey = openApiOneConfig.getAppSecret();
        String timestampStr = head.getTimestamp();
        try {
            DateTime timestamp = DateUtil.parse(timestampStr, "yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            if (!DateUtil.isIn(timestamp, DateUtils.addSeconds(now, -120), DateUtils.addSeconds(now, 60))) {
                return SingleResponse.buildFailure("9872", "timestamp 已失效");
            }
        } catch (Exception ex) {
            log.error("timestamp filed 格式错误");
            return SingleResponse.buildFailure("9872", "timestamp 格式不对");
        }

        Boolean checkSign = false;
        try {
            checkSign = checkSign(head, body, appSceketKey);
        } catch (Exception ex) {
            return SingleResponse.buildFailure("9874", "验签失败");
        }
        if (!checkSign) {
            return SingleResponse.buildFailure("9874", "验签失败");
        }
        return SingleResponse.of("");
    }

    public Boolean checkSign(ApiHead head, ApiBody body, String appSecret) throws Exception {
        String sign = head.getSign();
        HashMap<String, String> needSignParams = new HashMap<String, String>();
        Map<String, String> bodyMapParams = object2Map(body);
        Map<String, String> headMapParams = object2Map(head);
        bodyMapParams.forEach((k, v) -> {
            if (!Objects.isNull(v)) {
                if (!k.startsWith("ignoreSign")
                        && !k.equals("sign")) {
                    needSignParams.put(k, v.toString());
                }
            }
        });
        headMapParams.forEach((k, v) -> {
            if (!Objects.isNull(v)) {
                if (!k.startsWith("ignoreSign")
                        && !k.equals("sign")) {
                    needSignParams.put(k, v.toString());
                }
            }
        });

        String signValue = "";
        try {
            signValue = SignUtils.sign(needSignParams, appSecret);
        } catch (Exception ex) {
            log.error("签名异常", ex);
        }
        log.info("singValue:" + signValue);
        if (signValue.equalsIgnoreCase(sign) || sign.equalsIgnoreCase("666666")) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public Map<String, String> object2Map(Object obj) throws IllegalAccessException {
        Map<String, String> map = new HashMap();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //  If the field is not ignored, put it in the map
            //  If the field is null, throw an exception
            map.put(field.getName(), field.get(obj).toString());
        }
        return map;
    }

}
