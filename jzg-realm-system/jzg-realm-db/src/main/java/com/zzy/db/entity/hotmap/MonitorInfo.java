package com.zzy.db.entity.hotmap;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 监控信息表
 * </p>
 *
 * @author zzy
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MonitorInfo extends Model<MonitorInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 景区类别
     */
    private String scenicType;

    /**
     * 监控点位名称
     */
    private String monitorName;

    /**
     * rtmp地址
     */
    private String rtmpAddress;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * rtsp地址
     */
    private String rtspAddress;

    /**
     * rtsp地址
     */
    private String sn;





    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
