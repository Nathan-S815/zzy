package com.nuwa.infrastructure.zeus.third.sms;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.nuwa.infrastructure.zeus.third.ChannelResult;
import com.nuwa.infrastructure.zeus.third.sms.req.SendSmsCodeReq;
import com.nuwa.infrastructure.zeus.third.sms.resp.SendSmsCodeResp;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * TongMinHttpSender
 *
 * @author hy
 * @date 2021/3/23 13:47
 * @since 1.0.0
 */
@Slf4j
public class ZhuTongSmsHttpSender {

   public static final String gatewayUrl = "https://api.mix2.zthysms.com/v2/sendSms";

    //商户编号：
    public static final String account = "jyzyhy";

    public static final String password = "<REDACTED>";


    public static ChannelResult<SendSmsCodeResp> sendSmsCode(SendSmsCodeReq request) {
        String url = gatewayUrl;
        ChannelResult<SendSmsCodeResp> channelResult = new ChannelResult<>();
        String method = "sendSmsCode";
        channelResult.setUrl(url);
        channelResult.setMethod(method);
        Map<String, Object> params = new HashMap<>();
        try {
            long tKey = System.currentTimeMillis() / 1000;
            //账号
            params.put("username", account);
            //密码
            params.put("password", SecureUtil.md5(SecureUtil.md5(password) + tKey));
            //tKey
            params.put("tKey", tKey + "");
            //手机号
            params.put("mobile", request.getMobile());
            //内容
            params.put("content", request.getContent());

            log.info(">>>> request paramMap : [{}]", JSONUtil.toJsonPrettyStr(params));
        } catch (Exception ex) {
            log.error("buildParamsException request: [{}] 异常", JSONUtil.toJsonPrettyStr(request), ex);
            return channelResult.buildParamsException();
        }

        int status;
        String result;
        StopWatch stopWatch = new StopWatch(method);
        try {
            String auth = account + ":" + password;
            byte[] encodedAuth = Base64.decode(auth);
            String authorization = "Basic " + new String(encodedAuth);
            stopWatch.start();
            HttpResponse execute = HttpRequest.post(url)
                    .body(JSONUtil.toJsonStr(params))
                    .header("Authorization", authorization)
                    .setConnectionTimeout(3000)
                    .setReadTimeout(10000)
                    .execute();
            status = execute.getStatus();
            result = execute.body();
            stopWatch.stop();
            if (!execute.isOk()) {
                log.error("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}] 异常.", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
                return channelResult.channelException(status + "", "500 内部服务器错误", JSONUtil.toJsonStr(method), result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout 异常.", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method));
            return channelResult.timeoutException(JSONUtil.toJsonStr(method), "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException("没有返回响应参数", result);
            }
            log.info("<<<< response: [{}]", result);

            SendSmsCodeResp respBean = JSONUtil.toBean(result, SendSmsCodeResp.class);
            if (Objects.nonNull(respBean) && "200".equalsIgnoreCase(respBean.getCode())) {
                return channelResult.success(respBean, JSONUtil.toJsonStr(params), result);
            } else {
                return channelResult.channelException(respBean.getCode(), respBean.getMsg(), JSONUtil.toJsonStr(params), result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            log.error("parseRespException 异常", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static void main(String[] args) {
        SendSmsCodeReq req = new SendSmsCodeReq();
        req.setMobile("15868118817");
        req.setContent("【中智游】您的验证码：6950，5分钟有效，请勿把验证码告知他人。");
        ZhuTongSmsHttpSender.sendSmsCode(req);
    }
}
