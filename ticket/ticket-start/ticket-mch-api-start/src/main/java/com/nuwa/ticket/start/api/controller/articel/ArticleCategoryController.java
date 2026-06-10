package com.nuwa.ticket.start.api.controller.articel;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.article.entity.ArticleCategory;
import com.nuwa.infrastructure.ticket.database.article.entity.ArticleInfo;
import com.nuwa.infrastructure.ticket.database.article.service.ArticleCategoryService;
import com.nuwa.infrastructure.ticket.database.article.service.ArticleInfoService;
import com.nuwa.ticket.start.api.controller.articel.param.ModifyArticleCategoryParam;
import com.nuwa.ticket.start.api.controller.articel.param.RemoveArticleCategoryParam;
import com.nuwa.ticket.start.api.controller.articel.param.SaveArticleCategoryParam;
import com.nuwa.ticket.start.api.controller.articel.vo.ArticleCategoryTreeVO;
import com.nuwa.ticket.start.api.controller.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("article/category")
@Api(tags = {"资讯分类"})
public class ArticleCategoryController {

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ArticleInfoService articleInfoService;

    @ApiOperation(value = "新增分组")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> save(@RequestBody SaveArticleCategoryParam param, UserAware userAware) {
        Integer count = articleCategoryService.lambdaQuery()
                .eq(ArticleCategory::getMchId, userAware.getMchId())
                .eq(ArticleCategory::getName, param.getName()).count();
        if (count > 0) {
            return SingleResponse.buildFailure("9874", "分组名称重复");
        }
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setName(param.getName());
        articleCategory.setMchId(userAware.getMchId());
        if (Objects.isNull(param.getParentId())) {
            articleCategory.setParentId(null);
            articleCategory.setLevel(0);
        } else {
            ArticleCategory categoryParent = articleCategoryService.getById(param.getParentId());
            if (Objects.nonNull(categoryParent)) {
                articleCategory.setPath("/" + categoryParent.getId());
            }
            articleCategory.setLevel(1);
            articleCategory.setParentId(param.getParentId());
        }
        articleCategory.setCreateTime(new Date());
        boolean insert = articleCategory.insert();
        if (insert) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "新增分组失败");
    }

    @ApiOperation(value = "修改分组")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modify(@RequestBody ModifyArticleCategoryParam param, UserAware userAware) {
        Integer count = articleCategoryService.lambdaQuery()
                .eq(ArticleCategory::getMchId, userAware.getMchId())
                .eq(ArticleCategory::getName, param.getName()).count();
        if (count > 0) {
            return SingleResponse.buildFailure("9874", "分组名称重复");
        }
        boolean update = articleCategoryService.lambdaUpdate()
                .set(ArticleCategory::getName, param.getName())
                .set(ArticleCategory::getUpdateTime, new Date())
                .eq(ArticleCategory::getId, param.getId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "修改分组失败");
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> remove(@RequestBody RemoveArticleCategoryParam param, UserAware userAware) {
        ArticleCategory articleCategory = articleCategoryService.getById(param.getId());
        Integer level = articleCategory.getLevel();
        if (level == 0) {
            Integer countCategoryOne = articleInfoService.lambdaQuery()
                    .eq(ArticleInfo::getMchId, userAware.getMchId())
                    .eq(ArticleInfo::getCategoryOne, param.getId()).count();
            if (countCategoryOne > 0) {
                return SingleResponse.buildFailure("9874", "当前分组已绑定资讯，不可删除");
            }

            Integer categoryCount = articleCategoryService.lambdaQuery().eq(ArticleCategory::getParentId, articleCategory.getId()).count();
            if (categoryCount > 0) {
                return SingleResponse.buildFailure("9874", "当前分组有子分组，不可删除");
            }

        } else {
            Integer countCategorySecond = articleInfoService.lambdaQuery()
                    .eq(ArticleInfo::getMchId, userAware.getMchId())
                    .eq(ArticleInfo::getCategorySecond, param.getId()).count();
            if (countCategorySecond > 0) {
                return SingleResponse.buildFailure("9874", "当前分组已绑定资讯，不可删除");
            }
        }

        QueryWrapper<ArticleCategory> query = Wrappers.<ArticleCategory>query();
        query.eq(ArticleInfo.MCH_ID, userAware.getMchId());
        query.in(ArticleInfo.ID, param.getId());
        boolean remove = articleCategoryService.remove(query);
        if (remove) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "删除分组失败");
    }

    @ApiOperation(value = "获取分组树")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ArticleCategoryTreeVO>> tree(String title, UserAware userAware) {
        List<ArticleCategory> articleCategories = articleCategoryService.lambdaQuery()
                .eq(ArticleCategory::getMchId,userAware.getMchId())
                .list();
        return SingleResponse.of(getGroupTrees(articleCategories));
    }

    @ApiOperation(value = "获取资讯分类（不传分类id查询一级分类数据）")
    @RequestMapping(value = "/getCategoryList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ArticleCategory>> getCategoryList(Long categoryId, Long mchId) {
        List<ArticleCategory> categoryList;
        if (Objects.isNull(categoryId)) {
            categoryList = articleCategoryService.lambdaQuery()
                    .eq(ArticleCategory::getMchId, mchId)
                    .eq(ArticleCategory::getLevel, 0)
                    .list();
        } else {
            categoryList = articleCategoryService.lambdaQuery()
                    .eq(ArticleCategory::getMchId, mchId)
                    .eq(ArticleCategory::getParentId, categoryId)
                    .list();
        }
        return SingleResponse.of(categoryList);
    }

    private List<ArticleCategoryTreeVO> getGroupTrees(List<ArticleCategory> categoryList) {
        List<ArticleCategoryTreeVO> trees = new ArrayList<ArticleCategoryTreeVO>();
        ArticleCategoryTreeVO node = null;
        for (ArticleCategory category : categoryList) {
            node = new ArticleCategoryTreeVO();
            BeanUtils.copyProperties(category, node);
            node.setLabel(category.getName());
            trees.add(node);
        }
        return TreeUtil.bulid(trees, -1);
    }
}
