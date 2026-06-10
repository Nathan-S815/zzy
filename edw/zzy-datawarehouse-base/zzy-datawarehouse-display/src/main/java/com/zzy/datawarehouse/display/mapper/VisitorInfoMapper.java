package com.zzy.datawarehouse.display.mapper;

import com.zzy.datawarehouse.display.entity.VisitorInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 游客信息表
 * 
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:45
 */
@Mapper
public interface VisitorInfoMapper extends BaseMapper<VisitorInfo> {
	
}
