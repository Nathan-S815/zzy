package com.nuwa.infrastructure.attract.database.common.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.attract.database.common.entity.Material;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 *  Mapper 接口
 *
 * @author nanhuang @南皇
 * @since 2022-09-08
 */
@Repository
public interface MaterialMapper extends SuperMapper<Material> {


    List<Material> qryRewardList(@PathVariable ("fileTarget") Integer fileTarget);

}
