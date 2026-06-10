package com.nuwa.infrastructure.ticket.third.b2b.resp;

import com.nuwa.infrastructure.ticket.third.b2b.model.resp.B2bCreateOrderRespModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.resp.B2bRefundRespModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class B2bRefundResp extends B2bApiResponse<B2bRefundRespModel> {

}
