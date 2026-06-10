package com.zzy.api.client.dto;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class UserChatInfo implements Serializable {

    private String userName;
    private String msgContent;
    private String sendTime;
    private String groupName;
    private String headIcon;

}
