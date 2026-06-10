package com.nuwa.infrastructure.discovery.database.member.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberGmvRecordDouyin;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 抖音订单表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2022-08-25
 */
@Repository
public interface MemberGmvRecordDouyinMapper extends SuperMapper<MemberGmvRecordDouyin> {

    public void insertMemberGmvRecordDouyinBatch(List<MemberGmvRecordDouyin> list);

    public void incrMemberGMV(@Param("userId") Integer userId, @Param("gmv") Integer gmv);

    public void decrMemberGMV(@Param("userId") Integer userId, @Param("gmv") Integer gmv);

}
