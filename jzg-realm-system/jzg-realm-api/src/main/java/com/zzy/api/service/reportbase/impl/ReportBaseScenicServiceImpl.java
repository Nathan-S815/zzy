package com.zzy.api.service.reportbase.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.reportbase.IReportBaseScenicService;
import com.zzy.db.dao.base.MerchantTypeMapper;
import com.zzy.db.dao.reportbase.ReportBaseScenicMapper;
import com.zzy.db.entity.base.MerchantType;
import com.zzy.db.entity.reportbase.ReportBaseScenic;
import com.zzy.security.lib.dao.RoleInfoMapper;
import com.zzy.security.lib.entity.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区上报基础信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
@Service
public class ReportBaseScenicServiceImpl extends ServiceImpl<ReportBaseScenicMapper, ReportBaseScenic> implements IReportBaseScenicService {
     @Autowired
     private ReportBaseScenicMapper reportBaseScenicMapper;

     @Autowired
     private RoleInfoMapper roleInfoMapper;

     @Autowired
     private MerchantTypeMapper merchantTypeMapper;


     @Override
     public PageInfo<ReportBaseScenic> getReportBaseScenicList(Integer pagNumber, Integer pagSize, String keyword) {
          PageHelper.startPage(pagNumber, pagSize);
          return new PageInfo<ReportBaseScenic>(reportBaseScenicMapper.selectPageListBaseScenic(keyword));
     }

     @Override
     public PageInfo<Map<String, Object>> getBaseSpotListByPara(Integer pagNumber, Integer pagSize, Map<String, Object> para) {
          PageHelper.startPage(pagNumber,pagSize);
          List<Map<String,Object>> list = reportBaseScenicMapper.selectBaseSpotListByPara(para);
          return new PageInfo<>(list);
     }

     @Override
     public RoleInfo getRoleInfoBymerchantId(Integer merchantId) {
          MerchantType mt = merchantTypeMapper.selectById(merchantId);
          RoleInfo ri = roleInfoMapper.selectByRoleName(mt.getTypeName());
          if(ri==null){
               ri = new RoleInfo();
               ri.setCreateTime(new Date());
               ri.setIsEnable(1);
               ri.setRoleName(mt.getTypeName());
               roleInfoMapper.insertRoleInfo(ri);
          }
          return ri;
     }
}
