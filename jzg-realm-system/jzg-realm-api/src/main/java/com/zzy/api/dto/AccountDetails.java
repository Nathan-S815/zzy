package com.zzy.api.dto;

import com.zzy.db.entity.eventdepart.DepartmentInfo;
import com.zzy.db.entity.eventdepart.DepartmentMember;
import com.zzy.security.dto.MerchantInfo;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Data
public class AccountDetails {

    private Integer userId;
    private String userName;
    private String headIcon;
    private Integer departId;
    private Integer departMemberId;
    private Boolean isDepartLeader;
    private Boolean isDepartAdmin;
    private Set<String> roleNames;
    private List<MerchantInfo> merchantInfo;


}
