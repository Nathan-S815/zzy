package com.zzy.db.dao.reportbase;

import com.zzy.db.entity.reportbase.ReportBaseShopping;
import com.zzy.db.entity.reportbase.ReportBaseShopping;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物上报基础信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface ReportBaseShoppingMapper extends BaseMapper<ReportBaseShopping> {
    List<ReportBaseShopping> selectPageListBaseShopping(@Param("keyword")String keyword);
}
