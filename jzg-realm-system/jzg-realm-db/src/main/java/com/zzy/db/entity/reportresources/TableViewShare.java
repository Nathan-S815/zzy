package com.zzy.db.entity.eventdepart.reportresources;

import java.io.Serializable;


public class TableViewShare implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String tableName;

    /**
     * 
     */
    private String tableType;

    /**
     * 
     */
    private String tabType;

    /**
     * table_view_share
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
     * @return table_name 
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 
     * @param tableName 
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 
     * @return table_type 
     */
    public String getTableType() {
        return tableType;
    }

    /**
     * 
     * @param tableType 
     */
    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    /**
     * 
     * @return tab_type 
     */
    public String getTabType() {
        return tabType;
    }

    /**
     * 
     * @param tabType 
     */
    public void setTabType(String tabType) {
        this.tabType = tabType;
    }
}