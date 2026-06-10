package com.zzy.db.entity.eventdepart;

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
 * 事件任务表
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EventInfo extends Model<EventInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 事件名称
     */
    private String eventName;

    /**事件形式(1指派,2认领)*/
    private Integer eventForm;

    /**
     * 事件类型
     */
    private Integer eventTypeId;

    /**
     * 事件状态(1:待分配, 2:已分配待确认,3:已确认处理中,4:失效,5:结束,6:需强制指定,7:待管理员审核中,9:审核通过,10:再处理)',
     */
    private Integer eventStatus;

    /**
     * 事件等级(1:一般,2:较大,3:重大,4:特别重大)
     */
    private Integer eventLevel;

    /**
     * 事件内容
     */
    private String eventContent;

    /**
     * 事件发布人
     */
    private String eventPublisher;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private Date expireDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 完结备注
     */
    private String closeRemark;


    private Integer isDelete;

    private Integer isSingle;

    /**
     * 上报状态(1:一级上报,2:二级上报)
     */
    private Integer reportStatus;

    private Integer reportMemberId;


    private String lng;

    private String lat;

    private String address;

    private Date happenTime;

    /**1是,0否*/
    private Integer isNeedIntervene;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
