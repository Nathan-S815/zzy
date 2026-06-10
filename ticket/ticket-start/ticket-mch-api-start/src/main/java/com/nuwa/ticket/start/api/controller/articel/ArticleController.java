package com.nuwa.ticket.start.api.controller.articel;

import cn.hutool.core.util.PageUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.article.qry.ArticlePageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.article.entity.ArticleCategory;
import com.nuwa.infrastructure.ticket.database.article.entity.ArticleInfo;
import com.nuwa.infrastructure.ticket.database.article.param.ArticlePageParam;
import com.nuwa.infrastructure.ticket.database.article.service.ArticleCategoryService;
import com.nuwa.infrastructure.ticket.database.article.service.ArticleInfoService;
import com.nuwa.ticket.start.api.controller.articel.param.*;
import com.nuwa.ticket.start.api.controller.articel.vo.ArticleInfoPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("article")
@Api(tags = {"资讯管理相关"})
public class ArticleController {

    @Autowired
    private ArticleInfoService articleInfoService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @ApiOperation(value = "新增资讯")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> save(@RequestBody SaveArticleParam param, UserAware userAware) {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setContent(JSONUtil.toJsonStr(param.getContent()));
        articleInfo.setCreateTime(new Date());
        articleInfo.setCategoryOne(param.getCategoryOne());
        articleInfo.setCategorySecond(param.getCategorySecond());
        articleInfo.setMchId(userAware.getMchId());
        articleInfo.setCover(param.getCover());
        articleInfo.setTitle(param.getTitle());
        boolean insert = articleInfo.insert();
        if (insert) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "新增资讯失败");
    }

    @ApiOperation(value = "修改资讯")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> save(@RequestBody ModifyArticleParam param, UserAware userAware) {
        boolean update = articleInfoService.lambdaUpdate()
                .set(ArticleInfo::getContent, JSONUtil.toJsonStr(param.getContent()))
                .set(ArticleInfo::getCategoryOne, param.getCategoryOne())
                .set(ArticleInfo::getCategorySecond, param.getCategorySecond())
                .set(ArticleInfo::getCover, param.getCover())
                .set(ArticleInfo::getTitle, param.getTitle())
                .set(ArticleInfo::getUpdateTime, new Date())
                .eq(ArticleInfo::getId, param.getId())
                .eq(ArticleInfo::getMchId, userAware.getMchId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "修改资讯失败");
    }

    @ApiOperation(value = "资讯上架")
    @RequestMapping(value = "/on", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> on(@RequestBody ArticleOnParam param, UserAware userAware) {
        boolean update = articleInfoService.lambdaUpdate()
                .set(ArticleInfo::getStatus, 1)
                .set(ArticleInfo::getUpdateTime, new Date())
                .eq(ArticleInfo::getId, param.getId())
                .eq(ArticleInfo::getMchId, userAware.getMchId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "上架失败");
    }

    @ApiOperation(value = "资讯下架")
    @RequestMapping(value = "/off", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> off(@RequestBody ArticleOffParam param, UserAware userAware) {
        boolean update = articleInfoService.lambdaUpdate()
                .set(ArticleInfo::getStatus, 0)
                .set(ArticleInfo::getUpdateTime, new Date())
                .eq(ArticleInfo::getId, param.getId())
                .eq(ArticleInfo::getMchId, userAware.getMchId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "下架失败");
    }

    @ApiOperation(value = "批量移除资讯")
    @RequestMapping(value = "/removeByIds", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> remove(@RequestBody RemoveArticleIdsParam param, UserAware userAware) {
        QueryWrapper<ArticleInfo> query = Wrappers.<ArticleInfo>query();
        query.eq(ArticleInfo.MCH_ID, userAware.getMchId());
        query.in(ArticleInfo.ID, param.getIds());
        boolean remove = articleInfoService.remove(query);
        if (remove) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "移除资讯失败");
    }

    @ApiOperation(value = "批量修改分组")
    @RequestMapping(value = "/batchModifyCategory", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> batchModifyCategory(@RequestBody BatchModifyCategoryParam param, UserAware userAware) {
        boolean update = articleInfoService.lambdaUpdate()
                .set(ArticleInfo::getCategoryOne, param.getCategoryOne())
                .set(ArticleInfo::getCategorySecond, param.getCategorySecond())
                .set(ArticleInfo::getUpdateTime, new Date())
                .in(ArticleInfo::getId, param.getIds())
                .eq(ArticleInfo::getMchId, userAware.getMchId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "批量修改分组");
    }

    @ApiOperation(value = "资讯详情")
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<ArticleInfo> getById(@PathVariable("id") Long id) {
        ArticleInfo articleInfo = articleInfoService.getById(id);
        //Assert.isTrue(userAware.getMchId().equals(articleInfo.getMchId()));
        return SingleResponse.of(articleInfo);
    }

    @ApiOperation(value = "旅游通-资讯详情")
    @RequestMapping(value = "/lyt/getById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<ArticleInfo> lytGetById(@PathVariable("id") Long id) {
        ArticleInfo articleInfo = articleInfoService.getById(id);
        articleInfoService.incrementUpdate(ArticleInfo::getViews, 1).eq(ArticleInfo::getId, id).update();
        return SingleResponse.of(articleInfo);
    }

    @ApiOperation(value = "资讯分页")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<ArticleInfoPageVO>> page(ArticlePageQry pageQry, UserAware userAware) {
        ArticlePageParam pageParam = new ArticlePageParam(pageQry);
        IPage<ArticleInfoPageVO> pageData = articleInfoService.paginateAndConvert(pageParam, this::toVO);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "资讯分页(未登录)")
    @RequestMapping(value = "/noLoginPage", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<ArticleInfoPageVO>> noLoginPage(ArticlePageQry pageQry) {
        ArticlePageParam pageParam = new ArticlePageParam(pageQry);
        IPage<ArticleInfoPageVO> pageData = articleInfoService.paginateAndConvert(pageParam, this::toVO);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "获取当前资讯所在页数")
    @RequestMapping(value = "/currentArticlePageIndex", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Integer> getCurrentArticlePageIndex(ArticlePageQry pageQry, UserAware userAware) {
        Integer post = articleInfoService.lambdaQuery()
                .eq(ArticleInfo::getMchId, userAware.getMchId())
                .ge(ArticleInfo::getId, pageQry.getId())
                .count();
        if (post > 0) {
            int page = PageUtil.totalPage(post, 10);
            return SingleResponse.of(page);
        } else {
            return SingleResponse.of(0);
        }
    }

    @ApiOperation(value = "根据一级分类获取资讯列表")
    @RequestMapping(value = "/categoryOne/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ArticleInfoPageVO>> categoryListByCategoryOne(Long categoryOne, UserAware userAware) {
        ArticlePageQry pageQry = new ArticlePageQry();
        pageQry.setPage(0);
        pageQry.setLimit(500);
        pageQry.setCategoryOne(categoryOne);
        ArticlePageParam pageParam = new ArticlePageParam(pageQry);
        IPage<ArticleInfoPageVO> pageData = articleInfoService.paginateAndConvert(pageParam, this::toVO);
        return SingleResponse.of(pageData.getRecords());
    }

    @ApiOperation(value = "根据二级分类获取资讯列表")
    @RequestMapping(value = "/categorySecond/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ArticleInfoPageVO>> categoryListByCategorySecond(Long categorySecond, UserAware userAware) {
        ArticlePageQry pageQry = new ArticlePageQry();
        pageQry.setPage(0);
        pageQry.setLimit(500);
        pageQry.setCategorySecond(categorySecond);
        ArticlePageParam pageParam = new ArticlePageParam(pageQry);
        IPage<ArticleInfoPageVO> pageData = articleInfoService.paginateAndConvert(pageParam, this::toVO);
        return SingleResponse.of(pageData.getRecords());
    }

    public ArticleInfoPageVO toVO(ArticleInfo articleInfo) {
        ArticleInfoPageVO vo = new ArticleInfoPageVO();
        BeanUtils.copyProperties(articleInfo, vo);
        ArticleCategory articleCategoryOne = articleCategoryService.getById(articleInfo.getCategoryOne());
        if (Objects.nonNull(articleCategoryOne)) {
            vo.setCategoryOneName(articleCategoryOne.getName());
        }
        ArticleCategory articleCategorySecond = articleCategoryService.getById(articleInfo.getCategorySecond());
        if (Objects.nonNull(articleCategorySecond)) {
            vo.setCategorySecondName(articleCategorySecond.getName());
        }
        return vo;
    }

    public static void main(String[] args) {
        int total = 100;
        int currentPost = 100;
        int limit = 5;
        int page = 20 - (total - currentPost) / limit;
        System.out.println(page + "");
    }

}
