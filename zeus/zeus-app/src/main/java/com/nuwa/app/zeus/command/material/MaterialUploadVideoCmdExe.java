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

@Slf4j
@Component
public class MaterialUploadVideoCmdExe extends AbstractCmdExe<MaterialUploadCmd, SingleResponse> {
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
        MaterialType materialType = materialTypeService.lambdaQuery()
                .eq(MaterialType::getMchId, cmd.getUserAware().getMchId())
                .eq(MaterialType::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                .eq(MaterialType::getGroupType, 1).one();
        if(materialType==null){
            materialType=new MaterialType();
            materialType.setMchId(cmd.getUserAware().getMchId()).setGroupType(1).setName("默认分组");
            materialTypeService.save(materialType);
        }
        MaterialUploadCO materialUploadCO = cmd.getMaterialUploadCO();
        try {
            MaterialFileTypeEnum videoType = MaterialFileTypeEnum.VIDEO;
            UploadVO uploadVO = null;
            if("minio".equalsIgnoreCase(ossDefault)){
                uploadVO = MinioSender.uploadFileEx(materialUploadCO.getFile(), minioOssConfig.toConfig(), cmd.getUserAware().getMchId() + "",videoType.name());
            } else if ("upYun".equalsIgnoreCase(ossDefault)) {
                uploadVO = UpYunSender.uploadFileEx(materialUploadCO.getFile(), upYunConfig, cmd.getUserAware().getMchId() + "",videoType.name());
            }
            long size = materialUploadCO.getFile().getSize();
            if (StrUtil.isNotBlank(uploadVO.getUrl())) {
                Material material = new Material();
                material.setEndPoint(uploadVO.getEndPoint());
                material.setBucketName(uploadVO.getBucketName());
                if("minio".equalsIgnoreCase(ossDefault)){
                    material.setUrl(ossDomain + "/" + uploadVO.getBucketName() + "/" + uploadVO.getPathFileName());
                } else if ("upYun".equalsIgnoreCase(ossDefault)) {
                    material.setUrl(uploadVO.getUrl());
                }
                material.setOssKey(uploadVO.getPathFileName());
                material.setTargetType(MaterialTargetEnum.OTHER.getCode());
                material.setUsedTimes(0L);
                material.setOssChannel(ossDefault);//minio
                material.setFileType(videoType.getCode());
                material.setFileSize(size);
                material.setCreateTime(new Date());
                material.setMchId(cmd.getUserAware().getMchId());
                if(materialUploadCO.getTypeId()==null){
                    materialUploadCO.setTypeId(materialType.getId());
                }
                material.setTypeId(materialUploadCO.getTypeId());
                material.setRealName(materialUploadCO.getFile().getOriginalFilename());
                boolean save = materialService.save(material);
                if (save) {
                    return SingleResponse.of(new MaterialVO(material.getId(), material.getUrl()));
                }
            }
        } catch (Exception ex) {
            log.error("MinioSender.UploadVO error.", ex);
            return SingleResponse.buildFailure("-99", "上传视频失败");
        }
        return SingleResponse.buildFailure("-99", "上传视频失败");
    }

    @Data
    @AllArgsConstructor
    public static class MaterialVO {
        private Long materialId;
        private String url;
    }
}
