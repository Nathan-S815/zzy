package com.zzy.db.entity.eventdepart;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 事件部员关联
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EventDepartMember extends Model<EventDepartMember> {

    private static final long serialVersionUID = 1L;

    /**
     * 事件ID
     */
    private Integer eventId;

    /**
     * 部员ID
     */
    private Integer departMemberId;

    /**
     * 是否通知(1:是,0否)
     */
    private Integer isNotify;

    /**
     * 是否认领
     */
    private Integer isAssigned;

    /**
     * 认领时间
     */
    private Date assignedTime;

    /**
     * 通知时间
     */
    private Date notifyTime;

    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.eventId;
    }

}
