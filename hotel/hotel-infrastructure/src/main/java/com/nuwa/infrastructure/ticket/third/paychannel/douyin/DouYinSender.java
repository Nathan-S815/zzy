package com.nuwa.infrastructure.ticket.third.paychannel.douyin;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.req.Code2SessionReq;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.req.CreateOrderReq;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.req.CreateRefundReq;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.req.SettleReq;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp.Code2SessionResp;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp.CreateOrderResp;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp.CreateRefundResp;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp.SettleResp;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.util.DouYinSignTool;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 抖音支付
 *
 * @author hy
 */
@Slf4j
public class DouYinSender {

    public static ChannelResult<CreateOrderResp> createOrder(CreateOrderReq request) {
        String url = "https://developer.toutiao.com/api/apps/ecpay/v1/create_order";
        DouYinPayConfig config = request.getConfig();
        ChannelResult<CreateOrderResp> channelResult = new ChannelResult<>();
        String method = "createOrder";
        channelResult.setUrl(url);
        channelResult.setMethod(method);
        String requestStr;
        Map<String, Object> paramMap = new HashMap<>(16);
        try {
            paramMap.put("app_id", request.getAppId());
            paramMap.put("out_order_no", request.getOutOrderNo());
            paramMap.put("total_amount", request.getTotalAmount());
            paramMap.put("subject", request.getSubject());
            paramMap.put("body", request.getBody());
            paramMap.put("valid_time", request.getValidTime());
            paramMap.put("notify_url", request.getNotifyUrl());
            paramMap.put("disable_msg", request.getDisableMsg());
            paramMap.put("msg_page", request.getMsgPage());

            String sign = DouYinSignTool.getSign(paramMap, config.getSalt());
            paramMap.put("sign", sign);
            requestStr = JSONUtil.toJsonStr(paramMap);
            channelResult.setRequestStr(requestStr);
            log.info(">>>> request paramMap : [{}]", requestStr);
        } catch (Exception ex) {
            log.error("buildParamsException request: [{}] 异常", JSONUtil.toJsonPrettyStr(request), ex);
            return channelResult.buildParamsException();
        }

        int status;
        String result;
        StopWatch stopWatch = new StopWatch(method);
        try {
            stopWatch.start();
            HttpResponse execute = HttpRequest.post(url)
                    .body(JSONUtil.toJsonStr(paramMap))
                    .setConnectionTimeout(3000)
                    .setReadTimeout(10000)
                    .execute();
            status = execute.getStatus();
            result = execute.body();
            stopWatch.stop();
            if (!execute.isOk()) {
                log.error("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}] 异常.", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
                return channelResult.channelException(status + "", "500 内部服务器错误", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout 异常.", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method), ex);
            return channelResult.timeoutException(method, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException("没有返回响应参数", result);
            }
            log.info("<<<< response: [{}]", result);
            CreateOrderResp createOrderResp = JSONUtil.toBean(result, CreateOrderResp.class);
            if (Objects.nonNull(createOrderResp) && "0".equals(createOrderResp.getErr_no())) {
                return channelResult.success(createOrderResp, JSONUtil.toJsonStr(paramMap), result);
            } else {
                return channelResult.channelException(createOrderResp.getErr_no(), createOrderResp.getErr_tips(), requestStr, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            log.error("parseRespException 异常", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static ChannelResult<CreateRefundResp> createRefund(CreateRefundReq request) {
        String url = "https://developer.toutiao.com/api/apps/ecpay/v1/create_refund";
        DouYinPayConfig config = request.getConfig();
        ChannelResult<CreateRefundResp> channelResult = new ChannelResult<>();
        String method = "createRefund";
        channelResult.setUrl(url);
        channelResult.setMethod(method);
        String requestStr;
        Map<String, Object> paramMap = new HashMap<>(16);
        try {
            paramMap.put("app_id", request.getAppId());
            paramMap.put("out_order_no", request.getOutOrderNo());
            paramMap.put("out_refund_no", request.getOutRefundNo());
            paramMap.put("reason", StrUtil.isBlank(request.getReason()) ? "用户取消" : request.getReason());
            paramMap.put("refund_amount", request.getTotalAmount());
            paramMap.put("subject", request.getSubject());
            paramMap.put("body", request.getBody());
            paramMap.put("notify_url", request.getNotifyUrl());
            paramMap.put("disable_msg", request.getDisableMsg());
            paramMap.put("all_settle", request.getAllSettle());
            String sign = DouYinSignTool.getSign(paramMap, config.getSalt());
            paramMap.put("sign", sign);
            requestStr = JSONUtil.toJsonStr(paramMap);
            channelResult.setRequestStr(requestStr);
            log.info(">>>> request paramMap : [{}]", requestStr);
        } catch (Exception ex) {
            log.error("buildParamsException request: [{}] 异常", JSONUtil.toJsonPrettyStr(request), ex);
            return channelResult.buildParamsException();
        }

        int status;
        String result;
        StopWatch stopWatch = new StopWatch(method);
        try {
            stopWatch.start();
            HttpResponse execute = HttpRequest.post(url)
                    .body(JSONUtil.toJsonStr(paramMap))
                    .setConnectionTimeout(3000)
                    .setReadTimeout(10000)
                    .execute();
            status = execute.getStatus();
            result = execute.body();
            stopWatch.stop();
            if (!execute.isOk()) {
                log.error("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}] 异常.", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
                return channelResult.channelException(status + "", "500 内部服务器错误", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout 异常.", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method));
            return channelResult.timeoutException(method, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException("没有返回响应参数", result);
            }
            log.info("<<<< response: [{}]", result);
            CreateRefundResp refundResp = JSONUtil.toBean(result, CreateRefundResp.class);
            if (Objects.nonNull(refundResp) && "0".equals(refundResp.getErr_no())) {
                return channelResult.success(refundResp, JSONUtil.toJsonStr(paramMap), result);
            } else {
                return channelResult.channelException(refundResp.getErr_no(), refundResp.getErr_tips(), requestStr, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            log.error("parseRespException 异常", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static ChannelResult<SettleResp> settle(SettleReq request) {
        String url = "http://developer.toutiao.com/api/apps/ecpay/v1/settle";
        DouYinPayConfig config = request.getConfig();
        ChannelResult<SettleResp> channelResult = new ChannelResult<>();
        String method = "settle";
        channelResult.setUrl(url);
        channelResult.setMethod(method);
        String requestStr;
        Map<String, Object> paramMap = new HashMap<>(16);
        try {
            paramMap.put("app_id", request.getAppId());
            paramMap.put("out_order_no", request.getOutOrderNo());
            paramMap.put("out_settle_no", request.getOutSettleNo());
            paramMap.put("settle_desc", request.getSettleDesc());
            if (StrUtil.isNotEmpty(request.getSettleParams())) {
                paramMap.put("settle_params", request.getSettleParams());
            }
            paramMap.put("notify_url", request.getNotifyUrl());
            String sign = DouYinSignTool.getSign(paramMap, config.getSalt());
            paramMap.put("sign", sign);
            requestStr = JSONUtil.toJsonStr(paramMap);
            channelResult.setRequestStr(requestStr);
            log.info(">>>> request paramMap : [{}]", requestStr);
        } catch (Exception ex) {
            log.error("buildParamsException request: [{}] 异常", JSONUtil.toJsonPrettyStr(request), ex);
            return channelResult.buildParamsException();
        }

        int status;
        String result;
        StopWatch stopWatch = new StopWatch(method);
        try {
            stopWatch.start();
            HttpResponse execute = HttpRequest.post(url)
                    .body(JSONUtil.toJsonStr(paramMap))
                    .setConnectionTimeout(3000)
                    .setReadTimeout(10000)
                    .execute();
            status = execute.getStatus();
            result = execute.body();
            stopWatch.stop();
            if (!execute.isOk()) {
                log.error("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}] 异常.", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
                return channelResult.channelException(status + "", "500 内部服务器错误", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout 异常.", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method));
            return channelResult.timeoutException(method, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException("没有返回响应参数", result);
            }
            log.info("<<<< response: [{}]", result);
            SettleResp settleResp = JSONUtil.toBean(result, SettleResp.class);
            if (Objects.nonNull(settleResp) && "0".equals(settleResp.getErr_no())) {
                return channelResult.success(settleResp, JSONUtil.toJsonStr(paramMap), result);
            } else {
                return channelResult.channelException(settleResp.getErr_no(), settleResp.getErr_tips(), requestStr, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            log.error("parseRespException 异常", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static ChannelResult<Code2SessionResp> code2Session(Code2SessionReq request) {
        String url = "https://developer.toutiao.com/api/apps/v2/jscode2session";
        ChannelResult<Code2SessionResp> channelResult = new ChannelResult<>();
        String method = "code2Session";
        channelResult.setUrl(url);
        channelResult.setMethod(method);
        String requestStr;
        Map<String, Object> paramMap = new HashMap<>(16);
        try {
            paramMap.put("appid", request.getAppId());
            paramMap.put("secret", request.getSecret());
            paramMap.put("code", request.getCode());
            requestStr = JSONUtil.toJsonStr(paramMap);
            channelResult.setRequestStr(requestStr);
            log.info(">>>> request paramMap : [{}]", requestStr);
        } catch (Exception ex) {
            log.error("buildParamsException request: [{}] 异常", JSONUtil.toJsonPrettyStr(request), ex);
            return channelResult.buildParamsException();
        }

        int status;
        String result;
        StopWatch stopWatch = new StopWatch(method);
        try {
            stopWatch.start();
            HttpResponse execute = HttpRequest.post(url)
                    .body(JSONUtil.toJsonStr(paramMap))
                    .setConnectionTimeout(3000)
                    .setReadTimeout(10000)
                    .execute();
            status = execute.getStatus();
            result = execute.body();
            stopWatch.stop();
            if (!execute.isOk()) {
                log.error("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}] 异常.", url, stopWatch.getTotalTimeMillis(), status, JSONUtil.toJsonStr(method), result);
                return channelResult.channelException(status + "", "500 内部服务器错误", method, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            stopWatch.stop();
            channelResult.setCostTime(stopWatch.getTotalTimeMillis());
            log.error("post url:{} time:[{} ms] params:[{}] timeout 异常.", url, stopWatch.getTotalTimeMillis(), JSONUtil.toJsonStr(method));
            return channelResult.timeoutException(method, "timeout");
        }
        channelResult.setCostTime(stopWatch.getTotalTimeMillis());
        log.info("post url:{} time:[{} ms] [httpStatus:{}] params:[{}] response:[{}]", url, stopWatch.getTotalTimeMillis(), status, requestStr, result);
        try {
            if (StrUtil.isBlank(result)) {
                return channelResult.parseRespException("没有返回响应参数", result);
            }
            log.info("<<<< response: [{}]", result);
            Code2SessionResp refundResp = JSONUtil.toBean(result, Code2SessionResp.class);
            if (Objects.nonNull(refundResp) && "0".equals(refundResp.getErr_no())) {
                return channelResult.success(refundResp, JSONUtil.toJsonStr(paramMap), result);
            } else {
                return channelResult.channelException(refundResp.getErr_no(), refundResp.getErr_tips(), requestStr, result, stopWatch.getTotalTimeMillis());
            }
        } catch (Exception ex) {
            log.error("parseRespException 异常", ex);
            return channelResult.parseRespException(JSONUtil.toJsonStr(method), result);
        }
    }

    public static void main(String[] args) {
       /* CreateOrderReq req = new CreateOrderReq();
        DouYinPayConfig config = new DouYinPayConfig();
        config.setSalt("6AP4ttqxrfEYJZ6faYH0D7FWiwHWjJdlel3KshAR");
        req.setConfig(config);
        req.setAppId("tta4f5d2483bcc5c3a01");
        req.setBody("测试交易");
        req.setSubject("测试交易");
        req.setMsgPage("/page/order/detail");
        req.setTotalAmount(10L);
        req.setOutOrderNo("99901101781");
        req.setValidTime(10);
        createOrder(req);*/


        CreateRefundReq req = new CreateRefundReq();
        DouYinPayConfig config = new DouYinPayConfig();
        config.setSalt("6AP4ttqxrfEYJZ6faYH0D7FWiwHWjJdlel3KshAR");
        req.setConfig(config);
        req.setAppId("tta4f5d2483bcc5c3a01");
        req.setBody("测试交易");
        req.setSubject("测试交易");
        req.setTotalAmount(1L);
        req.setOutOrderNo("2130");
        req.setOutRefundNo("99785778978978");
        req.setAllSettle(0);
        req.setReason("不想买了");
        ChannelResult<CreateRefundResp> refund = createRefund(req);
        System.out.println(refund);

    }

}
