package com.nuwa.app.zeus.command.material;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.material.MaterialUploadCmd;
import com.nuwa.client.zeus.dto.clientobject.material.co.MaterialUploadCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.framework.oos.starter.config.MinioOssConfig;
import com.nuwa.framework.oos.starter.oos.UploadVO;
import com.nuwa.framework.oos.starter.oos.minio.MinioSender;
import com.nuwa.framework.oos.starter.oos.upyun.UpYunConfig;
import com.nuwa.framework.oos.starter.oos.upyun.UpYunSender;
import com.nuwa.infrastructure.zeus.database.material.entity.Material;
import com.nuwa.infrastructure.zeus.database.material.entity.MaterialType;
import com.nuwa.infrastructure.zeus.database.material.service.MaterialService;
import com.nuwa.infrastructure.zeus.database.material.service.MaterialTypeService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import com.nuwa.infrastructure.zeus.enums.MaterialFileTypeEnum;
import com.nuwa.infrastructure.zeus.enums.MaterialTargetEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class MaterialUploadKeyCmdExe extends AbstractCmdExe<MaterialUploadCmd, SingleResponse> {

    @Autowired
    private MaterialService materialService;

    @Value("${oss.file.domain}")
    private String ossDomain;

    @Value("${oss.default}")
    private String ossDefault;


    @Autowired
    private MinioOssConfig minioOssConfig;

    @Autowired
    private UpYunConfig upYunConfig;
    @Autowired
    private MaterialTypeService materialTypeService;

    @Override
    protected SingleResponse handle(MaterialUploadCmd cmd) {
        MaterialUploadCO materialUploadCO = cmd.getMaterialUploadCO();
        try {
            MaterialFileTypeEnum fileType = MaterialFileTypeEnum.KEY;
            String fileName = materialUploadCO.getFile().getOriginalFilename();
            assert fileName != null;
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

            UploadVO uploadVO = null;
            if ("minio".equalsIgnoreCase(ossDefault)) {
                uploadVO = MinioSender.uploadFileEx(materialUploadCO.getFile(), minioOssConfig.toConfig(), cmd.getUserAware().getMchId() + "", fileType.name());
            } else if ("upYun".equalsIgnoreCase(ossDefault)) {
                uploadVO = UpYunSender.uploadFileEx(materialUploadCO.getFile(), upYunConfig, cmd.getUserAware().getMchId() + "", fileType.name());
            }
            long size = materialUploadCO.getFile().getSize();
            assert uploadVO != null;
            if (StrUtil.isNotBlank(uploadVO.getUrl())) {
                String url = null;
                if ("minio".equalsIgnoreCase(ossDefault)) {
                    url = ossDomain + "/" + uploadVO.getBucketName() + "/" + uploadVO.getPathFileName();
                } else if ("upYun".equalsIgnoreCase(ossDefault)) {
                    url =  uploadVO.getUrl();
                }
                return SingleResponse.of(new MaterialVO(url));
            }
        } catch (Exception ex) {
            log.error("MinioSender.UploadVO error.", ex);
            return SingleResponse.buildFailure("-99", "上传证书失败");
        }
        return SingleResponse.buildFailure("-99", "上传证书失败");
    }

    @Data
    @AllArgsConstructor
    public static class MaterialVO {
        private String url;
    }
}
