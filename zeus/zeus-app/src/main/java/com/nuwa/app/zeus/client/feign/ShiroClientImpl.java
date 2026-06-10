package com.nuwa.app.zeus.client.feign;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.auth.vo.UserAuthorityVO;
import com.nuwa.client.zeus.api.order.MaterialClientI;
import com.nuwa.client.zeus.api.order.ShiroClientI;
import com.nuwa.infrastructure.zeus.config.OssDomainConfigEx;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseElement;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
import com.nuwa.infrastructure.zeus.database.material.entity.Material;
import com.nuwa.infrastructure.zeus.database.material.mapper.MaterialMapper;
import com.nuwa.infrastructure.zeus.util.MaterialJson;
import com.nuwa.infrastructure.zeus.util.SpringContextKit;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * OrderServiceClientImpl
 *
 * @author hy
 * @date 2021/4/23 10:24
 * @since 1.0.0
 */
@Slf4j
@RestController
public class ShiroClientImpl implements ShiroClientI {

    @Autowired
    private BaseMapperExt baseMapperExt;

    @Override
    public SingleResponse<List<String>> getElements(Long userId) {
        List<String> collect = baseMapperExt.selectAuthorityElementByUserId(userId + "").stream().map(BaseElement::getCode).collect(Collectors.toList());
        return SingleResponse.of(collect);
    }
}
