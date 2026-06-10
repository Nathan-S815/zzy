package com.zzy.api.dto;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
public class EventAddParam implements Serializable {


    @ApiModelProperty(value = "事件任务名称", required = true)
    private String eventName; //

    @ApiModelProperty(value = "事件形式(1指派,2认领)")
    private Integer eventForm; //

    @ApiModelProperty(value = "对应等级(1:一般,2:较大,3:重大,4:特别重大)", required = true)
    private Integer eventLevel; //

    @ApiModelProperty(value = "事件内容描述", required = false)
    private String eventContent; //

    @ApiModelProperty(value = "分配的部门(eg:多个Id用逗号分隔)", required = false)
    private String departIds;

    @ApiModelProperty(value = "对应的事件类型Id", required = true)
    private Integer eventTypeId;


    @ApiModelProperty(value = "是否单部门任务(1:是,0:否),pc上报必填", required = true)
    private Integer isSingle;


    @ApiModelProperty(value = "距离强制指定任务时间(若截止至该日期无人认领则强制指定eg:yyyy-MM-dd)", required = false)
    private String expireDate;

    @ApiModelProperty(value = "上报状态(1:一级上报(通过H5),2:二级上报(通过pc发布))", required = true)
    private Integer reportStatus;

    @ApiModelProperty(value = "经度", required = false)
    private String lng;

    @ApiModelProperty(value = "纬度", required = false)
    private String lat;

    @ApiModelProperty(value = "事件发生地址", required = false)
    private String address;

    @ApiModelProperty(value = "事件发生时间", required = false)
    private String happenTime;

    @ApiModelProperty(value = "图片1地址", required = false)
    private String pic1;

    @ApiModelProperty(value = "图片2地址", required = false)
    private String pic2;

    @ApiModelProperty(value = "图片3地址", required = false)
    private String pic3;

    @ApiModelProperty(value = "视频地址", required = false)
    private String video;

    @ApiModelProperty(value = "其他文件地址", required = false)
    private String otherFiles;


    /**
     * 非api接口参数
     */
    @ApiModelProperty(hidden = true)
    private Integer memeberId;

    @ApiModelProperty(hidden = true)
    private Integer eventTmpId;



    /**
     * 非api接口参数
     */
    @ApiModelProperty(hidden = true)
    private String eventPublisher;


    @ApiModelProperty(hidden = true)
    public boolean isIllegal(){
        boolean flag = (StrUtil.isBlankOrUndefined(eventName) || eventLevel==null || eventTypeId==null ||  reportStatus==null);
        if(!flag){
            if(reportStatus.equals(2)){
                flag = isSingle==null;
            }
        }
        return flag;
    }


    public String getExpireDate() {
        if(StrUtil.isBlankOrUndefined(expireDate)){
            expireDate = DateUtil.offsetHour(new Date(),24).toString("yyyy-MM-dd HH:mm:ss");
        }
        return expireDate;
    }

    public String getDepartIds() {
        if(departIds==null){
            departIds = new String();
        }
        return departIds;
    }
}
