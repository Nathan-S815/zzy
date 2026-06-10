package com.nuwa.ticket.start.api.controller.articel;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.article.qry.ArticlePageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.article.entity.ArticleCategory;
import com.nuwa.infrastructure.ticket.database.article.entity.ArticleInfo;
import com.nuwa.infrastructure.ticket.database.article.param.ArticlePageParam;
import com.nuwa.infrastructure.ticket.database.article.service.ArticleCategoryService;
import com.nuwa.infrastructure.ticket.database.article.service.ArticleInfoService;
import com.nuwa.ticket.start.api.controller.articel.vo.ArticleInfoPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ApiOperation(value = "获取资讯分类（不传分类id查询一级分类数据）")
    @RequestMapping(value = "/getCategoryList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ArticleCategory>> getCategoryList(Long categoryId, UserAware userAware) {
        List<ArticleCategory> categoryList;
        if (Objects.isNull(categoryId)) {
            categoryList = articleCategoryService.lambdaQuery()
                    .eq(ArticleCategory::getMchId, userAware.getMchId())
                    .eq(ArticleCategory::getLevel, 0)
                    .list();
        } else {
            categoryList = articleCategoryService.lambdaQuery()
                    .eq(ArticleCategory::getMchId, userAware.getMchId())
                    .eq(ArticleCategory::getParentId, categoryId)
                    .list();
        }
        return SingleResponse.of(categoryList);
    }

    @ApiOperation(value = "资讯详情")
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<ArticleInfo> getById(@PathVariable("id") Long id, UserAware userAware) {
        ArticleInfo articleInfo = articleInfoService.getById(id);
        Assert.isTrue(userAware.getMchId().equals(articleInfo.getMchId()));
        articleInfoService.incrementUpdate(ArticleInfo::getViews, 1).eq(ArticleInfo::getId, id).update();
        return SingleResponse.of(articleInfo);
    }

    @ApiOperation(value = "资讯分页")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<ArticleInfoPageVO>> page(ArticlePageQry pageQry, UserAware userAware) {
        pageQry.setStatus(1);
        ArticlePageParam pageParam = new ArticlePageParam(pageQry);
        IPage<ArticleInfoPageVO> pageData = articleInfoService.paginateAndConvert(pageParam, this::toVO);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "资讯分页(未登录)")
    @RequestMapping(value = "/notlogin/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<ArticleInfoPageVO>> notloginPage(ArticlePageQry pageQry) {
        pageQry.setStatus(1);
        ArticlePageParam pageParam = new ArticlePageParam(pageQry);
        IPage<ArticleInfoPageVO> pageData = articleInfoService.paginateAndConvert(pageParam, this::toVO);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "资讯详情(未登录)")
    @RequestMapping(value = "notlogin/getById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<ArticleInfo> getByIdNotLogin(@PathVariable("id") Long id) {
        ArticleInfo articleInfo = articleInfoService.getById(id);
        articleInfoService.incrementUpdate(ArticleInfo::getViews, 1).eq(ArticleInfo::getId, id).update();
        return SingleResponse.of(articleInfo);
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

}
