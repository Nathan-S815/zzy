package com.zzy.db.entity.ticket;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ABaWenLvTicket {
    private String date;
    private List<NumberList> numberList;
    private Integer type;


}