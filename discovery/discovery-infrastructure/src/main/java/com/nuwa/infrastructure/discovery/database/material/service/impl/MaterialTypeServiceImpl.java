package com.nuwa.infrastructure.discovery.database.material.service.impl;


import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.discovery.database.material.entity.MaterialType;
import com.nuwa.infrastructure.discovery.database.material.mapper.MaterialTypeMapper;
import com.nuwa.infrastructure.discovery.database.material.service.MaterialTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资源分类 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-04
 */
@Slf4j
@Service
public class MaterialTypeServiceImpl extends SuperServiceImpl<MaterialTypeMapper, MaterialType> implements MaterialTypeService {

    @Autowired
    private MaterialTypeMapper materialTypeMapper;

}
