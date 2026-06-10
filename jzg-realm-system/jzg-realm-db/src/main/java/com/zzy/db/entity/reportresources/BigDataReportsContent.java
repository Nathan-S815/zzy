package com.zzy.db.entity.eventdepart.reportresources;

import java.io.Serializable;

public class BigDataReportsContent implements Serializable {
    /**
     * 主键
     */
    private Integer ptBigDataReportsId;

    /**
     * 
     */
    private String reportContentPart;

    /**
     * big_data_reports_content
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * @return pt_big_data_reports_id 主键
     */
    public Integer getPtBigDataReportsId() {
        return ptBigDataReportsId;
    }

    /**
     * 主键
     * @param ptBigDataReportsId 主键
     */
    public void setPtBigDataReportsId(Integer ptBigDataReportsId) {
        this.ptBigDataReportsId = ptBigDataReportsId;
    }

    /**
     * 
     * @return report_content_part 
     */
    public String getReportContentPart() {
        return reportContentPart;
    }

    /**
     * 
     * @param reportContentPart 
     */
    public void setReportContentPart(String reportContentPart) {
        this.reportContentPart = reportContentPart;
    }
}