package com.zzy.api.service.carpark.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.api.service.carpark.IGetOutCarService;
import com.zzy.db.dao.carpark.GetOutCarMapper;
import com.zzy.db.entity.carpark.GetOutCar;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-24
 */
@Service
public class GetOutCarServiceImpl extends ServiceImpl<GetOutCarMapper, GetOutCar> implements IGetOutCarService {

}
