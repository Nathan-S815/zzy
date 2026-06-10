package com.zzy.db.entity.publicsentiment;

import lombok.Data;

@Data
public class ScenicNews {

    private Integer id;

    /**
     * 搜索数量
     */
    private String score;

    /**
     * 名称
     */
    private String name;

    /**
     * 排名
     */
    private Integer rankNum;

    /**
     * 标记1：热  2：新 3：爆 4：推
     */
    private Integer flag;
}
