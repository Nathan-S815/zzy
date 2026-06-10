package com.zzy.task.client.domain;

import com.alibaba.fastjson.JSONObject;

public class XieChengCommentJsonReq {

    private int viewid; //16166,
    private int pagenum; //1,
    private int pagesize; //10,
    private String contentType = "json"; //"json",
    private JSONObject head = new JSONObject(); //{ },
    private String ver = "7.10.3.0319180000"; //"7.10.3.0319180000"

    public XieChengCommentJsonReq(int viewid, int pagenum, int pagesize) {
        this.viewid = viewid;
        this.pagenum = pagenum;
        this.pagesize = pagesize;
    }

    public int getViewid() {
        return viewid;
    }

    public void setViewid(int viewid) {
        this.viewid = viewid;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public JSONObject getHead() {
        return head;
    }

    public void setHead(JSONObject head) {
        this.head = head;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }
}
