package com.zzy.db.entity.warning;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zzy
 * @since 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WarningInfo extends Model<WarningInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 地点
     */
    private String address;

    /**
     * 时间
     */
    @TableField("addTime")
    private LocalDateTime addTime;

    /**
     * 描述
     */
    private String describe;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 文件1uid
     */
    private String file1Uid;

    /**
     * 文件1名称
     */
    private String file1Name;

    /**
     * 文件1状态
     */
    private String file1Status;

    /**
     * 文件1路径
     */
    private String file1Url;

    /**
     * 文件1类型
     */
    private String file1Type;

    /**
     * 文件2uid
     */
    private String file2Uid;

    /**
     * 文件2名称
     */
    private String file2Name;

    /**
     * 文件2状态
     */
    private String file2Status;

    /**
     * 文件2路径
     */
    private String file2Url;

    /**
     * 文件2类型
     */
    private String file2Type;

    /**
     * 文件3uid
     */
    private String file3Uid;

    /**
     * 文件3名称
     */
    private String file3Name;

    /**
     * 文件3状态
     */
    private String file3Status;

    /**
     * 文件3路径
     */
    private String file3Url;

    /**
     * 文件3类型
     */
    private String file3Type;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
