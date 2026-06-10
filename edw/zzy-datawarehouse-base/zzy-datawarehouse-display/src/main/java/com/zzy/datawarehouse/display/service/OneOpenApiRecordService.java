package com.zzy.datawarehouse.display.service;


import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.datawarehouse.display.entity.OneOpenApiRecord;
import com.zzy.datawarehouse.display.vo.OneCodeUseInfoVO;
import com.zzy.datawarehouse.display.vo.OneOpenApiMemberVO;

import java.util.List;

/**
 * 一码通调用记录
 *
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:45
 */
public interface OneOpenApiRecordService extends IService<OneOpenApiRecord> {


    SingleResponse<OneOpenApiMemberVO>  getOpenApiUseServiceNum();

    SingleResponse<List<OneCodeUseInfoVO>> getOneCodeUseInfoVOList();
}

