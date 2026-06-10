package com.zzy.task.common.db.entity;

import java.io.Serializable;
import java.util.Date;

public class MethodRunningState implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String methodName;

    /**
     * 
     */
    private Integer isRunning;

    /**
     * 
     */
    private Date startTime;

    /**
     * 
     */
    private Date endTime;

    /**
     * 
     */
    private String source;

    /**
     * crawler_base..method_running_state
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return method_name 
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * 
     * @param methodName 
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * 
     * @return is_running 
     */
    public Integer getIsRunning() {
        return isRunning;
    }

    /**
     * 
     * @param isRunning 
     */
    public void setIsRunning(Integer isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * 
     * @return start_time 
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 
     * @param startTime 
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     * @return end_time 
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 
     * @param endTime 
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 
     * @return source 
     */
    public String getSource() {
        return source;
    }

    /**
     * 
     * @param source 
     */
    public void setSource(String source) {
        this.source = source;
    }
}