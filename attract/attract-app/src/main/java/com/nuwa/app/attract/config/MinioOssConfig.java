package com.nuwa.app.attract.config;

import com.nuwa.framework.oos.starter.oos.minio.MinioConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author nanHuang @南皇
 * @version com.nuwa.app.attract.config:MinioOssConfig.java,v1.0.0 2022-09-08 10:29:52 nanHuang Exp $
 */
@Data
public class MinioOssConfig {
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretAccessKey}")
    private String secretAccessKey;
    @Value("${minio.endPoint}")
    private String endPoint;
    @Value("${minio.bucketName}")
    private String bucketName;
    public MinioConfig toConfig() {
        MinioConfig minioConfig = new MinioConfig();
        minioConfig.setAccessKey(this.accessKey);
        minioConfig.setSecretAccessKey(this.secretAccessKey);
        minioConfig.setBucketName(this.bucketName);
        minioConfig.setEndPoint(this.endPoint);
        return minioConfig;
    }
}
