package com.nuwa.infrastructure.ticket.third.b2b.resp;

import com.nuwa.infrastructure.ticket.third.b2b.model.resp.B2bCreateOrderRespModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class B2bCreateOrderResp extends B2bApiResponse<B2bCreateOrderRespModel> {

}
