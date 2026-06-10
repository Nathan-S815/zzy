package com.nuwa.ticket.start.api.controller.user.param;

import lombok.Data;
import lombok.ToString;

/**
 * UpdateUserInfoByDouyinParam 更新用户昵称参数
 *
 * @author hy
 * @date 2021/5/7 9:39
 * @since 1.0.0
 */
@Data
@ToString
public class UpdateUserInfoByDouyinParam {
    private String signature;
    private String encryptedData;
    private String iv;
    private String session;
}
