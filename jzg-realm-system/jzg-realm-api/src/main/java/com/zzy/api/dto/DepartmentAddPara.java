package com.zzy.api.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepartmentAddPara {

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", required = true)
    private String name;

    /**
     * 部门职责
     */
    @ApiModelProperty(value = "部门职责", required = false)
    private String duty;


    /**
     * 部门地址
     */
    @ApiModelProperty(value = "部门地址", required = false)
    private String address;

    /**
     * 部门联系电话
     */
    @ApiModelProperty(value = "部门联系电话", required = false)
    private String phone;







}
