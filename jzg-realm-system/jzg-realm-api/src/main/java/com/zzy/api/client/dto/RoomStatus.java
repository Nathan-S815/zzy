package com.zzy.api.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomStatus {

    private Integer liveCount;
    private List<RoomChatter> liveChatter;

}
