package com.zzy.db.entity.publicsentiment;

import lombok.Data;

@Data
public class ScenicSpot {

    private Integer id;

    /**
     * 服务类型 1：景区 2：酒店 3：饭店
     */
    private Integer serviceNumber;

    /**
     * 分数
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
     * 升降标记1：up  2：down
     */
    private Integer flag;
}
