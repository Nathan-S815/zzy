package com.nuwa.infrastructure.discovery.database.user.mapper.query;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberAccountBind;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Objects;


/**
 * @author hy
 */
@ApiModel(value = "用户任务账号认证分页参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AccountBindJoinPageQuery extends BaseJoinPagingQuery<AccountBindJoinPageQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("达人用户id")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.ACCOUNT_ID)
    private String accountId;

    @ApiModelProperty("达人昵称")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.NICK)
    private String nick;

    @ApiModelProperty("开始报名时间")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.CREATE_TIME)
    private Date createTimeBegin;

    @ApiModelProperty("结束报名时间")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.CREATE_TIME)
    private Date createTimeEnd;

    @ApiModelProperty("达人等级")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.LEVEL)
    private String level;

    @ApiModelProperty("达人性别 1:男 0:女")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.SEX)
    private Integer sex;

    @ApiModelProperty("达人地域")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.REGION)
    private String region;

    @ApiModelProperty("达人uId")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.U_ID)
    private String uId;

    @ApiModelProperty("手机号")
    @JoinColumn(tableClass = Member.class, column = Member.USER_PHONE)
    private String userPhone;

    @ApiModelProperty("最小粉丝数")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.FANS_COUNT)
    private Long  fansMin;

    @ApiModelProperty("最大粉丝数")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.FANS_COUNT)
    private Long  fansMax;

    @ApiModelProperty("状态 0:已提交,待审核 1:绑定成功 2:绑定失败 3:已过期")
    private Integer status;

    @Override
    public void where(JoinQueryBuilder<AccountBindJoinPageQuery> wrapper) {
        if (Objects.nonNull(this.getCreateTimeEnd())) {
            this.setCreateTimeEnd(DateUtil.endOfDay(this.getCreateTimeEnd()));
        }
        wrapper.orderByDesc(AccountBindJoinPageQuery::getCreateTimeBegin);
        wrapper.eq(StrUtil.isNotEmpty(this.getAccountId()), AccountBindJoinPageQuery::getAccountId, this.getAccountId());
        wrapper.eq(StrUtil.isNotEmpty(this.getNick()), AccountBindJoinPageQuery::getNick, this.getNick());
        wrapper.eq(StrUtil.isNotEmpty(this.getLevel()), AccountBindJoinPageQuery::getLevel, this.getLevel());
        wrapper.eq(StrUtil.isNotEmpty(this.getUserPhone()), AccountBindJoinPageQuery::getUserPhone, this.getUserPhone());
        wrapper.eq(StrUtil.isNotEmpty(this.getUId()), AccountBindJoinPageQuery::getUId, this.getUId());
        wrapper.eq(Objects.nonNull(this.getSex()), AccountBindJoinPageQuery::getSex, this.getSex());
        wrapper.eq(Objects.nonNull(this.getStatus()), AccountBindJoinPageQuery::getStatus, this.getStatus());
        wrapper.like(StrUtil.isNotEmpty(this.getRegion()), AccountBindJoinPageQuery::getRegion, this.getRegion());
        wrapper.ge(Objects.nonNull(this.getCreateTimeBegin()), AccountBindJoinPageQuery::getCreateTimeBegin, this.getCreateTimeBegin());
        wrapper.le(Objects.nonNull(this.getCreateTimeEnd()), AccountBindJoinPageQuery::getCreateTimeEnd, this.getCreateTimeEnd());
        wrapper.gt(Objects.nonNull(this.getFansMin()), AccountBindJoinPageQuery::getFansMin, this.getFansMin());
        wrapper.lt(Objects.nonNull(this.getFansMax()), AccountBindJoinPageQuery::getFansMax, this.getFansMax());
    }
}
