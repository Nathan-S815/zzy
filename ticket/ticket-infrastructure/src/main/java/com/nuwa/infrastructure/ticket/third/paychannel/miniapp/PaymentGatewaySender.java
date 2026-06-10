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

import java.math.BigDecimal;
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
public class PaymentGatewaySender {

    public static ChannelResult<MiniAppOrderCreateResp> createOrder(MiniAppOrderCreateReq req, String gatewayUrl) {
        String url = gatewayUrl + "/api/v1/mini_app/order/pay";
        ChannelResult<MiniAppOrderCreateResp> channelResult = new ChannelResult<>();
        String method = "createOrder";
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
                return channelResult.channelException(status + "", "500 服务器内部错误", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout ??.", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method), ex);
            return channelResult.timeoutException(method, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException("没有返回值", result);
            }
            log.info("<<<< response: [{}]", result);
            JSONObject retJson = JSONUtil.parseObj(result, true);
            if ("0000".equalsIgnoreCase(retJson.getStr("code"))) {
                MiniAppOrderCreateResp data = retJson.getBean("data", MiniAppOrderCreateResp.class);
                return channelResult.success(data, JSONUtil.toJsonStr(req), result);
            } else {
                return channelResult.channelException(retJson.getStr("code"), retJson.getStr("msg"), requestStr, result, stopWatch.getTotalTimeMillis());

            }
        } catch (Exception ex) {
            log.error("parseRespException ??", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static ChannelResult<H5OrderCreateResp> createH5Order(H5OrderCreateReq req, String gatewayUrl) {
        String url = gatewayUrl + "/api/v1/h5/order/pay";
        ChannelResult<H5OrderCreateResp> channelResult = new ChannelResult<>();
        String method = "createOrder";
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
                return channelResult.channelException(status + "", "500 服务器内部错误", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout ??.", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method), ex);
            return channelResult.timeoutException(method, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException("没有返回值", result);
            }
            log.info("<<<< response: [{}]", result);
            JSONObject retJson = JSONUtil.parseObj(result, true);
            if ("0000".equalsIgnoreCase(retJson.getStr("code"))) {
                H5OrderCreateResp data = retJson.getBean("data", H5OrderCreateResp.class);
                return channelResult.success(data, JSONUtil.toJsonStr(req), result);
            } else {
                return channelResult.channelException(retJson.getStr("code"), retJson.getStr("msg"), requestStr, result, stopWatch.getTotalTimeMillis());

            }
        } catch (Exception ex) {
            log.error("parseRespException ??", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static ChannelResult<MiniAppOrderRefundResp> refundOrder(MiniAppOrderRefundReq req, String gatewayUrl) {
        String url = gatewayUrl + "/api/v1/mini_app/order/refund";
        ChannelResult<MiniAppOrderRefundResp> channelResult = new ChannelResult<>();
        String method = "refundOrder";
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
                return channelResult.channelException(status + "", "500 服务器内部错误", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method), ex);
            return channelResult.timeoutException(method, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException("解析报文异常", result);
            }
            log.info("<<<< response: [{}]", result);
            JSONObject retJson = JSONUtil.parseObj(result, true);
            if ("0000".equalsIgnoreCase(retJson.getStr("code"))) {
                MiniAppOrderRefundResp data = retJson.getBean("data", MiniAppOrderRefundResp.class);
                return channelResult.success(data, JSONUtil.toJsonStr(req), result);
            } else {
                return channelResult.channelException(retJson.getStr("code"), retJson.getStr("msg"), requestStr, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            log.error("parseRespException ??", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static ChannelResult<MiniAppRefundQueryResp> refundOrderQuery(MiniAppRefundQueryReq req, String gatewayUrl) {
        String url = gatewayUrl + "/api/v1/mini_app/refund/query";
        ChannelResult<MiniAppRefundQueryResp> channelResult = new ChannelResult<>();
        String method = "refundQuery";
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
                return channelResult.channelException(status + "", "500 服务器内部错误", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method), ex);
            return channelResult.timeoutException(method, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException("解析报文异常", result);
            }
            log.info("<<<< response: [{}]", result);
            JSONObject retJson = JSONUtil.parseObj(result, true);
            if ("0000".equalsIgnoreCase(retJson.getStr("code"))) {
                MiniAppRefundQueryResp data = retJson.getBean("data", MiniAppRefundQueryResp.class);
                return channelResult.success(data, JSONUtil.toJsonStr(req), result);
            } else {
                return channelResult.channelException(retJson.getStr("code"), retJson.getStr("msg"), requestStr, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            log.error("parseRespException 解析报文错误", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static void main(String[] args) {
    }

}
