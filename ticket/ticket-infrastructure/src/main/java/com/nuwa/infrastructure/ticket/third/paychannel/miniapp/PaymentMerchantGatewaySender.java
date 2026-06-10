package com.nuwa.infrastructure.ticket.third.paychannel.miniapp;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.req.*;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * ???????
 * ZzyPayGateWaySender
 *
 * @author hy
 * @date 2020/10/29 13:33
 * @since 1.0.0
 */
@Slf4j
public class PaymentMerchantGatewaySender {

    public static ChannelResult<ModifyMerchantResp> modifyMerchant(ModifyMerchantReq req, String gatewayUrl) {
        String url = gatewayUrl + "/api/paychannel/merchant/modify";
        ChannelResult<ModifyMerchantResp> channelResult = new ChannelResult<>();
        String method = "alipayMerchantModify";
        channelResult.setUrl(url);
        channelResult.setMethod(method);
        String requestStr = null;
        try {
            requestStr = JSONUtil.toJsonStr(req);
            channelResult.setRequestStr(requestStr);
            log.info(">>>> request paramMap : [{}]", requestStr);
        } catch (Exception ex) {
            log.error("buildParamsException request: [{}] ??", requestStr, ex);
            return channelResult.buildParamsException();
        }

        int status;
        String result;
        StopWatch stopWatch = new StopWatch(method);
        try {
            stopWatch.start();
            HttpResponse execute = HttpRequest.post(url)
                    .body(requestStr)
                    .setConnectionTimeout(3000)
                    .setReadTimeout(10000)
                    .execute();
            status = execute.getStatus();
            result = execute.body();
            stopWatch.stop();
            if (!execute.isOk()) {
                log.error("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}] ??.", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
                return channelResult.channelException(status + "", "500 服务器内部异常", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout ", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method), ex);
            return channelResult.timeoutException(method, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException("解析异常", result);
            }
            log.info("<<<< response: [{}]", result);
            JSONObject retJson = JSONUtil.parseObj(result, true);
            if ("0000".equalsIgnoreCase(retJson.getStr("code"))) {
                ModifyMerchantResp data = retJson.getBean("data", ModifyMerchantResp.class);
                return channelResult.success(data, JSONUtil.toJsonStr(req), result);
            } else {
                return channelResult.channelException(retJson.getStr("code"), retJson.getStr("msg"), requestStr, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            log.error("parseRespException 解析报文异常", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static ChannelResult<CreateMerchantResp> createMerchant(CreateMerchantReq req, String gatewayUrl) {
        String url = gatewayUrl + "/api/paychannel/merchant/create";
        ChannelResult<CreateMerchantResp> channelResult = new ChannelResult<>();
        String method = "paychannelMerchantCreate";
        channelResult.setUrl(url);
        channelResult.setMethod(method);
        String requestStr = null;
        try {
            requestStr = JSONUtil.toJsonStr(req);
            channelResult.setRequestStr(requestStr);
            log.info(">>>> request paramMap : [{}]", requestStr);
        } catch (Exception ex) {
            log.error("buildParamsException request: [{}] ??", requestStr, ex);
            return channelResult.buildParamsException();
        }

        int status;
        String result;
        StopWatch stopWatch = new StopWatch(method);
        try {
            stopWatch.start();
            HttpResponse execute = HttpRequest.post(url)
                    .body(requestStr)
                    .setConnectionTimeout(3000)
                    .setReadTimeout(10000)
                    .execute();
            status = execute.getStatus();
            result = execute.body();
            stopWatch.stop();
            if (!execute.isOk()) {
                log.error("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}] ??.", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
                return channelResult.channelException(status + "", "500 服务器内部异常", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout ", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method), ex);
            return channelResult.timeoutException(method, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException("解析异常", result);
            }
            log.info("<<<< response: [{}]", result);
            JSONObject retJson = JSONUtil.parseObj(result, true);
            if ("0000".equalsIgnoreCase(retJson.getStr("code"))) {
                CreateMerchantResp data = retJson.getBean("data", CreateMerchantResp.class);
                return channelResult.success(data, JSONUtil.toJsonStr(req), result);
            } else {
                return channelResult.channelException(retJson.getStr("code"), retJson.getStr("msg"), requestStr, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            log.error("parseRespException 解析报文异常", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static ChannelResult<MerchantParamPublishResp> modifySecretKey(MerchantModifySecretKeyReq req, String gatewayUrl) {
        String url = gatewayUrl + "/api/paychannel/merchant/modifySecretKey";
        ChannelResult<MerchantParamPublishResp> channelResult = new ChannelResult<>();
        String method = "modifySecretKey";
        channelResult.setUrl(url);
        channelResult.setMethod(method);
        String requestStr = null;
        try {
            requestStr = JSONUtil.toJsonStr(req);
            channelResult.setRequestStr(requestStr);
            log.info(">>>> request paramMap : [{}]", requestStr);
        } catch (Exception ex) {
            log.error("buildParamsException request: [{}] 构建参数异常", requestStr, ex);
            return channelResult.buildParamsException();
        }

        int status;
        String result;
        StopWatch stopWatch = new StopWatch(method);
        try {
            stopWatch.start();
            HttpResponse execute = HttpRequest.post(url)
                    .body(requestStr)
                    .setConnectionTimeout(3000)
                    .setReadTimeout(10000)
                    .execute();
            status = execute.getStatus();
            result = execute.body();
            stopWatch.stop();
            if (!execute.isOk()) {
                log.error("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
                return channelResult.channelException(status + "", "500 服务器内部异常", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout.", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method), ex);
            return channelResult.timeoutException(method, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException("解析异常", result);
            }
            log.info("<<<< response: [{}]", result);
            JSONObject retJson = JSONUtil.parseObj(result, true);
            if ("0000".equalsIgnoreCase(retJson.getStr("code"))) {
                MerchantParamPublishResp data = retJson.getBean("data", MerchantParamPublishResp.class);
                return channelResult.success(data, JSONUtil.toJsonStr(req), result);
            } else {
                return channelResult.channelException(retJson.getStr("code"), retJson.getStr("msg"), requestStr, result, stopWatch.getTotalTimeMillis());

            }
        } catch (Exception ex) {
            log.error("parseRespException 解析报文异常", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static ChannelResult<MerchantParamPublishResp> publish(MerchantPublishReq req, String gatewayUrl) {
        String url = gatewayUrl + "/api/paychannel/merchant/on";
        ChannelResult<MerchantParamPublishResp> channelResult = new ChannelResult<>();
        String method = "alipayMerchantCreate";
        channelResult.setUrl(url);
        channelResult.setMethod(method);
        String requestStr = null;
        try {
            requestStr = JSONUtil.toJsonStr(req);
            channelResult.setRequestStr(requestStr);
            log.info(">>>> request paramMap : [{}]", requestStr);
        } catch (Exception ex) {
            log.error("buildParamsException request: [{}] 构建参数异常", requestStr, ex);
            return channelResult.buildParamsException();
        }

        int status;
        String result;
        StopWatch stopWatch = new StopWatch(method);
        try {
            stopWatch.start();
            HttpResponse execute = HttpRequest.post(url)
                    .body(requestStr)
                    .setConnectionTimeout(3000)
                    .setReadTimeout(10000)
                    .execute();
            status = execute.getStatus();
            result = execute.body();
            stopWatch.stop();
            if (!execute.isOk()) {
                log.error("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
                return channelResult.channelException(status + "", "500 服务器内部异常", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout.", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method), ex);
            return channelResult.timeoutException(method, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException("解析异常", result);
            }
            log.info("<<<< response: [{}]", result);
            JSONObject retJson = JSONUtil.parseObj(result, true);
            if ("0000".equalsIgnoreCase(retJson.getStr("code"))) {
                MerchantParamPublishResp data = retJson.getBean("data", MerchantParamPublishResp.class);
                return channelResult.success(data, JSONUtil.toJsonStr(req), result);
            } else {
                return channelResult.channelException(retJson.getStr("code"), retJson.getStr("msg"), requestStr, result, stopWatch.getTotalTimeMillis());

            }
        } catch (Exception ex) {
            log.error("parseRespException 解析报文异常", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static void main(String[] args) {
        MerchantPublishReq createReq = new MerchantPublishReq();
        RequestHead head = new RequestHead("20210621", new Date());
        createReq.setHead(head);
        MerchantPublishReq.Body body = new MerchantPublishReq.Body();
        body.setGatewayMchId("2089496209");
        createReq.setBody(body);
        String sign = createReq.buildSign("werYUYT123");
        head.setSign(sign);
        PaymentMerchantGatewaySender.publish(createReq, "http://192.168.110.126:8888");
    }
}
