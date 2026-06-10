package com.nuwa.client.zeus.dto.clientobject.app;

import com.nuwa.client.zeus.dto.clientobject.app.co.AppSkuInfoCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * CreateAppCmd 创建应用
 *
 * @author hy
 * @date 2021/5/31 13:33
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "上架应用-命令")
public class OnAppCmd extends NuwaCommand {

    private Long id;

}
