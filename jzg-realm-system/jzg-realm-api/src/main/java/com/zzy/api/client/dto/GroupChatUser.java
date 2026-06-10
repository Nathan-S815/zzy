package com.zzy.api.client.dto;


import cn.hutool.crypto.digest.DigestUtil;
import lombok.Data;

@Data
public class GroupChatUser {

    private String groupName;
    private String groupId;
    private String userId;
    private String userName;
    private String eventId;
    private String departmentId;

    private GroupChatUser(){}

    private GroupChatUser(String groupName, String groupId, String userId, String userName, String eventId, String departmentId) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.userId = userId;
        this.userName = userName;
        this.eventId = eventId;
        this.departmentId = departmentId;
    }


    public static GroupChatUser build(String groupName,String userId, String userName, String eventId, String departmentId){
        return new GroupChatUser(groupName, DigestUtil.md5Hex(userId+userName+eventId+departmentId),userId,userName,eventId,departmentId);
    }

    public static GroupChatUser build(String groupName,String userName){
        return new GroupChatUser(groupName, DigestUtil.md5Hex(groupName+userName),null,userName,null,null);
    }

}
