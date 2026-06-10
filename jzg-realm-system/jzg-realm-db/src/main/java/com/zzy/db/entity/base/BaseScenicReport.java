package com.zzy.db.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 景区人数收入上报表
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseScenicReport extends Model<BaseScenicReport> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属景区id
     */
    private String subScenicId;

    /**
     * 所属景区名称
     */
    private String subScenicName;

    /**
     * 进入人数
     */
    private String inPeople;

    /**
     * 出去人数
     */
    private String outPeople;

    /**
     * 收入
     */
    private String income;

    /**
     * 上报事项
     */
    private String reportMatters;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除
     */
    private String idDelete;


    private Integer userId;

    private Date reportTime;

    /**
     * 总人数
     */
    private String totalNum;

    /**
     * 团队人数
     */
    private String teamNum;

    /**
     * 散客人数
     */
    private String aloneNum;

    /**
     * 投诉救助情况
     */
    private String complaintsRescue;

    /**
     * 餐饮服务保障人数
     */
    private String foodServiceNum;

    /**
     * 网络舆情监测情况
     */
    private String sentimentState;

    /**
     * 微信微博同步推送美文数量
     */
    private String wechatArticleNum;

    /**
     * 预约总人数
     */
    private String reserveTotalNum;

    /**
     * 预约团队人数
     */
    private Integer reserveTeamNum;

    /**
     * 预约散客人数
     */
    private Integer reserveAloneNum;

    /**
     * top1省份
     */
    private String provinceTop1;

    /**
     * top1省份数量
     */
    private String top1Count;

    /**
     * top2省份
     */
    private String provinceTop2;

    /**
     * top2省份数量
     */
    private String top2Count;

    /**
     * top3省份
     */
    private String provinceTop3;

    /**
     * top3省份数量
     */
    private String top3Count;

    /**
     * top4省份
     */
    private String provinceTop4;

    /**
     * top4省份数量
     */
    private String top4Count;

    /**
     * top5省份
     */
    private String provinceTop5;

    /**
     * top5省份数量
     */
    private String top5Count;

    /**
     * top6省份
     */
    private String provinceTop6;

    /**
     * top6省份数量
     */
    private String top6Count;

    /**
     * top7省份
     */
    private String provinceTop7;

    /**
     * top7省份数量
     */
    private String top7Count;

    /**
     * top8省份
     */
    private String provinceTop8;

    /**
     * top8省份数量
     */
    private String top8Count;

    /**
     * top9省份
     */
    private String provinceTop9;

    /**
     * top9省份数量
     */
    private String top9Count;

    /**
     * top10省份
     */
    private String provinceTop10;

    /**
     * top10省份数量
     */
    private String top10Count;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
