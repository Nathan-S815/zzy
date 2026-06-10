package com.zzy.db.entity.eventdepart;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 事件执法反馈
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EventFeedback extends Model<EventFeedback> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属事件ID
     */
    private Integer eventId;

    private Integer departmemberId;

    /**
     * 0:待负责人审核,1:负责人审核不通过,2:负责人审核通过等待二级上报,3:待管理员审核,4:管理员审核通过,5:管理员审核不通过
     */
    private Integer checkStatus;

    /**
     * 视频内容路径
     */
    private String videoContentPath;

    private String createUser;

    /**
     * 事件反馈内容
     */
    private String processMsg;

    private Date createTime;

    private Date updateTime;

    private Date checkTime;


    /**
     * 上报等级(1:一级上报,2:二级上报)
     */
    private Integer reportStatus;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
