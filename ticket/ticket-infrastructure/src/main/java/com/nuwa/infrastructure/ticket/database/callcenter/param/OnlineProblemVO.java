package com.nuwa.infrastructure.ticket.database.callcenter.param;

import lombok.Data;

import java.util.Date;

@Data
public class OnlineProblemVO {

    private Long id;

    /**
     * 类别
     */
    private String category;

    /**
     * 类型
     */
    private String type;

    /**
     * 问题
     */
    private String problem;

    /**
     * 问题结果
     */
    private String result;

    private Date createTime;

}
