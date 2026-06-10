package com.nuwa.infrastructure.discovery.database.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.discovery.util.MaterialJson;
import lombok.Data;

import java.util.Date;

/**
 * @author hy
 */
@Data
public class UserTaskApplyPageVO {
    private Long userId;
    private String userImg;
    private String accountId;
    private String nick;
    private String userNick;
    private String fansCount;
    private String sex;
    private String region;
    private String level;
    private Date applyTime;

    private String memberTag;

    private Integer userLevelId;

    private String levelName;

    @JsonSerialize(using = MaterialJson.class)
    private String video;

}
