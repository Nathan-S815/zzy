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
 * 搜索配置
 *
 * @author huyonghack@163.com
 * @since 2022-09-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SearchConf对象")
public class SearchConf extends Model<SearchConf> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商户id")
    private Integer mchId;

    @ApiModelProperty("搜索关键字")
    private String keyword;

    @ApiModelProperty("数据名称")
    private String dataTitle;

    @ApiModelProperty("数据类别(hotel(酒店) scenicspot(景区)  wenchuang(文创) meishi(美食)")
    private String dataType;

    @ApiModelProperty("数据id(详情id)")
    private String dataId;

    @ApiModelProperty("显示方式(list(列表) detail(详情))")
    private String viewMode;

    @ApiModelProperty("应用id")
    private String appId;

    @ApiModelProperty("排序")
    private Integer orderNum;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String KEYWORD = "keyword";

    public static final String DATA_TITLE = "data_title";

    public static final String DATA_TYPE = "data_type";

    public static final String DATA_ID = "data_id";

    public static final String VIEW_MODE = "view_mode";

    public static final String APP_ID = "app_id";

    public static final String ORDER_NUM = "order_num";

}
