package com.zzy.task.common.constant;

public enum XieChengLocateHtml {

    HAIYAN_HTML("/haiyan529"),
    ZHOUSHAN_HTML("/zhoushan479"),
    JIUZHAIGOU_HTML("/jiuzhaigou25"),
    getXieChengList_KEYWORD_PUTUO("普陀"),
    getXieChengList_KEYWORD_JZG("九寨沟"),



    ;



    private String path;
    XieChengLocateHtml(String html){
        this.path = html;
    }

    public String getPath(){
        return path;
    }


    public String getDbKw(){
        if(HAIYAN_HTML.equals(this)){
            return "haiyan";
        }else if(ZHOUSHAN_HTML.equals(this) || getXieChengList_KEYWORD_PUTUO.equals(this)){
            return "putuo";
        }else if(JIUZHAIGOU_HTML.equals(this) || getXieChengList_KEYWORD_JZG.equals(this)){
            return "jiuzhaigou";
        }
        return null;
    }


}
