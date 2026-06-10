package com.nuwa.client.attract.co;

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

    private Long mchId;

    private Long target_id;

    // 文件类型: 1:文章 2:图片 3:视频,4其他,5文件
    private Integer fileType;

    // 资源类型 1.奖励政策  2.营业执照
    private Integer targeType;

}
