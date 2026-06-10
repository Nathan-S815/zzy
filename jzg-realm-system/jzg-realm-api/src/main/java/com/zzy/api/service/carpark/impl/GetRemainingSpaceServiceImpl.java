package com.zzy.api.service.carpark.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.api.service.carpark.IGetRemainingSpaceService;
import com.zzy.db.dao.carpark.GetRemainingSpaceMapper;
import com.zzy.db.entity.carpark.GetRemainingSpace;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 停车场剩余车位 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-11
 */
@Service
public class GetRemainingSpaceServiceImpl extends ServiceImpl<GetRemainingSpaceMapper, GetRemainingSpace> implements IGetRemainingSpaceService {

}
