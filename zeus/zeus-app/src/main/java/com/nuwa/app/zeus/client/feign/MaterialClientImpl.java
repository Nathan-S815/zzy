package com.nuwa.app.zeus.client.feign;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.material.MaterialUploadImageCmdExe;
import com.nuwa.client.zeus.api.order.MaterialClientI;
import com.nuwa.infrastructure.zeus.config.OssDomainConfigEx;
import com.nuwa.infrastructure.zeus.database.material.entity.Material;
import com.nuwa.infrastructure.zeus.database.material.mapper.MaterialMapper;
import com.nuwa.infrastructure.zeus.util.MaterialJson;
import com.nuwa.infrastructure.zeus.util.SpringContextKit;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * OrderServiceClientImpl
 *
 * @author hy
 * @date 2021/4/23 10:24
 * @since 1.0.0
 */
@Slf4j
@RestController
public class MaterialClientImpl implements MaterialClientI {

    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public SingleResponse getMaterialByIds(List<Long> ids) {
        List<Material> materialListData = materialMapper.lambdaQueryChain()
                .in(Material::getId, ids)
                .eq(Material::getStatus, 1)
                .list();

        Set<Long> setMaterialId = materialListData.stream().map(Material::getId).collect(Collectors.toSet());
        List<Material> materialItems = new ArrayList<>(materialListData);
        ids.forEach(id -> {
            if (!setMaterialId.contains(id)) {
                Material material = materialMapper.selectById(1L); //默认图片
                materialItems.add(material);
            }
        });

        List<MaterialJson.MaterialVO> materialList = materialItems.stream()
                .map(this::urlConvert)
                .map(MaterialJson.MaterialVO::toVO)
                .collect(Collectors.toList());

        return SingleResponse.of(materialList);
    }

    public Material urlConvert(Material material) {
        OssDomainConfigEx ossDomainConfig = SpringContextKit.getBean(OssDomainConfigEx.class);
        String ossChannel = material.getOssChannel();
        if ("minio".equalsIgnoreCase(ossChannel)) {
            if (StrUtil.isBlank(ossDomainConfig.getOssDomain())) {
                return material;
            }
            material.setUrl(ossDomainConfig.getOssDomain() + "/" + material.getBucketName() + "/" + material.getOssKey());
        } else if ("upYun".equalsIgnoreCase(ossChannel)) {
            material.setUrl(ossDomainConfig.getUpDomain() + "/" + material.getOssKey());
        }
        return material;
    }

    @Data
    @AllArgsConstructor
    public static class MaterialVO {

        @ApiModelProperty(value = "素材id")
        private Long id;

        @ApiModelProperty(value = "图片url")
        private String url;

        @ApiModelProperty(value = "文件类型")
        private Integer fileType;

        public static MaterialVO toVO(Material entity) {
            return new MaterialVO(entity.getId(), entity.getUrl(), entity.getFileType());
        }
    }
}
