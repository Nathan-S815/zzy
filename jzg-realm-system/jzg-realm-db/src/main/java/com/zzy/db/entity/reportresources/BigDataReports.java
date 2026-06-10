package com.zzy.db.entity.eventdepart.reportresources;

import java.io.Serializable;
import java.util.Date;

public class BigDataReports implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 报告名称
     */
    private String reportName;

    /**
     * 报告主题(1年报,2月报,3春节,4五一,5国庆)
     */
    private Integer reportSubject;

    /**
     * 报告周期
     */
    private String reportCycle;

    /**
     * 报告状态(1发布,0未发布)
     */
    private Integer reportState;

    /**
     * 场所数量相关信息
     */
    private String placeNum;

    /**
     * 景区客流量
     */
    private String scenicPassenger;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * big_data_reports
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 报告名称
     * @return report_name 报告名称
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * 报告名称
     * @param reportName 报告名称
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * 报告主题(1年报,2月报,3春节,4五一,5国庆)
     * @return report_subject 报告主题(1年报,2月报,3春节,4五一,5国庆)
     */
    public Integer getReportSubject() {
        return reportSubject;
    }

    /**
     * 报告主题(1年报,2月报,3春节,4五一,5国庆)
     * @param reportSubject 报告主题(1年报,2月报,3春节,4五一,5国庆)
     */
    public void setReportSubject(Integer reportSubject) {
        this.reportSubject = reportSubject;
    }

    /**
     * 报告周期
     * @return report_cycle 报告周期
     */
    public String getReportCycle() {
        return reportCycle;
    }

    /**
     * 报告周期
     * @param reportCycle 报告周期
     */
    public void setReportCycle(String reportCycle) {
        this.reportCycle = reportCycle;
    }

    /**
     * 报告状态(1发布,0未发布)
     * @return report_state 报告状态(1发布,0未发布)
     */
    public Integer getReportState() {
        return reportState;
    }

    /**
     * 报告状态(1发布,0未发布)
     * @param reportState 报告状态(1发布,0未发布)
     */
    public void setReportState(Integer reportState) {
        this.reportState = reportState;
    }

    /**
     * 场所数量相关信息
     * @return place_num 场所数量相关信息
     */
    public String getPlaceNum() {
        return placeNum;
    }

    /**
     * 场所数量相关信息
     * @param placeNum 场所数量相关信息
     */
    public void setPlaceNum(String placeNum) {
        this.placeNum = placeNum;
    }

    /**
     * 景区客流量
     * @return scenic_passenger 景区客流量
     */
    public String getScenicPassenger() {
        return scenicPassenger;
    }

    /**
     * 景区客流量
     * @param scenicPassenger 景区客流量
     */
    public void setScenicPassenger(String scenicPassenger) {
        this.scenicPassenger = scenicPassenger;
    }

    /**
     * 创建人
     * @return create_user 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     * @return update_time 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 是否删除
     * @return is_delete 是否删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 是否删除
     * @param isDelete 是否删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}