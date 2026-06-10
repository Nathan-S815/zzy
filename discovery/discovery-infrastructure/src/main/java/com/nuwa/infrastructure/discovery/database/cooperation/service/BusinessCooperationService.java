package com.nuwa.infrastructure.discovery.database.cooperation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.discovery.dto.clientobject.business.qry.BusinessCooperationQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.supper.SuperService;
import com.nuwa.infrastructure.discovery.database.cooperation.entity.BusinessCooperation;
import com.nuwa.infrastructure.discovery.database.user.entity.Industry;

import java.util.Map;

public interface BusinessCooperationService extends SuperService<BusinessCooperation> {

    public void addBusinessCooperation(BusinessCooperation businessCooperation);

    public void delBusinessCooperation(Long id);

    public void updateBusinessCooperation(BusinessCooperation businessCooperation);

    public BusinessCooperation getBusinessCooperationById(Long id);

    IPage<BusinessCooperation> getBusinessCooperationPage(BusinessCooperationQry qry, UserAware userAware, boolean isMch);
}
