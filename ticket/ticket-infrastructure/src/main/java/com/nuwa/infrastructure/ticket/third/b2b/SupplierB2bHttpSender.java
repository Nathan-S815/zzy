package com.nuwa.infrastructure.ticket.third.b2b;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.infrastructure.ticket.third.b2b.model.req.*;
import com.nuwa.infrastructure.ticket.third.b2b.model.B2bConfigModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.resp.B2bCreateOrderRespModel;
import com.nuwa.infrastructure.ticket.third.b2b.req.B2bCancelReq;
import com.nuwa.infrastructure.ticket.third.b2b.req.B2bCreateOrderReq;
import com.nuwa.infrastructure.ticket.third.b2b.req.B2bPaymentReq;
import com.nuwa.infrastructure.ticket.third.b2b.req.B2bRefundReq;
import com.nuwa.infrastructure.ticket.third.b2b.resp.B2bCancelResp;
import com.nuwa.infrastructure.ticket.third.b2b.resp.B2bCreateOrderResp;
import com.nuwa.infrastructure.ticket.third.b2b.resp.B2bPaymentResp;
import com.nuwa.infrastructure.ticket.third.b2b.resp.B2bRefundResp;
import com.nuwa.infrastructure.ticket.third.b2b.util.JsonKit;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jcajce.provider.util.SecretKeyUtil;
import org.bouncycastle.math.ec.ECPoint;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 抖音支付
 *
 * @author hy
 */
@Slf4j
public class SupplierB2bHttpSender {

    public static ChannelResult<B2bCreateOrderResp> createOrder(B2bCreateOrderReq request) {
        B2bConfigModel config = request.getConfig();
        String path = "/order/create";
        String url = StrUtil.removeSuffix(config.getApiUrl(), "/") + path;
        B2bCreateOrderReqModel model = request.getModel();
        request.getModel().setPartnerId(config.getPartnerId());
        ChannelResult<B2bCreateOrderResp> channelResult = new ChannelResult<>();
        String method = "createOrder";
        StopWatch stopWatch = new StopWatch(method);
        stopWatch.start();
        channelResult.setUrl(url);
        channelResult.setMethod(method);
        String requestStr;
        Map<String, Object> paramMap = new HashMap<>(16);
        try {
            requestStr = JSONUtil.toJsonStr(model);
            channelResult.setRequestStr(requestStr);
            log.info(">>>> request paramMap : [{}]", requestStr);
        } catch (Exception ex) {
            log.error("buildParamsException request: [{}] 异常", JSONUtil.toJsonPrettyStr(request), ex);
            requestStr = JSONUtil.toJsonStr(paramMap);
            channelResult.setRequestStr(requestStr);
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            return channelResult.buildParamsException();
        }

        int status;
        String result;
        try {
            HashMap<String, String> headers = new HashMap<>(3);
            Long millisecond = (new Date()).getTime();
            headers.put("Authorization", getBearerToken(config.getKey(), config.getPartnerId(), path, millisecond));
            headers.put("PartnerId", config.getPartnerId());
            headers.put("Timestamp", millisecond + "");
            HttpResponse execute = HttpRequest.post(url)
                    .addHeaders(headers)
                    .body(requestStr)
                    .setConnectionTimeout(3000)
                    .setReadTimeout(10000)
                    .execute();
            status = execute.getStatus();
            channelResult.setStatus(status);
            result = execute.body();
            channelResult.setResponseStr(result);
            stopWatch.stop();
            if (!execute.isOk()) {
                log.error("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}] 异常.", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
                return channelResult.channelException(status + "", "500 内部服务器错误", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout 异常.", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method));
            return channelResult.timeoutException(requestStr, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException(requestStr, result);
            }
            log.info("<<<< response: [{}]", result);

            B2bCreateOrderResp baseB2bResult = JsonKit.toBean(result, B2bCreateOrderResp.class);
            if (Objects.isNull(baseB2bResult)) {
                return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
            }
            if (baseB2bResult.checkSuccessRet()) {
                return channelResult.success(baseB2bResult, requestStr, result);
            } else {
                return channelResult.channelException(baseB2bResult.getCode() + "", baseB2bResult.getMessage(), requestStr, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            log.error("parseRespException 异常", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static ChannelResult<B2bPaymentResp> payment(B2bPaymentReq request) {
        B2bConfigModel config = request.getConfig();
        String path = "/order/pay";
        String url = StrUtil.removeSuffix(config.getApiUrl(), "/") + path;
        B2bPaymentReqModel model = request.getModel();
        request.getModel().setPartnerId(config.getPartnerId());
        ChannelResult<B2bPaymentResp> channelResult = new ChannelResult<>();
        String method = "payment";
        StopWatch stopWatch = new StopWatch(method);
        stopWatch.start();
        channelResult.setUrl(url);
        channelResult.setMethod(method);
        String requestStr;
        Map<String, Object> paramMap = new HashMap<>(16);
        try {
            requestStr = JSONUtil.toJsonStr(model);
            channelResult.setRequestStr(requestStr);
            log.info(">>>> request paramMap : [{}]", requestStr);
        } catch (Exception ex) {
            log.error("buildParamsException request: [{}] 异常", JSONUtil.toJsonPrettyStr(request), ex);
            requestStr = JSONUtil.toJsonStr(paramMap);
            channelResult.setRequestStr(requestStr);
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            return channelResult.buildParamsException();
        }

        int status;
        String result;
        try {
            HashMap<String, String> headers = new HashMap<>(1);
            Long millisecond = (new Date()).getTime();
            headers.put("Authorization", getBearerToken(config.getKey(), config.getPartnerId(), path, millisecond));
            headers.put("PartnerId", config.getPartnerId());
            headers.put("Timestamp", millisecond + "");
            HttpResponse execute = HttpRequest.post(url)
                    .addHeaders(headers)
                    .body(requestStr)
                    .setConnectionTimeout(3000)
                    .setReadTimeout(10000)
                    .execute();
            status = execute.getStatus();
            channelResult.setStatus(status);
            result = execute.body();
            channelResult.setResponseStr(result);
            stopWatch.stop();
            if (!execute.isOk()) {
                log.error("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}] 异常.", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
                return channelResult.channelException(status + "", "500 内部服务器错误", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout 异常.", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method));
            return channelResult.timeoutException(requestStr, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException(requestStr, result);
            }
            log.info("<<<< response: [{}]", result);

            B2bPaymentResp baseB2bResult = JsonKit.toBean(result, B2bPaymentResp.class);
            if (Objects.isNull(baseB2bResult)) {
                return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
            }
            if (baseB2bResult.checkSuccessRet()) {
                return channelResult.success(baseB2bResult, requestStr, result);
            } else {
                return channelResult.channelException(baseB2bResult.getCode() + "", baseB2bResult.getMessage(), requestStr, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            log.error("parseRespException 异常", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static ChannelResult<B2bRefundResp> refund(B2bRefundReq request) {
        String path = "/order/refund";
        B2bConfigModel config = request.getConfig();
        String url = StrUtil.removeSuffix(config.getApiUrl(), "/") + path;
        B2bRefundReqModel model = request.getModel();
        request.getModel().setPartnerId(config.getPartnerId());
        ChannelResult<B2bRefundResp> channelResult = new ChannelResult<>();
        String method = "refund";
        StopWatch stopWatch = new StopWatch(method);
        stopWatch.start();
        channelResult.setUrl(url);
        channelResult.setMethod(method);
        String requestStr;
        Map<String, Object> paramMap = new HashMap<>(16);
        try {
            requestStr = JSONUtil.toJsonStr(model);
            channelResult.setRequestStr(requestStr);
            log.info(">>>> request paramMap : [{}]", requestStr);
        } catch (Exception ex) {
            log.error("buildParamsException request: [{}] 异常", JSONUtil.toJsonPrettyStr(request), ex);
            requestStr = JSONUtil.toJsonStr(paramMap);
            channelResult.setRequestStr(requestStr);
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            return channelResult.buildParamsException();
        }

        int status;
        String result;
        try {
            HashMap<String, String> headers = new HashMap<>(1);
            Long millisecond = (new Date()).getTime();
            headers.put("Authorization", getBearerToken(config.getKey(), config.getPartnerId(), path, millisecond));
            headers.put("PartnerId", config.getPartnerId());
            headers.put("Timestamp", millisecond + "");
            HttpResponse execute = HttpRequest.post(url)
                    .addHeaders(headers)
                    .body(requestStr)
                    .setConnectionTimeout(3000)
                    .setReadTimeout(10000)
                    .execute();
            status = execute.getStatus();
            channelResult.setStatus(status);
            result = execute.body();
            channelResult.setResponseStr(result);
            stopWatch.stop();
            if (!execute.isOk()) {
                log.error("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}] 异常.", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
                return channelResult.channelException(status + "", "500 内部服务器错误", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout 异常.", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method));
            return channelResult.timeoutException(requestStr, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException(requestStr, result);
            }
            log.info("<<<< response: [{}]", result);

            B2bRefundResp baseB2bResult = JsonKit.toBean(result, B2bRefundResp.class);
            if (Objects.isNull(baseB2bResult)) {
                return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
            }
            if (baseB2bResult.checkSuccessRet()) {
                return channelResult.success(baseB2bResult, requestStr, result);
            } else {
                return channelResult.channelException(baseB2bResult.getCode() + "", baseB2bResult.getMessage(), requestStr, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            log.error("parseRespException 异常", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static ChannelResult<B2bCancelResp> cancel(B2bCancelReq request) {
        B2bConfigModel config = request.getConfig();
        String path = "/order/cancelOrder";
        String url = StrUtil.removeSuffix(config.getApiUrl(), "/") + path;
        url = url + "?orderId=" + request.getOrderId();
        ChannelResult<B2bCancelResp> channelResult = new ChannelResult<>();
        String method = "cancelOrder";
        StopWatch stopWatch = new StopWatch(method);
        stopWatch.start();
        channelResult.setUrl(url);
        channelResult.setMethod(method);
        String requestStr;
        Map<String, Object> paramMap = new HashMap<>(16);
        try {
            requestStr = request.getOrderId();
            channelResult.setRequestStr(requestStr);
            log.info(">>>> request paramMap : [{}]", requestStr);
        } catch (Exception ex) {
            log.error("buildParamsException request: [{}] 异常", JSONUtil.toJsonPrettyStr(request), ex);
            requestStr = JSONUtil.toJsonStr(paramMap);
            channelResult.setRequestStr(requestStr);
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            return channelResult.buildParamsException();
        }

        int status;
        String result;
        try {
            HashMap<String, String> headers = new HashMap<>(1);
            Long millisecond = (new Date()).getTime();
            headers.put("Authorization", getBearerToken(config.getKey(), config.getPartnerId(), path, millisecond));
            headers.put("PartnerId", config.getPartnerId());
            headers.put("Timestamp", millisecond + "");

            HttpResponse execute = HttpRequest.get(url)
                    .addHeaders(headers)
                    .setConnectionTimeout(3000)
                    .setReadTimeout(10000)
                    .execute();
            status = execute.getStatus();
            channelResult.setStatus(status);
            result = execute.body();
            channelResult.setResponseStr(result);
            stopWatch.stop();
            if (!execute.isOk()) {
                log.error("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}] 异常.", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
                return channelResult.channelException(status + "", "500 内部服务器错误", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout 异常.", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method));
            return channelResult.timeoutException(requestStr, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException(requestStr, result);
            }
            log.info("<<<< response: [{}]", result);

            B2bCancelResp baseB2bResult = JsonKit.toBean(result, B2bCancelResp.class);
            if (Objects.isNull(baseB2bResult)) {
                return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
            }
            if (baseB2bResult.checkSuccessRet()) {
                return channelResult.success(baseB2bResult, requestStr, result);
            } else {
                return channelResult.channelException(baseB2bResult.getCode() + "", baseB2bResult.getMessage(), requestStr, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            log.error("parseRespException 异常", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    private static String getBearerToken(String key, String partnerId, String path, Long timestamp) {
        String data = partnerId + "" + timestamp + "" + path;
        byte[] hacByte = SecureUtil.hmacSha1(key).digest(data);
        return "Bearer ZZY-OPENAPI:" + partnerId + ":" + Base64.encode(hacByte);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        B2bCreateOrderReq req = new B2bCreateOrderReq();
        B2bConfigModel config = new B2bConfigModel();
        config.setApiUrl("http://testapi.juyingzhiye.com");
        config.setPartnerId("7811");
        config.setKey("f90f43139c0a4c118f2000095df12df9");
        req.setConfig(config);

        B2bCreateOrderReqModel model = new B2bCreateOrderReqModel();
        model.setOrderQuantity(1);
        model.setOrderRemark("订单备注");
        model.setProductId("13915");
        model.setVisitDate("2021-12-21");
        model.setOriginOrderId(RandomUtil.randomNumbers(10));

        ContactPersonInfo contactPerson = new ContactPersonInfo();
        contactPerson.setMobile("18101870165");
        contactPerson.setIdCard("232332199402221520");
        contactPerson.setName("李桓");
        model.setContactPerson(contactPerson);

        List<VisitPersonInfo> visitPersonItems = new ArrayList<>();
        VisitPersonInfo visitPerson1 = new VisitPersonInfo();
        visitPerson1.setMobile("18101870165");
        visitPerson1.setIdCard("232332199402221520");
        visitPerson1.setName("李桓");
        visitPersonItems.add(visitPerson1);
        model.setVisitPerson(visitPersonItems);
        req.setModel(model);

        ChannelResult<B2bCreateOrderResp> channelResult = createOrder(req);

        if (channelResult.isSuccessful()) {
            B2bCreateOrderResp data = channelResult.getData();
            B2bCreateOrderRespModel respModel = data.getModel();
            System.out.println(respModel);
        } else {
            System.out.println(channelResult.getCode());
            System.out.println(channelResult.getMsg());
        }
    }

}
