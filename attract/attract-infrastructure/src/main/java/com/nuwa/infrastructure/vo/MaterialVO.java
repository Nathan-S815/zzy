package com.nuwa.infrastructure.vo;



import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "获取奖励列表文件")
public class MaterialVO  extends Model<MaterialVO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文件码")
    private Long id;

    @ApiModelProperty("文件类型  1:文章 2:图片 3:视频,4其他")
    private Integer fileType;

    @ApiModelProperty("访问地址url")
    private String url;

    @ApiModelProperty("文件名称")
    private String realName;

    @ApiModelProperty("资源类型")
    private Integer targetType;

}
