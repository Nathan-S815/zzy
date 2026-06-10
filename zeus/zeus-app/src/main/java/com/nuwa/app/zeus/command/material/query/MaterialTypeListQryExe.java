package com.nuwa.app.zeus.command.material.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.material.qry.MaterialTypeListQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.material.entity.Material;
import com.nuwa.infrastructure.zeus.database.material.entity.MaterialType;
import com.nuwa.infrastructure.zeus.database.material.service.MaterialService;
import com.nuwa.infrastructure.zeus.database.material.service.MaterialTypeService;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GetBaseGroupByIdQryExe
 *
 * @author hy
 * @date 2021/5/25 16:32
 * @since 1.0.0
 */
@Slf4j
@Component
public class MaterialTypeListQryExe extends AbstractCmdExe<MaterialTypeListQry, SingleResponse<List<MaterialTypeListQryExe.MaterialTypeVo>>> {

    @Autowired
    private MaterialTypeService materialTypeService;

    @Autowired
    private MaterialService materialService;

    @Override
    protected SingleResponse<List<MaterialTypeListQryExe.MaterialTypeVo>> handle(MaterialTypeListQry cmd) {
        MaterialType materialType = materialTypeService.lambdaQuery()
                .eq(MaterialType::getMchId, cmd.getUserAware().getMchId())
                .eq(MaterialType::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                .eq(MaterialType::getGroupType, 1).one();
        if (materialType == null) {
            materialType = new MaterialType();
            materialType.setMchId(cmd.getUserAware().getMchId()).setGroupType(1).setName("默认分组");
            materialTypeService.save(materialType);
        }
        List<MaterialTypeVo> collect = materialTypeService.lambdaQuery()
                .eq(MaterialType::getMchId, cmd.getUserAware().getMchId())
                .eq(MaterialType::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode())
                .orderByDesc(MaterialType::getGroupType)
                .list().stream().map(x -> {
                    MaterialTypeVo vo = new MaterialTypeVo();
                    vo.setId(x.getId());
                    vo.setName(x.getName());
                    vo.setCreateTime(x.getCreateTime());
                    Integer count = materialService.lambdaQuery().eq(Material::getTypeId, x.getId()).count();
                    vo.setNum(count);
                    return vo;
                }).collect(Collectors.toList());
        return SingleResponse.of(collect);
    }

    @Data
    public static class MaterialTypeVo {
        @ApiModelProperty("id")
        private Long id;
        @ApiModelProperty("分类名称")
        private String name;
        @ApiModelProperty("创建时间")
        private Date createTime;
        @ApiModelProperty("图片数量")
        private Integer num;
    }
}
