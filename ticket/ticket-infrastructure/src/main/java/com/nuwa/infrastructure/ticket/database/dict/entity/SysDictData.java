package com.nuwa.infrastructure.ticket.database.dict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 字典数据表
 *
 * @author huyonghack@163.com
 * @since 2021-04-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDictData extends Model<SysDictData> {
    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    @TableId(value = "dict_code", type = IdType.AUTO)
    private Long dictCode;

    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * 字典标签
     */
    private String dictColumn;

    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 是否默认（Y是 N否）
     */
    private String isDefault;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


    public static final String DICT_CODE = "dict_code";

    public static final String DICT_SORT = "dict_sort";

    public static final String DICT_COLUMN = "dict_column";

    public static final String DICT_LABEL = "dict_label";

    public static final String DICT_VALUE = "dict_value";

    public static final String DICT_TYPE = "dict_type";

    public static final String IS_DEFAULT = "is_default";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String REMARK = "remark";

}
