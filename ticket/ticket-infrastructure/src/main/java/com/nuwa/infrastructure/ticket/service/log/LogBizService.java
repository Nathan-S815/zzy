package com.nuwa.infrastructure.ticket.service.log;

import com.nuwa.infrastructure.ticket.database.order.entity.SupplierRequestLog;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrderLog;
import com.nuwa.infrastructure.ticket.service.log.dto.ApiLogDTO;
import com.nuwa.infrastructure.ticket.service.log.dto.OrderLogDTO;
import com.nuwa.infrastructure.ticket.service.log.enums.SupplierLogTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hy
 */
@Service
@Slf4j
public class LogBizService {

    @Async
    public void saveOrderLog(OrderLogDTO dto) {
        log.info("orderLog:{}", dto);
        TicketOrderLog ticketOrderLog = new TicketOrderLog();
        ticketOrderLog.setCreateDate(new Date());
        ticketOrderLog.setOrderId(dto.getOrderId());
        ticketOrderLog.setType(dto.getType());
        ticketOrderLog.setResult(dto.getResult());
        ticketOrderLog.setBizOrderId(dto.getBizOrderId());
        ticketOrderLog.insert();
        log.info("save OrderLog[{}] success", dto.getResult());
    }

    @Async
    public void saveApiLog(ApiLogDTO dto) {
        log.info("apiLog:{}", dto);
        SupplierRequestLog supplierRequestLog = new SupplierRequestLog();
        supplierRequestLog.setRequestNo("");
        supplierRequestLog.setReq(dto.getReq());
        supplierRequestLog.setResp(dto.getResp());
        supplierRequestLog.setCostTime(dto.getCostTime());
        supplierRequestLog.setOrderId(dto.getOrderId());
        supplierRequestLog.setBizOrderId(dto.getBizOrderId());
        supplierRequestLog.setCreateDate(new Date());
        supplierRequestLog.setResult(dto.getResult());
        supplierRequestLog.setType(dto.getType());
        SupplierLogTypeEnum typeEnum = SupplierLogTypeEnum.getByCode(dto.getType());
        if (typeEnum != null) {
            supplierRequestLog.setTypeName(typeEnum.getMessage());
        }
        supplierRequestLog.setHttpCode(dto.getHttpCode());
        supplierRequestLog.setUrl(dto.getUrl());
        supplierRequestLog.insert();
        log.info("save SupplierRequestLog[orderId:{}] [{}] success.", supplierRequestLog.getId(), supplierRequestLog.getTypeName());
    }
}
