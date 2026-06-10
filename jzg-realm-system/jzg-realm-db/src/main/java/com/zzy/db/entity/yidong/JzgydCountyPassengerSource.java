package com.zzy.db.entity.yidong;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 反馈阿坝九寨沟县市内各区县来源地客流数据(日月)
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JzgydCountyPassengerSource extends Model<JzgydCountyPassengerSource> {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 区域id
     */
    private String scenicId;

    /**
     * 区域名称
     */
    private String scenicName;

    /**
     * 人数
     */
    private Integer peopleNum;

    private String time;

    /**
     * 区县id
     */
    private String countyId;

    /**
     * 区县name
     */
    private String countyName;

    /**
     * 请求参数日期
     */
    private Integer dateLog;

    /**
     * 1day,2month
     */
    private Integer dateType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
