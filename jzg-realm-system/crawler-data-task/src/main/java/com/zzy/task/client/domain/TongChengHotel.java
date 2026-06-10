package com.zzy.task.client.domain;

import cn.hutool.core.util.StrUtil;
import com.zzy.core.constant.DataBaseConstant;
import com.zzy.task.common.db.entity.PlaceInfo;

import java.util.Date;

public class TongChengHotel {
    private String Id; //"31241005",
    private String Name; //"蝶来海盐宾馆",
    private String CityName; //"嘉兴市",
    private String Section; //"海盐县",
    private String Address; //"<i class="add">海滨东路35号</i>",
    private Integer CmtNum; //2951,  评论数量
    private String Pf; //"4.5",
    private String latitude;
    private String longitude;

    private String kw;

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Integer getCmtNum() {
        return CmtNum;
    }

    public void setCmtNum(Integer cmtNum) {
        CmtNum = cmtNum;
    }

    public String getPf() {
        return Pf;
    }

    public void setPf(String pf) {
        Pf = pf;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public PlaceInfo toDataBasePlace(){
        PlaceInfo info = new PlaceInfo();
        if(null == this.getCmtNum()){
            info.setCommentNum(0);
        }else{
            info.setCommentNum(this.getCmtNum());
        }
        info.setLatitude(this.getLatitude());
        info.setLongitude(this.getLongitude());
        info.setPlaceAddress(this.getAddress());
        info.setPlaceName(this.getName());
        if(StrUtil.isBlankOrUndefined(this.getPf())){
            info.setPlaceScore("-1");
        }else {
            info.setPlaceScore(this.getPf());
        }
        info.setPlaceSource(DataBaseConstant.SOURCE_PY_TONGCHENGLVYOU.getCode());
        info.setPlaceType(DataBaseConstant.PLACE_Type_HOTEL.getCode());
        info.setSiteHref("-");
        info.setKeyWord(this.getKw());
        info.setUpdateTime(new Date());
        return info;
    }
}
