package com.nuwa.client.zeus.dto.clientobject.material;

import com.nuwa.client.zeus.dto.clientobject.material.co.MaterialUploadCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "上传图片视频")
public class MaterialUploadCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;
    private MaterialUploadCO materialUploadCO;
}
