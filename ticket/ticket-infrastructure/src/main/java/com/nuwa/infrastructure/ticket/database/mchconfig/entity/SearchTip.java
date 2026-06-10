package com.nuwa.infrastructure.ticket.database.mchconfig.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 搜索提示配置
 *
 * @author huyonghack@163.com
 * @since 2022-09-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SearchTip对象")
public class SearchTip extends Model<SearchTip> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商户id")
    private Integer mchId;

    @ApiModelProperty("提示配置")
    private String keyword;

    @ApiModelProperty("业务类型")
    private String bizList;

    @ApiModelProperty("排序")
    private Integer orderNum;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String KEYWORD = "keyword";

    public static final String BIZ_LIST = "biz_list";

    public static final String ORDER_NUM = "order_num";

}
