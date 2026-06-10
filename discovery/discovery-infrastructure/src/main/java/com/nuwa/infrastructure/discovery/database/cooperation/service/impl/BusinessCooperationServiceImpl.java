package com.nuwa.infrastructure.discovery.database.cooperation.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.client.discovery.dto.clientobject.business.qry.BusinessCooperationQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.discovery.common.exception.ParamException;
import com.nuwa.infrastructure.discovery.database.cooperation.entity.BusinessCooperation;
import com.nuwa.infrastructure.discovery.database.cooperation.mapper.BusinessCooperationMapper;
import com.nuwa.infrastructure.discovery.database.cooperation.service.BusinessCooperationService;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberAccountBindRecord;
import com.nuwa.infrastructure.discovery.database.user.entity.Industry;
import com.nuwa.infrastructure.discovery.database.user.mapper.IndustryMapper;
import com.nuwa.infrastructure.discovery.enums.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BusinessCooperationServiceImpl extends SuperServiceImpl<BusinessCooperationMapper, BusinessCooperation> implements BusinessCooperationService {

    @Autowired
    private BusinessCooperationMapper businessCooperationMapper;

    @Override
    public void addBusinessCooperation(BusinessCooperation businessCooperation) {
        checkBusinessCooperation(businessCooperation);
        businessCooperation.setId(null);
        businessCooperation.setStatus(1);
        businessCooperationMapper.insert(businessCooperation);
    }

    @Override
    public void delBusinessCooperation(Long id) {
        if (id == null) {
            throw new ParamException(ErrorEnum.PARAM_FAILED, "id不能为空");
        }
        BusinessCooperation businessCooperation = new BusinessCooperation();
        businessCooperation.setId(id);
        businessCooperation.setDeleteFlag(1);
        businessCooperationMapper.updateById(businessCooperation);
    }

    @Override
    public void updateBusinessCooperation(BusinessCooperation businessCooperation) {
        if (businessCooperation == null) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"businessCooperation不能为空");
        }
        if(businessCooperation.getId() == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED,"id不能为空");
        }
        businessCooperationMapper.updateById(businessCooperation);
    }

    @Override
    public BusinessCooperation getBusinessCooperationById(Long id) {
        if(id == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED,"id不能为空");
        }
        BusinessCooperation businessCooperation = businessCooperationMapper.selectById(id);
        return businessCooperation;
    }

    @Override
    public IPage<BusinessCooperation> getBusinessCooperationPage(BusinessCooperationQry qry, UserAware userAware, boolean isMch) {
        Page<BusinessCooperation> page = new Page<>();
        if(qry.getLimit() != null && qry.getPage() != null){
            page.setSize(qry.getLimit());
            page.setCurrent(qry.getPage());
        }
        QueryWrapper<BusinessCooperation> queryWrapper = new QueryWrapper<>();
        if(!isMch){
            queryWrapper.eq("user_id",userAware.getUserId());
        }
        queryWrapper.eq("delete_flag", 0);
        if(qry.getStatus() != null){
            queryWrapper.eq("status", qry.getStatus());
        }
        if(StrUtil.isNotBlank(qry.getName())){
            queryWrapper.like("name", qry.getName());
        }
        if(qry.getIndustry() != null){
            queryWrapper.eq("industry", qry.getIndustry());
        }
        if(qry.getCreateTimeStart() != null){
            queryWrapper.ge("create_time", qry.getCreateTimeStart());
        }
        if(qry.getCreateTimeEnd() != null){
            queryWrapper.lt("create_time", qry.getCreateTimeEnd());
        }
        queryWrapper.orderByDesc("create_time");
        IPage<BusinessCooperation> businessCooperationIPage = businessCooperationMapper.selectPage(page, queryWrapper);
        return businessCooperationIPage;
    }


    private void checkBusinessCooperation(BusinessCooperation businessCooperation){
        if (businessCooperation == null) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"businessCooperation不能为空");
        }
        if (StrUtil.isBlank(businessCooperation.getName())) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"name不能为空");
        }
        if (businessCooperation.getUserId() == null) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"userId不能为空");
        }
        if (businessCooperation.getIndustry() == null) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"industry不能为空");
        }
        if (StrUtil.isBlank(businessCooperation.getObjective())) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"objective不能为空");
        }
        if (StrUtil.isBlank(businessCooperation.getObjectiveContent())) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"objectiveContent不能为空");
        }
        if (StrUtil.isBlank(businessCooperation.getCommunicationPlatform())) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"communicationPlatform不能为空");
        }
        if (StrUtil.isBlank(businessCooperation.getCommunicationPlatformContent())) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"communicationPlatformContent不能为空");
        }
        if (businessCooperation.getContentForm() == null) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"contentForm不能为空");
        }
        if (businessCooperation.getPattern() == null) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"pattern不能为空");
        }
        if (businessCooperation.getBudget() == null) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"budget不能为空");
        }
        if (StrUtil.isBlank(businessCooperation.getContactsName())) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"contactsName不能为空");
        }
        if (StrUtil.isBlank(businessCooperation.getPhone())) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"phone");
        }
    }
}
