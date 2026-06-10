package com.nuwa.infrastructure.discovery.database.sms.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.discovery.database.sms.entity.SmsTemplate;
import com.nuwa.client.discovery.dto.clientobject.sms.qry.SmsTemplatePageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 商户短信模板 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-11-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户短信模板分页参数")
public class SmsTemplatePageParam extends PageQry<SmsTemplate> {
    private static final long serialVersionUID = 1L;

    private SmsTemplatePageQry qry;

    public SmsTemplatePageParam(SmsTemplatePageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<SmsTemplate> toQueryWrapper() {
        LambdaQueryWrapper<SmsTemplate> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(SmsTemplate.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(!StrUtil.isBlankOrUndefined(qry.getTitle()), SmsTemplate::getTitle, qry.getTitle());
        queryWrapper.eq(!StrUtil.isBlankOrUndefined(qry.getBizCode()), SmsTemplate::getBizCode, qry.getBizCode());
        return queryWrapper;
    }
}
