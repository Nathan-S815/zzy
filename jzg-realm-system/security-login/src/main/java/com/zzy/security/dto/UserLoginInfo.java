package com.zzy.security.dto;


import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class UserLoginInfo {

    private String loginState;
    private Integer userId;
    private String loginName;
    private List<String> auths;
    private String name;
    private Integer departId;
    private Integer memberId;
    private String departmentName;
    private String phone;
    private String title;
    private String email;
    private List<MerchantInfo> merchantInfo;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLoginInfo that = (UserLoginInfo) o;
        return userId.equals(that.userId) &&
                loginName.equals(that.loginName) &&
                name.equals(that.name) &&
                departId.equals(that.departId) &&
                memberId.equals(that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, loginName, name, departId, memberId);
    }
}
