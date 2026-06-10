package com.nuwa.infrastructure.ticket.third.b2b.resp;

import com.nuwa.infrastructure.ticket.third.b2b.model.resp.B2bCancelRespModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.resp.B2bPaymentRespModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class B2bCancelResp extends B2bApiResponse<B2bCancelRespModel> {

}
