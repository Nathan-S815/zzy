package com.nuwa.discovery.start.api.controller.vo;

import lombok.Data;

/**
 * @author hy
 */
@Data
public class CheckAuthResultVO {
    private Boolean authFlag;
    private String authMsg;
}
