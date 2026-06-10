package com.nuwa.app.zeus.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.app.entity.AppDependent;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.entity.AppSkuInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppDependentService;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.app.service.AppSkuInfoService;
import com.nuwa.infrastructure.zeus.database.base.entity.*;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseMenuService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseResourceAuthorityService;
import com.nuwa.infrastructure.zeus.database.mch.entity.AppPageInfo;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppUrl;
import com.nuwa.infrastructure.zeus.database.mch.service.AppPageInfoService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppUrlService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * AppBiz 应用管理
 *
 * @author hy
 * @date 2021/5/31 13:45
 * @since 1.0.0
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AppBiz {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AppSkuInfoService appSkuInfoService;

    @Autowired
    private AppDependentService appDependentService;

    @Autowired
    private MerchantAppService merchantAppService;

    @Autowired
    private AppPageInfoService appPageInfoService;

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private BaseGroupMemberService baseGroupMemberService;

    @Autowired
    private BaseMenuService baseMenuService;

    @Autowired
    private MerchantAppUrlService merchantAppUrlService;

    @Autowired
    private BaseResourceAuthorityService baseResourceAuthorityService;

    /**
     * 创建应用
     *
     * @param appInfo        AppInfo
     * @param dependentAppId dependentAppId 依耐应用id
     * @return Boolean
     */
    public Boolean createApp(AppInfo appInfo, List<AppSkuInfo> skuList, List<Integer> dependentAppId) {
        String transId = RandomUtil.randomString(10);
        log.info(">> transId:{}", transId);

        log.info("appInfo:{}", JSONUtil.toJsonStr(appInfo));
        boolean insert = appInfo.insert();
        Assert.isTrue(insert, "save appInfo is failed");
        skuList.stream().forEach(x ->{
            x.setAppId(appInfo.getId());
        });
        appSkuInfoService.saveBatch(skuList);

        BaseGroup baseGroup = new BaseGroup();
        baseGroup.setName(appInfo.getAppName());
        baseGroup.setCode(appInfo.getId() + "");
        baseGroup.setGroupType(2);
        baseGroup.setPath("/" + baseGroup.getCode());
        baseGroup.setDescription("自动创建" + appInfo.getAppName() + "角色");
        baseGroup.setCreateTime(new Date());
        baseGroup.setCreateUserId(appInfo.getCreateUserId());
        baseGroup.setCreateUserName(appInfo.getCreateUserName());
        baseGroup.setCreateHost(appInfo.getCreateHost());
        boolean insertGroup = baseGroup.insert();
        Assert.isTrue(insertGroup, "save appInfo[id:" + appInfo.getId() + "] baseGroup is failed");

        if (Objects.nonNull(dependentAppId)) {
            dependentAppId.forEach(x->{
                AppDependent appDependent = new AppDependent();
                appDependent.setAppId(appInfo.getId());
                appDependent.setDependentAppId(x.longValue());
                appDependent.insert();
            });
            Assert.isTrue(insertGroup, "save appDependent[id:" + appInfo.getId() + "]  is failed");
        }

        //todo 创建根菜单
        /*BaseMenu rootMenu = new BaseMenu();
        rootMenu.setAppId(appInfo.getId());
        rootMenu.setCode(appInfo.getId() + "");
        rootMenu.setParentId(-1);
        rootMenu.setType(AdminCommonConstant.RESOURCE_TYPE_SYS);
        rootMenu.setTitle(appInfo.getAppName() + "系统");
        rootMenu.setIcon("user-default");
        rootMenu.setHref("");
        rootMenu.setPath("/" + appInfo.getId());
        rootMenu.setDescription("自动默认系统菜单");
        rootMenu.setCreateUserId(appInfo.getCreateUserId());
        rootMenu.setCreateTime(new Date());
        rootMenu.setCreateHost(appInfo.getCreateHost());
        rootMenu.setCreateUserName(appInfo.getCreateUserName());
        boolean rootMenuInsert = rootMenu.insert();
        Assert.isTrue(rootMenuInsert, "save BaseMenu[id:" + appInfo.getId() + "]  is failed");*/

       /* BaseResourceAuthority resourceAuthority = new BaseResourceAuthority();
        resourceAuthority.setAuthorityId(baseGroup.getId() + "");
        resourceAuthority.setResourceId(rootMenu.getId() + "");
        resourceAuthority.setAuthorityType(AdminCommonConstant.AUTHORITY_TYPE_GROUP);
        resourceAuthority.setResourceType(AdminCommonConstant.RESOURCE_TYPE_MENU);
        resourceAuthority.setParentId(-1L);
        resourceAuthority.setCreateTime(new Date());
        resourceAuthority.setCreateHost(appInfo.getCreateHost());
        resourceAuthority.setCreateUserId(appInfo.getCreateUserId());
        resourceAuthority.setCreateUserName(appInfo.getCreateUserName());
        boolean resourceAuthorityInsert = resourceAuthority.insert();
        Assert.isTrue(resourceAuthorityInsert, "save resourceAuthority is failed");*/

        log.info("<< transId:{}", transId);
        return Boolean.TRUE;
    }

    /**
     * 更新应用
     *
     * @param appInfo        AppInfo
     * @param dependentAppId dependentAppId 依耐应用id
     * @return Boolean
     */
    public Boolean updateApp(AppInfo appInfo, List<AppSkuInfo> skuList, List<Integer> dependentAppId) {
        String transId = RandomUtil.randomString(10);
        log.info(">> transId:{}", transId);

        log.info("appInfo:{}", JSONUtil.toJsonStr(appInfo));
        boolean update = appInfo.updateById();
        Assert.isTrue(update, "save appInfo is failed");
        appSkuInfoService.remove(new LambdaQueryWrapper<AppSkuInfo>().eq(AppSkuInfo::getAppId,appInfo.getId()));
        appSkuInfoService.saveBatch(skuList);

        appDependentService.remove(new LambdaQueryWrapper<AppDependent>().eq(AppDependent::getAppId,appInfo.getId()));

        if (Objects.nonNull(dependentAppId)) {
            dependentAppId.forEach(x->{
                AppDependent appDependent = new AppDependent();
                appDependent.setAppId(appInfo.getId());
                appDependent.setDependentAppId(x.longValue());
                appDependent.insert();
            });
        }
        log.info("<< transId:{}", transId);
        return Boolean.TRUE;
    }

    /**
     * 删除应用
     *
     */
    public SingleResponse deleteApp(Long appId) {
        String transId = RandomUtil.randomString(10);
        log.info(">> transId:{}", transId);
        Integer count = merchantAppService.lambdaQuery().eq(MerchantApp::getAppId, appId).count();
        if (count > 0){
            return ErrorEnum.APP_USING.buildFailure();
        }
        Integer count1 = appDependentService.lambdaQuery().eq(AppDependent::getDependentAppId, appId).count();
        if (count1 > 0){
            return ErrorEnum.APP_PARENT.buildFailure();
        }

        log.info("appId:{}", appId);
        appInfoService.removeById(appId);
        appDependentService.remove(new LambdaQueryWrapper<AppDependent>().eq(AppDependent::getAppId,appId));
        appPageInfoService.remove(new LambdaQueryWrapper<AppPageInfo>().eq(AppPageInfo::getAppId,appId));
        appSkuInfoService.remove(new LambdaQueryWrapper<AppSkuInfo>().eq(AppSkuInfo::getAppId,appId));
        List<Integer> groupIds = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, appId).list().stream().map(BaseGroup::getId).collect(Collectors.toList());
        baseGroupService.remove(new LambdaQueryWrapper<BaseGroup>().eq(BaseGroup::getCode,appId.toString()));
        baseResourceAuthorityService.remove(new LambdaQueryWrapper<BaseResourceAuthority>().in(BaseResourceAuthority::getAuthorityId,groupIds));
        baseGroupMemberService.remove(new LambdaQueryWrapper<BaseGroupMember>().in(BaseGroupMember::getId,groupIds));
        baseMenuService.remove(new LambdaQueryWrapper<BaseMenu>().eq(BaseMenu::getAppId,appId));
        merchantAppUrlService.remove(new LambdaQueryWrapper<MerchantAppUrl>().eq(MerchantAppUrl::getAppId,appId));

        log.info("<< transId:{}", transId);
        return SingleResponse.buildSuccess();
    }

    /**
     * 创建应用菜单
     *
     * @param baseMenu BaseMenu
     * @return Boolean
     */
    public Boolean createAppMenu(BaseMenu baseMenu, int groupId) {
        String transId = RandomUtil.randomString(10);
        log.info(">> transId:{}", transId);

        log.info("baseMenu:{}", JSONUtil.toJsonStr(baseMenu));
        boolean insert = baseMenu.insert();
        Assert.isTrue(insert, "save baseMenu is failed");

        BaseResourceAuthority resourceAuthority = new BaseResourceAuthority();
        resourceAuthority.setAuthorityId(groupId + "");
        resourceAuthority.setResourceId(baseMenu.getId() + "");
        resourceAuthority.setAuthorityType(AdminCommonConstant.AUTHORITY_TYPE_GROUP);
        resourceAuthority.setResourceType(AdminCommonConstant.RESOURCE_TYPE_MENU);
        resourceAuthority.setParentId(-1L);
        resourceAuthority.setCreateTime(new Date());
        resourceAuthority.setCreateHost(baseMenu.getCreateHost());
        resourceAuthority.setCreateUserId(baseMenu.getCreateUserId());
        resourceAuthority.setCreateUserName(baseMenu.getCreateUserName());
        boolean resourceAuthorityInsert = resourceAuthority.insert();
        Assert.isTrue(resourceAuthorityInsert, "save resourceAuthority is failed");

        log.info("<< transId:{}", transId);
        return Boolean.TRUE;
    }

    /**
     * 创建应用菜单权限点
     *
     * @param baseElement baseElement
     * @param groupId     groupId
     * @return Boolean
     */
    public Boolean createAppMenuElement(BaseElement baseElement, int groupId) {
        String transId = RandomUtil.randomString(10);
        log.info(">> transId:{}", transId);

        log.info("baseElement:{}", JSONUtil.toJsonStr(baseElement));
        boolean insert = baseElement.insert();
        Assert.isTrue(insert, "save BaseElement is failed");

        BaseResourceAuthority resourceAuthority = new BaseResourceAuthority();
        resourceAuthority.setAuthorityId(groupId + "");
        resourceAuthority.setResourceId(baseElement.getId() + "");
        resourceAuthority.setAuthorityType(AdminCommonConstant.AUTHORITY_TYPE_GROUP);
        resourceAuthority.setResourceType(baseElement.getType());
        resourceAuthority.setParentId(-1L);
        resourceAuthority.setCreateTime(new Date());
        resourceAuthority.setCreateHost(baseElement.getCreateHost());
        resourceAuthority.setCreateUserId(baseElement.getCreateUserId());
        resourceAuthority.setCreateUserName(baseElement.getCreateUserName());
        boolean resourceAuthorityInsert = resourceAuthority.insert();
        Assert.isTrue(resourceAuthorityInsert, "save resourceAuthority is failed");

        log.info("<< transId:{}", transId);
        return Boolean.TRUE;
    }
}
