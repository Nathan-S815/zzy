package com.nuwa.infrastructure.discovery.database.material.service.impl;


import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.discovery.database.material.entity.Material;
import com.nuwa.infrastructure.discovery.database.material.mapper.MaterialMapper;
import com.nuwa.infrastructure.discovery.database.material.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-04
 */
@Slf4j
@Service
public class MaterialServiceImpl extends SuperServiceImpl<MaterialMapper, Material> implements MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

}
