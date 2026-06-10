package com.nuwa.ticket.start.api.controller.order.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import lombok.Data;

import java.util.Objects;

/**
 * @author hy
 */
@Data
public class OrderStatusConverter implements Converter<Integer> {


    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里是读的时候会调用 不用管
     *
     * @return
     */
    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) {
        return null;
    }

    /**
     * 这里是写的时候会调用 不用管
     *
     * @return
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) {
        String statusName = (String) "--";
        TicketOrderEnum ticketOrderEnum = TicketOrderEnum.getByCode(context.getValue());
        if (Objects.nonNull(ticketOrderEnum)) {
            statusName = (String) ticketOrderEnum.getMessage();
        }
        return new WriteCellData<>(statusName);
    }
}
