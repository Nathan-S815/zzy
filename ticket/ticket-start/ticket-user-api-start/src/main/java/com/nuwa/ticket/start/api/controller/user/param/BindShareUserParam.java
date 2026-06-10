package com.nuwa.ticket.start.api.controller.user.param;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * BindShareUserParam 绑定分享人
 *
 * @author hy
 * @date 2021/5/7 9:39
 * @since 1.0.0
 */
@Data
@ToString
public class BindShareUserParam {
    @NotBlank
    private String shareCode;
}
