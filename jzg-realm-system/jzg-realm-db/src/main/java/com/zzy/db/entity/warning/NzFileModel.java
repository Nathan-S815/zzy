package com.zzy.db.entity.warning;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class NzFileModel implements Serializable {

    private String uid;

    private String name;

    private String status;

    private String url;

    private String type;

    public NzFileModel(String uid, String name, String status, String url, String type) {
        this.uid = uid;
        this.name = name;
        this.status = status;
        this.url = url;
        this.type = type;
    }

    public NzFileModel() {
    }
}
