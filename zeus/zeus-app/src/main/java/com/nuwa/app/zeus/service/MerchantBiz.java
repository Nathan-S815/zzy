package com.nuwa.app.zeus.service;

import cn.hutool.core.lang.func.VoidFunc0;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.app.zeus.service.dto.MerchantUserDTO;
import com.nuwa.client.zeus.dto.clientobject.mch.OpenMerchantAppCmd;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppDependentService;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.base.entity.*;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import com.nuwa.infrastructure.zeus.database.base.service.*;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppUrl;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppUrlService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import com.nuwa.infrastructure.zeus.util.SerializUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * MerchantBiz
 *
 * @author hy
 * @date 2021/6/2 16:26
 * @since 1.0.0
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MerchantBiz {

    @Value("${app.url.appId.fixed}")
    private String fixed;

    @Value("${app.url.appId.inflexible},20211062")
    private String inflexible;

    @Value("${app.url.wechat.miniapp}")
    private String wechatMiniappUrl;

    @Value("${app.url.alibaba.miniapp}")
    private String alibabaMiniappUrl;

    @Value("${app.url.wechat.official}")
    private String wechatOfficialUrl;

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private BaseGroupMemberService baseGroupMemberService;

    @Autowired
    private MerchantAppService merchantAppService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AppDependentService appDependentService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantAppUrlService merchantAppUrlService;

    @Autowired
    private BaseMenuService baseMenuService;

    @Autowired
    private BaseElementService baseElementService;

    @Autowired
    private BaseMapperExt baseMapperExt;

    @Autowired
    private BaseResourceAuthorityService baseResourceAuthorityService;

    public Boolean createMerchant(Merchant merchant, MerchantUserDTO userDTO) {
        String transId = RandomUtil.randomString(10);
        log.info(">> transId:{}", transId);

        Integer count = merchantService.lambdaQuery().eq(Merchant::getContentPhone, merchant.getContentPhone()).count();
        Assert.isTrue(count == 0, "手机号重复");

        Integer count1 = baseUserService.lambdaQuery().eq(BaseUser::getUsername, userDTO.getUserName()).count();
        Assert.isTrue(count1 == 0, "用户名重复");

        log.info("merchant:{}", JSONUtil.toJsonStr(merchant));
        merchant.setUserName(userDTO.getUserName());
        boolean insert = merchant.insert();
        Assert.isTrue(insert, "save merchant is failed");

        BaseUser baseUser = new BaseUser();
        baseUser.setUsername(userDTO.getUserName());
        baseUser.setPassword(MD5.create().digestHex(userDTO.getPassword(), "utf-8"));
        baseUser.setTenantId(merchant.getMchId());
        baseUser.setMobilePhone(merchant.getContentPhone());
        baseUser.setDescription("商户主账户");
        baseUser.setAddress(merchant.getAddress());
        baseUser.setEmail(merchant.getEmail());
        baseUser.setName(merchant.getContentName());
        baseUser.setStatus("0");
        baseUser.setType(AdminCommonConstant.SUPPER_USER_TYPE);
        boolean insertUser = baseUser.insert();
        Assert.isTrue(insertUser, "save BaseUser is failed");

        log.info("<< transId:{}", transId);
        return Boolean.TRUE;
    }

    public Boolean openApp(OpenMerchantAppCmd cmd) {
        String transId = RandomUtil.randomString(10);
        log.info(">> transId:{}", transId);

        Set<String> appIds = cmd.getOpenApps().stream().map(x -> x.getParentAppId() + "_" + x.getAppId()).collect(Collectors.toSet());
        Set<String> merchantAppIds = merchantAppService.lambdaQuery().eq(MerchantApp::getMerchantId, cmd.getMerchantId()).list().stream().map(x -> x.getParentAppId() + "_" + x.getAppId()).collect(Collectors.toSet());

        //移除的应用
        List<Long> removedAppIds = merchantAppService.lambdaQuery().eq(MerchantApp::getMerchantId, cmd.getMerchantId()).list().stream().filter(
                x -> {
                    String s = x.getParentAppId() + "_" + x.getAppId();
                    return !appIds.contains(s);
                }
        ).map(MerchantApp::getAppId).collect(Collectors.toList());

        List<Integer> userIds = baseUserService.lambdaQuery()
                .eq(BaseUser::getTenantId, cmd.getMerchantId())
                .list().stream().map(BaseUser::getId).collect(Collectors.toList());
        //移除商户应用
        merchantAppService.lambdaQuery().eq(MerchantApp::getMerchantId, cmd.getMerchantId()).list().stream().filter(
                x -> {
                    String s = x.getParentAppId() + "_" + x.getAppId();
                    return !appIds.contains(s);
                }
        ).forEach(x -> {
            merchantAppService.lambdaUpdate()
                    .eq(MerchantApp::getId, x.getId())
                    .remove();
            BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, x.getAppId()).one();
            if (Objects.nonNull(baseGroup)) {

                if (userIds.size() > 0) {
                    baseGroupMemberService.lambdaUpdate()
                            .eq(BaseGroupMember::getGroupId, baseGroup.getId())
                            .in(BaseGroupMember::getUserId, userIds)
                            .remove();
                }
            }

        });

        BaseUser adminUser = baseUserService.lambdaQuery()
                .eq(BaseUser::getTenantId, cmd.getMerchantId())
                .eq(BaseUser::getType, AdminCommonConstant.SUPPER_USER_TYPE)
                .one();
        Assert.notNull(adminUser, "商户主账号为空");

        List<MerchantApp> batchSaveMerchantApps = cmd.getOpenApps().stream().filter(
                x -> {
                    String s = x.getParentAppId() + "_" + x.getAppId();
                    return !merchantAppIds.contains(s);
                }
        ).map(x -> {
            AppInfo appInfo = appInfoService.getById(x.getAppId());
            MerchantApp merchantApp = new MerchantApp();
            merchantApp.setAppId(x.getAppId());
            merchantApp.setParentAppId(x.getParentAppId());
            merchantApp.setMerchantId(cmd.getMerchantId().longValue());
            merchantApp.setAppName(appInfo.getAppName());
            merchantApp.setCreateUserId(cmd.getUserAware().getMchUserId() + "");
            merchantApp.setCreateHost(cmd.getUserAware().getHostIp());
            merchantApp.setCreateUserName(cmd.getUserAware().getUserName());
            merchantApp.setCreateTime(new Date());
            return merchantApp;
        }).collect(Collectors.toList());
        boolean saveMerchantApp = merchantAppService.saveBatch(batchSaveMerchantApps);
        Assert.isTrue(saveMerchantApp, "保存商户应用失败");

        List<BaseGroupMember> baseGroupMembers = cmd.getOpenApps().stream().filter(
                x -> {
                    String s = x.getParentAppId() + "_" + x.getAppId();
                    return !merchantAppIds.contains(s);
                }
        ).map(x -> {
            BaseGroupMember groupMember = new BaseGroupMember();
            BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, x.getAppId()).one();
            groupMember.setGroupId(baseGroup.getId() + "");
            if (!x.getParentAppId().equals(-1L)) {
                BaseGroup parentGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, x.getParentAppId()).one();
                groupMember.setParentGroupId(parentGroup.getId().longValue());
            }
            groupMember.setUserId(adminUser.getId() + "");
            groupMember.setCreateHost(cmd.getUserAware().getHostIp());
            groupMember.setDescription("开通应用");
            groupMember.setCreateUserId(cmd.getUserAware().getMchUserId() + "");
            groupMember.setCreateUserName(cmd.getUserAware().getUserName());
            groupMember.setCreateTime(new Date());
            return groupMember;
        }).collect(Collectors.toList());
        boolean saveBaseGroupMember = baseGroupMemberService.saveBatch(baseGroupMembers);
        Assert.isTrue(saveBaseGroupMember, "保存分组user失败");


        List<String> fixed = SerializUtil.strToList(this.fixed);

        List<Long> inflexible = appInfoService.lambdaQuery().in(AppInfo::getAppType, 1, 3).list().stream().map(AppInfo::getId).filter(x -> !fixed.contains(x.toString())).collect(Collectors.toList());

//        List<String> inflexible = SerializUtil.strToList(this.inflexible);
        List<Long> apps = merchantAppUrlService.lambdaQuery().eq(MerchantAppUrl::getMchId, cmd.getMerchantId()).list().stream().map(MerchantAppUrl::getAppId).collect(Collectors.toList());

        List<MerchantAppUrl> collect = cmd.getOpenApps().stream().filter(x -> fixed.contains(x.getAppId().toString()) || inflexible.contains(x.getAppId()))
                .filter(x -> !apps.contains(x.getAppId())).map(x -> {
                    MerchantAppUrl merchantAppUrl = new MerchantAppUrl();
                    merchantAppUrl.setMchId(cmd.getMerchantId().longValue());
                    merchantAppUrl.setAppId(x.getAppId());
                    if (fixed.contains(x.getAppId().toString())) {
                        switch (x.getAppId().toString()) {
                            case "9001": {
                                merchantAppUrl.setLoginSubmitUrl(wechatMiniappUrl);
                                break;
                            }
                            case "20211012": {
                                merchantAppUrl.setLoginSubmitUrl(alibabaMiniappUrl);
                                break;
                            }
                            case "20211041": {
                                merchantAppUrl.setLoginSubmitUrl(wechatOfficialUrl);
                                break;
                            }
                            default:
                                break;
                        }
                    }
                    return merchantAppUrl;
                }).collect(Collectors.toList());
        merchantAppUrlService.saveBatch(collect);

        log.info("<< transId:{}", transId);
        return Boolean.TRUE;
    }

    public Boolean createMerchantChild(BaseUser baseUser, List<Long> appIds) {
        String transId = RandomUtil.randomString(10);
        log.info(">> transId:{}", transId);

        Integer count1 = baseUserService.lambdaQuery().eq(BaseUser::getUsername, baseUser.getUsername()).count();
        Assert.isTrue(count1 == 0, "用户名重复");

        Integer count2 = baseUserService.lambdaQuery().eq(BaseUser::getMobilePhone, baseUser.getMobilePhone()).count();
        Assert.isTrue(count2 == 0, "手机号重复");

        log.info("user:{}", JSONUtil.toJsonStr(baseUser));
        boolean insert = baseUser.insert();
        Assert.isTrue(insert, "save user is failed");

        if (Objects.nonNull(appIds) && appIds.size() > 0) {
            List<MerchantApp> merApps = merchantAppService.lambdaQuery().in(MerchantApp::getId, appIds).list();
            List<BaseGroupMember> groups = merApps.stream().map(merApp -> {
                BaseGroupMember baseGroupMember = new BaseGroupMember();
                BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, merApp.getAppId()).one();
                baseGroupMember.setGroupId(baseGroup.getId() + "");
                if (!merApp.getParentAppId().equals(-1L)) {
                    BaseGroup patentBaseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, merApp.getParentAppId()).one();
                    baseGroupMember.setParentGroupId(patentBaseGroup.getId().longValue());
                }
                baseGroupMember.setUserId(baseUser.getId().toString());
                baseGroupMember.setDescription("开通应用");
                baseGroupMember.setCreateUserId(baseUser.getCreateUserId());
                baseGroupMember.setCreateUserName(baseUser.getCreateUserName());
                baseGroupMember.setCreateHost(baseUser.getCreateHost());
                return baseGroupMember;
            }).collect(Collectors.toList());
            baseGroupMemberService.saveBatch(groups);

            //保存用户的菜单和权限
            List<BaseResourceAuthority> allResourceAuthorityList = new ArrayList<>();
            merApps.stream().filter(x -> !x.getParentAppId().equals(-1L)).forEach(merApp -> {
                List<BaseResourceAuthority> resourceAuthorityList = baseMapperExt.listAuthorityByAppId(merApp.getAppId());
                resourceAuthorityList.forEach(x -> {
                    x.setParentAppId(merApp.getParentAppId());
                    x.setParentId(merApp.getAppId());
                    x.setAuthorityType("user");
                    x.setAuthorityId(baseUser.getId() + "");
                });
                allResourceAuthorityList.addAll(resourceAuthorityList);
            });
            if (allResourceAuthorityList.size() > 0) {
                baseResourceAuthorityService.saveBatch(allResourceAuthorityList);
            }
        }
        log.info("<< transId:{}", transId);
        return Boolean.TRUE;
    }

    public Boolean modifyMerchantChild(BaseUser baseUser, List<Long> appIds) {
        String transId = RandomUtil.randomString(10);
        log.info(">> transId:{}", transId);

        Integer count1 = baseUserService.lambdaQuery().eq(BaseUser::getUsername, baseUser.getUsername()).ne(BaseUser::getId, baseUser.getId()).count();
        Assert.isTrue(count1 == 0, "用户名重复");

        log.info("user:{}", JSONUtil.toJsonStr(baseUser));
        boolean update = baseUser.updateById();
        Assert.isTrue(update, "update user is failed");

        List<BaseGroupMember> baseGroupMembers = baseGroupMemberService.lambdaQuery().eq(BaseGroupMember::getUserId, baseUser.getId()).list();
        Set<String> oldGroupIds = baseGroupMembers.stream().map(BaseGroupMember::getGroupId).collect(Collectors.toSet());
        log.info("oldGroupIds:{}", oldGroupIds);

        List<MerchantApp> merApps = merchantAppService.lambdaQuery().in(MerchantApp::getId, appIds).list();
        Set<String> submitGroupIds = merApps.stream().map(merApp -> {
            BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getCode, merApp.getAppId()).one();
            return baseGroup.getId() + "";
        }).collect(Collectors.toSet());

        log.info("submitGroupIds:{}", submitGroupIds);

        //移除的应用
        oldGroupIds.stream().filter(x -> !submitGroupIds.contains(x)).forEach(x -> {
            log.info("移除的应用:{}", x);
            BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getId, x).one();
            baseGroupMemberService.lambdaUpdate()
                    .eq(BaseGroupMember::getUserId, baseUser.getId())
                    .eq(BaseGroupMember::getGroupId, baseGroup.getId())
                    .remove();

            baseGroupMemberService.lambdaUpdate()
                    .eq(BaseGroupMember::getUserId, baseUser.getId())
                    .eq(BaseGroupMember::getParentGroupId, baseGroup.getId())
                    .remove();
        });

        //新增的应用
        List<BaseGroupMember> batchSaveBaseGroupMember = submitGroupIds.stream().filter(x -> !oldGroupIds.contains(x)).map(x -> {
            BaseGroupMember baseGroupMember = new BaseGroupMember();
            BaseGroup baseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getId, x).one();
            baseGroupMember.setGroupId(baseGroup.getId() + "");
            if (!baseGroup.getParentId().equals(-1)) {
                BaseGroup patentBaseGroup = baseGroupService.lambdaQuery().eq(BaseGroup::getId, baseGroup.getParentId()).one();
                baseGroupMember.setParentGroupId(patentBaseGroup.getId().longValue());
            }
            baseGroupMember.setUserId(baseUser.getId().toString());
            baseGroupMember.setDescription("开通应用");
            baseGroupMember.setCreateUserId(baseUser.getCreateUserId());
            baseGroupMember.setCreateUserName(baseUser.getCreateUserName());
            baseGroupMember.setCreateHost(baseUser.getCreateHost());
            log.info("新增的应用:{}", x);
            return baseGroupMember;
        }).collect(Collectors.toList());
        baseGroupMemberService.saveBatch(batchSaveBaseGroupMember);

        //保存用户的菜单和权限
            /*baseResourceAuthorityService.remove(new LambdaQueryWrapper<BaseResourceAuthority>()
                    .eq(BaseResourceAuthority::getAuthorityId, baseUser.getId())
                    .eq(BaseResourceAuthority::getAuthorityType, "user")
            );
            List<BaseResourceAuthority> allResourceAuthorityList = new ArrayList<>();
            merApps.stream().filter(x -> !x.getParentAppId().equals(-1L)).forEach(merApp -> {
                List<BaseResourceAuthority> resourceAuthorityList = baseMapperExt.listAuthorityByAppId(merApp.getAppId());
                resourceAuthorityList.forEach(x -> {
                    x.setParentAppId(merApp.getParentAppId());
                    x.setParentId(merApp.getAppId());
                    x.setAuthorityType("user");
                    x.setAuthorityId(baseUser.getId() + "");
                });
                allResourceAuthorityList.addAll(resourceAuthorityList);
            });
            if (allResourceAuthorityList.size() > 0) {
                baseResourceAuthorityService.saveBatch(allResourceAuthorityList);
            }*/

        log.info("<< transId:{}", transId);
        return Boolean.TRUE;
    }

    public Boolean deleteMerchantChild(Long userId) {
        String transId = RandomUtil.randomString(10);
        log.info(">> transId:{}", transId);

        baseUserService.removeById(userId);

        baseGroupMemberService.remove(new LambdaQueryWrapper<BaseGroupMember>().eq(BaseGroupMember::getUserId, userId));

        log.info("<< transId:{}", transId);
        return Boolean.TRUE;
    }

    @Data
    public static class AuthorityDTO {
        private List<BaseMenu> menus;
        private List<BaseElement> elements;
    }
}
