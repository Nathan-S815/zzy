package com.nuwa.infrastructure.vo;

import lombok.Data;

/**
 * 上传vo
 *
 * @author nanHuang @南皇
 * @version com.nuwa.infrastructure.vo:UploadVO.java,v1.0.0 2022-09-08 10:32:26 nanHuang Exp $
 */
@Data
public class UploadVO {
    private String url;
    private String endPoint;
    private String bucketName;
    private String pathFileName;
}
