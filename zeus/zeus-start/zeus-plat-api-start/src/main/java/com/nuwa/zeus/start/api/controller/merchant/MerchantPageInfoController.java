package com.nuwa.zeus.start.api.controller.merchant;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.app.GetPageInfoListQryExe;
import com.nuwa.client.zeus.dto.clientobject.app.qry.PageInfoListQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.database.mch.entity.AppPageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MerchantMenuController 菜单管理
 *
 * @author hy
 * @date 2021/6/3 10:38
 * @since 1.0.0
 */
@Controller
@RequestMapping("merchant/page")
@Api(tags = {"页面信息管理"})
public class MerchantPageInfoController {

    @Autowired
    private GetPageInfoListQryExe getPageInfoListQryExe;

    @ApiOperation(value = "获取页面链接列表")
    @GetMapping(value = "/list")
    @ResponseBody
    public SingleResponse<List<AppPageInfo>> icon(PageInfoListQry cmd, UserAware userAware) {
        return getPageInfoListQryExe.execute(cmd);
    }

}
