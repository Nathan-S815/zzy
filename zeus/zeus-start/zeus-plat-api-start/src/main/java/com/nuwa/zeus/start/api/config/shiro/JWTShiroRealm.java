package com.nuwa.zeus.start.api.config.shiro;


import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.auth.qry.GetCurrentUserAuthInfoQryExe;
import com.nuwa.app.zeus.command.auth.vo.UserAuthorityVO;
import com.nuwa.client.zeus.dto.clientobject.auth.qry.GetCurrentUserAuthInfoQry;
import com.nuwa.framework.shiro.starter.JWTToken;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseElement;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import com.nuwa.zeus.start.api.config.shiro.entity.User;
import com.nuwa.zeus.start.api.config.shiro.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description  : 身份校验核心类
 */
@Slf4j
public class JWTShiroRealm extends AuthorizingRealm {

    @Autowired
    private GetCurrentUserAuthInfoQryExe getCurrentUserAuthInfoQryExe;

    /**
     *
     */
    public static final String SECRET = "<REDACTED>";

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证信息(身份验证)
     * Authentication 是用来验证用户身份
     *
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        User user = new User();
        user.setUserId(JWTUtil.getMchUserId(token));
        return new SimpleAuthenticationInfo(user, token, getName());
    }

    /**
     * 此方法调用hasRole,hasPermission的时候才会进行回调.
     * <p>
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /*
         * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
         * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
         * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
         * 缓存过期之后会再次执行。
         */
        log.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");

        User userInfo = (User) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SingleResponse<UserAuthorityVO> userAuthorityInfoResp = getCurrentUserAuthInfoQryExe.execute(new GetCurrentUserAuthInfoQry(userInfo.getUserId()));
        if (userAuthorityInfoResp.isSuccess()) {
            UserAuthorityVO userAuthorityVO = userAuthorityInfoResp.getData();
            authorizationInfo.addRoles(userAuthorityVO.getGroupList().stream().map(BaseGroup::getCode).collect(Collectors.toList()));
            authorizationInfo.addStringPermissions(userAuthorityVO.getAuthorityList().stream().map(BaseElement::getCode).collect(Collectors.toList()));
        }
        return authorizationInfo;
    }

}
