package com.nuwa.infrastructure.ticket.database.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 资讯表
 *
 * @author huyonghack@163.com
 * @since 2022-03-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ArticleInfo对象")
public class ArticleInfo extends Model<ArticleInfo> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商户Id")
    private Long mchId;

    @ApiModelProperty("一级分类Id")
    private Long categoryOne;

    @ApiModelProperty("二级分类Id")
    private Long categorySecond;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("封面图")
    @JsonSerialize(using = MaterialJson.class)
    private String cover;

    @ApiModelProperty("关键字")
    private String keyword;

    @ApiModelProperty("点赞数")
    private Long liked;

    @ApiModelProperty("浏览次数")
    private Long views;

    @ApiModelProperty("上下架 0:下架 1:上架")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("删除标志[0正常 1删除]")
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String CATEGORY_ONE = "category_one";

    public static final String CATEGORY_SECOND = "category_second";

    public static final String TITLE = "title";

    public static final String CONTENT = "content";

    public static final String COVER = "cover";

    public static final String KEYWORD = "keyword";

    public static final String LIKED = "liked";

    public static final String VIEWS = "views";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
