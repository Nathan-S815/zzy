package com.zzy.db.entity.reportbase;

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
 * 
 * </p>
 *
 * @author zzy
 * @since 2020-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ReportBaseGuide extends Model<ReportBaseGuide> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 导游姓名
     */
    private String guideName;

    /**
     * 导游证号码
     */
    private String guideLicenseNumber;

    /**
     * 导游级别
     */
    private String guideLevel;

    /**
     * 导游语种
     */
    private String guideLanguage;

    /**
     * 所在机构
     */
    private String mechanism;

    /**
     * 导游证
     */
    private String license;

    /**
     * 信用分
     */
    private Integer credit;

    private Integer isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
