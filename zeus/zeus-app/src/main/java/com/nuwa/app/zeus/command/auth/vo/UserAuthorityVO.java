package com.nuwa.app.zeus.command.auth.vo;

import com.nuwa.infrastructure.zeus.database.base.entity.BaseElement;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * UserAuthorityVO 当前登录用户用用的权限点信息
 *
 * @author hy
 * @date 2021/5/25 15:44
 * @since 1.0.0
 */
@AllArgsConstructor
@Data
public class UserAuthorityVO {
    private List<BaseGroup> groupList;
    private List<BaseMenu>  menuList;
    private List<BaseElement> authorityList;
}
