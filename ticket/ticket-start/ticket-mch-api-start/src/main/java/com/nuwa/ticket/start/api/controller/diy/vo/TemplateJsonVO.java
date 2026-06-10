package com.nuwa.ticket.start.api.controller.diy.vo;

import lombok.Data;

/**
 * 装修模板VO
 *
 * @author hy
 */
@Data
public class TemplateJsonVO {
    private Object value;
    private Long id;

    public TemplateJsonVO(Object value, Long id) {
        this.value = value;
        this.id = id;
    }
}
