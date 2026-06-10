package com.nuwa.ticket.start.api.controller.user.param;

import lombok.Data;
import lombok.ToString;

/**
 * UpdateShareCodeParam 更新用户分享码
 *
 * @author hy
 * @date 2021/5/7 9:39
 * @since 1.0.0
 */
@Data
@ToString
public class UpdateShareCodeParam {
    private String shareCode;
}
