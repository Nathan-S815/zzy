package com.nuwa.infrastructure.zeus.database.app.entity;

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
 * 应用依耐表
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AppDependent对象")
public class AppDependent extends Model<AppDependent> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("应用id")
    private Long appId;

    @ApiModelProperty("依赖的应用id")
    private Long dependentAppId;

    @ApiModelProperty("是否为基础应用[0否 1是]")
    private Integer isBaseApp;


    public static final String ID = "id";

    public static final String APP_ID = "app_id";

    public static final String DEPENDENT_APP_ID = "dependent_app_id";

    public static final String IS_BASE_APP = "is_base_app";

}
