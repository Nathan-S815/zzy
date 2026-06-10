package com.nuwa.ticket.start.api.controller.one.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.database.order.vo.UserVoucherOrderListVO;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserVoucherOrderPoiListVO extends UserVoucherOrderListVO {
    private String poiName;

    @JsonSerialize(using = MaterialJson.class)
    private String poiImage;
}
