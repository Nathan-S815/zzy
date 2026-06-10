package com.nuwa.app.attract.command;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.Resource;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;

import com.nuwa.client.attract.co.MaterialUploadCO;
import com.nuwa.client.attract.dto.clientobject.MaterialUploadCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.framework.oos.starter.config.MinioOssConfig;
import com.nuwa.framework.oos.starter.oos.UploadVO;
import com.nuwa.framework.oos.starter.oos.minio.MinioSender;
import com.nuwa.infrastructure.attract.database.common.entity.Material;
import com.nuwa.infrastructure.attract.database.common.service.MaterialService;

import com.nuwa.infrastructure.enums.MaterialFileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 上传图片
 *
 * @author nanHuang @南皇
 * @version 2022/9/8 10:30:38 nanHuang Exp $
 */
@Slf4j
@Component
public class MaterialUploadImageCmdExe extends AbstractCmdExe<MaterialUploadCmd, SingleResponse> {

    @Resource
    private MaterialService materialService;

    @Value("${oss.file.domain}")
    private String ossDomain;

    @Value("${oss.default}")
    private String ossDefault;

    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretAccessKey}")
    private String secretAccessKey;
    @Value("${minio.endPoint}")
    private String endPoint;
    @Value("${minio.bucketName}")
    private String bucketName;

    @Resource
    private MinioOssConfig minioOssConfig;
    @Override
    protected SingleResponse handle(MaterialUploadCmd cmd) {
        Assert.notNull(cmd.getMaterialUploadCO().getFile(), "文件不能为空");
        MaterialUploadCO materialUploadCO = cmd.getMaterialUploadCO();
        //MinioOssConfig minioOssConfig = new MinioOssConfig();
        //minioOssConfig.setAccessKey(accessKey);
        //minioOssConfig.setBucketName(bucketName);
        //minioOssConfig.setEndPoint(endPoint);
        //minioOssConfig.setSecretAccessKey(secretAccessKey);
        try {
            MaterialFileTypeEnum imageType = null;
            String fileName = materialUploadCO.getFile().getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            switch (suffix.toUpperCase()) {
                case "IMAGE":
                    imageType = MaterialFileTypeEnum.IMAGE;
                    break;
                case "VIDEO":
                    imageType = MaterialFileTypeEnum.VIDEO;
                    break;
                case "XLS":
                    imageType = MaterialFileTypeEnum.FILE;
                    break;
                case "XLSX":
                    imageType = MaterialFileTypeEnum.FILE;
                    break;
                default:
                    imageType=MaterialFileTypeEnum.KEY;
                    break;
            }

            UploadVO uploadVO = MinioSender.uploadFileEx(materialUploadCO.getFile(), minioOssConfig.toConfig(),
                "0" + "", imageType.name());

            long size = materialUploadCO.getFile().getSize();
            if (StrUtil.isNotBlank(uploadVO.getUrl())) {
                Material material = new Material();
                material.setEndPoint(uploadVO.getEndPoint());
                material.setBucketName(uploadVO.getBucketName());
                material.setUrl(ossDomain + "/" + uploadVO.getBucketName() + "/" + uploadVO.getPathFileName());
                material.setOssKey(uploadVO.getPathFileName());
                material.setUsedTimes(0L);
                material.setMchId(materialUploadCO.getMchId());
                material.setFileType(imageType.getCode());
                material.setOssChannel(ossDefault);
                material.setFileSize(size);
                material.setCreateTime(new Date());
                material.setTargetType(materialUploadCO.getTargeType());
                Long typeId = materialUploadCO.getTypeId();
                material.setTypeId(typeId);
                material.setRealName(materialUploadCO.getFile().getOriginalFilename());
                boolean save = materialService.save(material);
                if (save) {
                    return SingleResponse.of(new MaterialVO(material.getId(), material.getUrl()));
                }
            }
        } catch (Exception ex) {
            log.error("MinioSender.UploadVO error.", ex);
            return SingleResponse.buildFailure("-99", "上传失败");
        }
        return SingleResponse.buildFailure("-99", "上传失败");
    }

    @Data
    @AllArgsConstructor
    public static class MaterialVO {
        private Long   materialId;
        private String url;
    }
}
