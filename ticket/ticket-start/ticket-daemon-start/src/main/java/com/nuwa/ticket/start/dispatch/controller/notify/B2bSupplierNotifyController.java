package com.nuwa.ticket.start.dispatch.controller.notify;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.order.OrderClientI;
import com.nuwa.client.ticket.api.order.param.B2bSupplierConsumerNotifyParam;
import com.nuwa.client.ticket.api.order.param.B2bSupplierRefundNotifyParam;
import com.nuwa.client.ticket.api.order.param.B2bSupplierTicketNotifyParam;
import com.nuwa.client.ticket.api.order.param.VoucherDTO;
import com.nuwa.ticket.start.dispatch.controller.notify.b2b.B2bNoticeDTO;
import com.nuwa.ticket.start.dispatch.controller.notify.b2b.B2bNoticeRetVO;
import com.nuwa.ticket.start.dispatch.controller.notify.b2b.model.*;
import com.nuwa.ticket.start.dispatch.controller.notify.b2b.OrderNotifyHeader;
import com.nuwa.ticket.start.dispatch.util.JsonKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@RestController
@Api(tags = {"B2B供应商回调"})
public class B2bSupplierNotifyController {

    @Autowired
    private OrderClientI orderClientI;

    @ApiOperation(value = "回调")
    @RequestMapping(value = "/b2b/notice", method = RequestMethod.POST)
    @ResponseBody
    public B2bNoticeRetVO notice(@RequestBody B2bNoticeDTO param) {
        String jsonStr = JSONUtil.toJsonPrettyStr(param);
        log.info("b2b notice param:{}", jsonStr);
        B2bNoticeRetVO vo = new B2bNoticeRetVO();
        vo.setCode(0);
        vo.setMessage("未知异常");
        OrderNotifyHeader orderNotifyHeader = param.getOrderNotifyHeader();
        String orderNotifyBody = param.getOrderNotifyBody();
        String notifyServiceName = orderNotifyHeader.getNotifyServiceName();
        if ("order_refund_notify".equalsIgnoreCase(notifyServiceName)) {
            return doRefundNotify(orderNotifyBody, jsonStr);
        } else if ("order_ticket_notify".equalsIgnoreCase(notifyServiceName)) {
            return doTicketNotify(orderNotifyBody, jsonStr);
        } else if ("order_verify_notify".equalsIgnoreCase(notifyServiceName)) {
            return doConsumerNotify(orderNotifyBody, jsonStr);
        }
        log.error("b2bNotice return vo:{}.", JSONUtil.toJsonPrettyStr(vo));
        return vo;
    }

    private B2bNoticeRetVO doConsumerNotify(String orderNotifyBody, String requestStr) {
        B2bNoticeRetVO vo = new B2bNoticeRetVO();
        try {
            B2bConsumerNoticeModel b2bConsumerNoticeModel = JsonKit.toBean(orderNotifyBody, B2bConsumerNoticeModel.class);
            if (Objects.nonNull(b2bConsumerNoticeModel)) {
                Integer checkedNumber = b2bConsumerNoticeModel.getCheckedNumber();
                String supplierPaymentOrderNo = b2bConsumerNoticeModel.getOriginOrderId();
                B2bSupplierConsumerNotifyParam dto = new B2bSupplierConsumerNotifyParam();
                dto.setCheckedNumber(checkedNumber);
                dto.setTotalNumber(b2bConsumerNoticeModel.getOrderTotal());
                dto.setPaymentNo(supplierPaymentOrderNo);
                dto.setRequestStr(requestStr);
                dto.setRequestStr("{" + "\"code\": 1000," + "\"message\": \"收到通知\"}");
                SingleResponse<?> singleResponse = orderClientI.b2bConsumerNoticeProcess(dto);
                if (singleResponse.isSuccess()) {
                    vo.setCode(B2bNoticeRetVO.SUCCESS);
                    vo.setMessage("收到通知");
                } else {
                    vo.setCode(0);
                    vo.setMessage(singleResponse.getErrMessage());
                }
            }
        } catch (Exception ex) {
            log.error("doConsumerNotify error.", ex);
            vo.setCode(0);
            vo.setMessage("未知异常");
        }
        log.error("doConsumerNotify return vo:{}.", JSONUtil.toJsonPrettyStr(vo));
        return vo;
    }

    private B2bNoticeRetVO doTicketNotify(String orderNotifyBody, String requestStr) {
        B2bNoticeRetVO vo = new B2bNoticeRetVO();
        try {
            B2bTicketNoticeModel b2bTicketNoticeModel = JsonKit.toBean(orderNotifyBody, B2bTicketNoticeModel.class);
            if (Objects.nonNull(b2bTicketNoticeModel)) {
                String supplierPaymentOrderNo = b2bTicketNoticeModel.getOriginOrderId();
                List<VoucherDTO> voucherItems = new ArrayList<>();
                B2bSupplierTicketNotifyParam dto = new B2bSupplierTicketNotifyParam();
                VoucherCodeModel voucherCode = b2bTicketNoticeModel.getVoucherCode();
                if (Objects.nonNull(voucherCode)) {
                    List<UserVoucherModel> orderUserVouchers = voucherCode.getOrderUserVouchers();
                    if (Objects.nonNull(orderUserVouchers) && orderUserVouchers.size() > 0) {
                        voucherItems = orderUserVouchers.stream().map(x -> {
                            VoucherDTO voucherDTO = new VoucherDTO();
                            BeanUtil.copyProperties(x, voucherDTO);
                            return voucherDTO;
                        }).collect(Collectors.toList());
                    } else if (StrUtil.isNotBlank(voucherCode.getVoucherCode())) {
                        VoucherDTO voucherDTO = new VoucherDTO();
                        voucherDTO.setVoucherCode(voucherCode.getVoucherCode());
                        voucherItems.add(voucherDTO);
                    }
                }

                dto.setVoucherItems(voucherItems);
                dto.setPaymentNo(supplierPaymentOrderNo);
                dto.setRequestStr(requestStr);
                dto.setRequestStr("{" + "\"code\": 1000," + "\"message\": \"收到通知\"}");
                dto.setTicketStatus(b2bTicketNoticeModel.getTicketStatus());
                SingleResponse<?> singleResponse = orderClientI.b2bTicketNoticeProcess(dto);
                if (singleResponse.isSuccess()) {
                    vo.setCode(B2bNoticeRetVO.SUCCESS);
                    vo.setMessage("收到通知");
                } else {
                    vo.setCode(0);
                    vo.setMessage(singleResponse.getErrMessage());
                }
            }
        } catch (Exception ex) {
            log.error("doTicketNotify error.", ex);
            vo.setCode(0);
            vo.setMessage("未知异常");
        }
        log.error("doTicketNotify return vo:{}.", JSONUtil.toJsonPrettyStr(vo));
        return vo;
    }

    private B2bNoticeRetVO doRefundNotify(String orderNotifyBody, String requestStr) {
        B2bNoticeRetVO vo = new B2bNoticeRetVO();
        try {
            B2bRefundNoticeModel b2bRefundNoticeModel = JsonKit.toBean(orderNotifyBody, B2bRefundNoticeModel.class);
            if (Objects.nonNull(b2bRefundNoticeModel)) {
                String refundApplyNo = b2bRefundNoticeModel.getRefundApplyNo();
                B2bSupplierRefundNotifyParam dto = new B2bSupplierRefundNotifyParam();
                dto.setReason(b2bRefundNoticeModel.getRefundReason());
                dto.setStatus(b2bRefundNoticeModel.getRefundStatus());
                dto.setRefundNo(refundApplyNo);
                dto.setOrderId(b2bRefundNoticeModel.getOrderId());
                dto.setRequestStr(requestStr);
                dto.setRequestStr("{" + "\"code\": 1000," + "\"message\": \"收到通知\"}");
                SingleResponse<?> singleResponse = orderClientI.b2bRefundNoticeProcess(dto);
                log.info("singleResponse:{}", JSONUtil.toJsonPrettyStr(singleResponse));
                if (singleResponse.isSuccess()) {
                    vo.setCode(B2bNoticeRetVO.SUCCESS);
                    vo.setMessage("收到通知");
                } else {
                    vo.setCode(0);
                    vo.setMessage(singleResponse.getErrMessage());
                }
            }
        } catch (Exception ex) {
            log.error("doRefundNotify error.", ex);
            vo.setCode(0);
            vo.setMessage("未知异常");
        }
        log.error("doRefundNotify return vo:{}.", JSONUtil.toJsonPrettyStr(vo));
        return vo;
    }
}
