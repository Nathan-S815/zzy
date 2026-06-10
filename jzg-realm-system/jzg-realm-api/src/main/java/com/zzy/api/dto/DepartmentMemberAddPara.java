package com.zzy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DepartmentMemberAddPara {


    /**
     * 所属部门ID
     */
    @ApiModelProperty(value = "所属部门ID", required = true)
    private Integer departmentId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    /**
     * 职称
     */
    @ApiModelProperty(value = "职称/岗位", required = false)
    private String title;

    /**
     * 性别(0:女,1:男)
     */
    @ApiModelProperty(value = "性别(0:女,1:男)", required = false)
    private Integer gender;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = false)
    private String phoneNumber;

    @ApiModelProperty(value = "出生日期", required = false)
    private String birth;

    @ApiModelProperty(value = "头像", required = false)
    private String headIcon;

    /**
     * 邮件地址
     */
    @ApiModelProperty(value = "邮件地址", required = false)
    private String email;


}
