package com.nuwa.infrastructure.ticket.database.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 *
 * @author huyonghack@163.com
 * @since 2022-03-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ArticleCategory对象")
public class ArticleCategory extends Model<ArticleCategory> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("上级节点")
    private Integer parentId;

    @ApiModelProperty("树状关系")
    private String path;

    private Date createTime;

    private Date updateTime;

    @ApiModelProperty("级别 默认1")
    private Integer level;

    @ApiModelProperty("商户Id")
    private Long mchId;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String PARENT_ID = "parent_id";

    public static final String PATH = "path";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String LEVEL = "level";

    public static final String MCH_ID = "mch_id";

}
