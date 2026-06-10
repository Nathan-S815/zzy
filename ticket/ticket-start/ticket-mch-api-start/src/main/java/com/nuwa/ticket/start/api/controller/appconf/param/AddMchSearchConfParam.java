package com.nuwa.ticket.start.api.controller.appconf.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AddMchSearchConfParam 添加搜索配置")
public class AddMchSearchConfParam extends NuwaCommand {

    @ApiModelProperty("搜索关键字")
    @NotBlank(message = "搜索关键字不能为空")
    private String keyword;

    @ApiModelProperty("数据名称(详情传 'POI名称',列表传 '列表')")
    private String dataTitle;

    @NotBlank(message = "数据类别不能为空")
    @ApiModelProperty("数据类别 hotel(酒店) scenicspot(景区)  wenchuang(文创) meishi(美食)")
    private String dataType;

    @ApiModelProperty("数据id(资源详情id)")
    private String dataId;

    @ApiModelProperty("显示方式(list(列表) detail(详情))")
    private String viewMode;

    @ApiModelProperty("排序")
    private Integer orderNum;
}
