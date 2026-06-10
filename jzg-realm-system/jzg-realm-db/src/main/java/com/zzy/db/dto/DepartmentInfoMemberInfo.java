package com.zzy.db.dto;


import com.zzy.db.entity.eventdepart.DepartmentInfo;
import lombok.Data;

@Data
public class DepartmentInfoMemberInfo extends DepartmentInfo {

    private String memberName;
    private String headIcon;

}
