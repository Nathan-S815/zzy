package com.zzy.db.dao.warning;

import com.zzy.db.entity.warning.NzFileModel;
import com.zzy.db.entity.warning.WarningInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-28
 */
public interface WarningInfoMapper extends BaseMapper<WarningInfo> {
    Integer saveMessage(@Param("address") String address, @Param("addTime") String addTime, @Param("describe") String describe,@Param("longitude") String longitude,@Param("latitude") String latitude, @Param("nzFileModel1") NzFileModel nzFileModel1, @Param("nzFileModel2") NzFileModel nzFileModel2, @Param("nzFileModel3") NzFileModel nzFileModel3, @Param("updateTime") String updateTime);

}
