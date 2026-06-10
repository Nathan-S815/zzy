package com.zzy.api.controller.eventdepart;


import com.zzy.api.service.eventdepart.IDepartPrivilegeService;
import com.zzy.api.service.eventdepart.IDepartmentInfoService;
import com.zzy.core.dto.R;
import com.zzy.db.entity.eventdepart.DepartPrivilege;
import com.zzy.db.entity.eventdepart.DepartmentInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Api(tags = "部门权限相关")
@RequestMapping("/v2/depart")
public class DepartmentAuthController {

    @Autowired
    private IDepartPrivilegeService departPrivilegeService;

    @Autowired
    private IDepartmentInfoService departmentInfoService;


    @PostMapping("setDepartProcessComplainEvent")
    @ApiOperation(value = "设置部门投诉事件处理权限")
    public R setDepartProcessComplainEvent(Integer departId){
        if(departId==null){
            return R.nullValueError();
        }
        DepartPrivilege tmp = departPrivilegeService.lambdaQuery().eq(DepartPrivilege::getDepartId,departId)
                .eq(DepartPrivilege::getPrivilegeName,"process_complain_event").one();
        if(tmp!=null){
            return R.error("该部门已拥有权限，勿重复设置");
        }
        DepartPrivilege dp = new DepartPrivilege();
        dp.setDepartId(departId).setPrivilegeName("process_complain_event").setRemark("投诉处理权限");
        return dp.insert()?R.success():R.fail();
    }

    @GetMapping("getProcessComplainDepartList")
    @ApiOperation(value = "获取具有投诉事件处理权限的部门列表")
    public R getProcessComplainDepartList(){
        List<DepartPrivilege> dps = departPrivilegeService.lambdaQuery().eq(DepartPrivilege::getPrivilegeName,"process_complain_event").list();
        Set<Integer> ids = dps.stream().map(o->o.getDepartId()).collect(Collectors.toSet());
        return R.ok(departmentInfoService.lambdaQuery().in(DepartmentInfo::getId,ids).list());
    }


}
