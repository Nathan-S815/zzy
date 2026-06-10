package com.nuwa.client.zeus.dto.clientobject.material.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "上传图片视频")
public class MaterialUploadCO extends NuwaCO {

    private MultipartFile file;

    private Long typeId;

}
