package com.zzy.task.client.domain;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.util.TypeUtils;
import com.zzy.core.constant.DataBaseConstant;
import com.zzy.core.utils.UserDataUtil;
import com.zzy.task.common.db.entity.HotelCommentInfo;
import com.zzy.task.common.db.entity.RestaurantCommentInfo;
import com.zzy.task.common.db.entity.ScenicCommentInfo;
import com.zzy.task.common.util.DomainUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class MeiTuanComment {

    private String placeTitle;
    private String userName;  //"匿名用户",
    private String avgPrice;  //110,
    private String comment;  //"我们走的是北大门 离湖还有三公里左右 景区内门口有中巴车票价5元一位 可以选择坐车或者步行进湖!我们选择步行进湖. 到了南北湖里面的交通还要收费 有开往山顶看海和跨海大桥的中巴车，还有绕南北湖中巴车与观光车 都要收费!  湖边还停放着哈罗四轮自行车 有双人与四人骑的  支付宝扫一扫就可以骑了押金200元 二人车20元一小时 四人车60元一小时!  南北湖绕湖有2个景点都要验票  南北湖湖边设施很完善 休息的椅子和石凳很多 走累了不怕没出休息!  我们全程步行绕湖连中饭时间一共用了7个小时走完了南北湖是! 风景嘛还不错 就是湖水不是特别干净有点混浊 视觉感差了点! 图中这家饭店价格实惠 味道烧的也不错4个人 六个菜 加一瓶大瓶雪碧 收215元。",
    private String commentTime;  //"1570171841399",
    private String readCnt;  //7078,
    private String star;  //40,
    private String menu;  //"南北湖景区（双人票）",
    private String reviewId;
    private boolean anonymous;  //true,
    private String placeKw;

    public String getPlaceKw() {
        return placeKw;
    }


    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public MeiTuanComment setPlaceKw(String placeKw) {
        this.placeKw = placeKw;
        return this;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public MeiTuanComment setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if(StrUtil.isBlankOrUndefined(userName)){
            this.userName="美团用户";
        }
        this.userName = userName;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getReadCnt() {
        return readCnt;
    }

    public void setReadCnt(String readCnt) {
        this.readCnt = readCnt;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }



    public RestaurantCommentInfo toRestautantComment(){
        RestaurantCommentInfo info = new RestaurantCommentInfo();
        info.setCommentContent(DomainUtil.removeEmojiUtf8mb4(this.getComment()));
        info.setCommentPlaceName(this.getPlaceTitle());
        info.setCommentSource(DataBaseConstant.SOURCE_PY_MEITUAN.getCode());
        if(this.getCommentTime().contains("更新")){
            this.setCommentTime(this.getCommentTime().split("更")[0].replace(" ", ""));
        }
        info.setCommentTime(TypeUtils.castToDate(this.getCommentTime()));
        info.setCommentUser(UserDataUtil.toAnonymousStr(this.getUserName()));
        info.setCreateTime(new Date());
        if(StrUtil.isBlank(this.getStar())){
            this.setStar("-1");
            info.setCommentType(-1);
        }
        double s = Double.parseDouble(this.getStar());
        info.setCommentScore(s/10.0);
        if(NumberUtil.isGreaterOrEqual(BigDecimal.valueOf(s),BigDecimal.valueOf(4))){
            info.setCommentType(1);
        }else if(NumberUtil.isGreaterOrEqual(BigDecimal.valueOf(s),BigDecimal.valueOf(3))
                && NumberUtil.isLess(BigDecimal.valueOf(s),BigDecimal.valueOf(4))){
            info.setCommentType(2);
        }else if(NumberUtil.isLess(BigDecimal.valueOf(s),BigDecimal.valueOf(3.0))){
            info.setCommentType(0);
        }
        if(StrUtil.isBlank(info.getCommentContent())){
            info.setCommentContent("系统默认评论");
        }
        return info;
    }



    public ScenicCommentInfo toScenicComment(){
        ScenicCommentInfo info = new ScenicCommentInfo();
        info.setPlaceKeyWord(this.getPlaceKw());
        info.setCommentContent(DomainUtil.removeEmojiUtf8mb4(this.getComment()));
        info.setCommentPlaceName(this.getPlaceTitle());
        info.setCommentSource(DataBaseConstant.SOURCE_PY_MEITUAN.getCode());
        if(this.getCommentTime().contains("更新")){
            this.setCommentTime(this.getCommentTime().split("更")[0].replace(" ", ""));
        }
        info.setCommentTime(TypeUtils.castToDate(this.getCommentTime()));
        info.setCommentUser(UserDataUtil.toAnonymousStr(this.getUserName()));
        info.setCreateTime(new Date());
        if(StrUtil.isBlank(this.getStar())){
            this.setStar("-1");
            info.setCommentType(-1);
        }
        double s = Double.parseDouble(this.getStar());
        info.setCommentScore(s/10.0);
        if(NumberUtil.isGreaterOrEqual(BigDecimal.valueOf(s),BigDecimal.valueOf(4))){
            info.setCommentType(1);
        }else if(NumberUtil.isGreaterOrEqual(BigDecimal.valueOf(s),BigDecimal.valueOf(3))
                && NumberUtil.isLess(BigDecimal.valueOf(s),BigDecimal.valueOf(4))){
            info.setCommentType(2);
        }else if(NumberUtil.isLess(BigDecimal.valueOf(s),BigDecimal.valueOf(3.0))){
            info.setCommentType(0);
        }
        if(StrUtil.isBlank(info.getCommentContent())){
            info.setCommentContent("系统默认评论");
        }
        return info;
    }


    public HotelCommentInfo toHotelComment(){
        HotelCommentInfo info = new HotelCommentInfo();
        info.setCommentContent(DomainUtil.removeEmojiUtf8mb4(this.getComment()));
        info.setCommentPlaceName(this.getPlaceTitle());
        info.setCommentSource(DataBaseConstant.SOURCE_PY_MEITUAN.getCode());
        if(this.getCommentTime().contains("更新")){
            this.setCommentTime(this.getCommentTime().split("更")[0].replace(" ", ""));
        }
        info.setCommentTime(TypeUtils.castToDate(this.getCommentTime()));
        info.setCommentUser(UserDataUtil.toAnonymousStr(this.getUserName()));
        info.setCreateTime(new Date());
        if(StrUtil.isBlank(this.getStar())){
            this.setStar("-1");
            info.setCommentType(-1);
        }
        double s = Double.parseDouble(this.getStar());
        info.setCommentScore(s/10.0);
        if(NumberUtil.isGreaterOrEqual(BigDecimal.valueOf(s),BigDecimal.valueOf(4))){
            info.setCommentType(1);
        }else if(NumberUtil.isGreaterOrEqual(BigDecimal.valueOf(s),BigDecimal.valueOf(3))
                && NumberUtil.isLess(BigDecimal.valueOf(s),BigDecimal.valueOf(4))){
            info.setCommentType(2);
        }else if(NumberUtil.isLess(BigDecimal.valueOf(s),BigDecimal.valueOf(3.0))){
            info.setCommentType(0);
        }
        if(StrUtil.isBlank(info.getCommentContent())){
            info.setCommentContent("系统默认评论");
        }
        info.setPlaceKeyWord(this.getPlaceKw());
        return info;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeiTuanComment that = (MeiTuanComment) o;
        return userName.equals(that.userName) &&
                Objects.equals(avgPrice, that.avgPrice) &&
                Objects.equals(comment, that.comment) &&
                commentTime.equals(that.commentTime) &&
                Objects.equals(readCnt, that.readCnt) &&
                Objects.equals(star, that.star) &&
                Objects.equals(menu, that.menu) &&
                reviewId.equals(that.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, avgPrice, comment, commentTime, readCnt, star, menu, reviewId);
    }
}
