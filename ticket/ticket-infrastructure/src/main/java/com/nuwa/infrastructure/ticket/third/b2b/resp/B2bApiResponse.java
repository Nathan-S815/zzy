package com.nuwa.infrastructure.ticket.third.b2b.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * B2B通用响应参数
 *
 * @author hy
 */
@Data
@ToString
public class B2bApiResponse<T> {

    private Integer code;

    private String logId;

    @JsonProperty(value = "IsSuccess")
    private Boolean success;

    private String message;

    @JsonProperty(value = "Data")
    private T model;

    public Boolean checkSuccessRet() {
        return code.equals(1000) || success;
    }
}
