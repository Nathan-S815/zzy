package com.nuwa.infrastructure.discovery.database.member.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberAccountBindRecord;

import com.nuwa.infrastructure.discovery.database.member.vo.MemberAccountBindRecordVO;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberExaminePageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * 达人账号绑定历史记录表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2022-08-22
 */
@Repository
public interface MemberAccountBindRecordMapper extends SuperMapper<MemberAccountBindRecord> {

    public MemberAccountBindRecordVO getMemberAccountBindRecordVOByBindId(@Param("memberAccountBindId") Long memberAccountBindId, @Param("status") Integer status);

    public MemberAccountBindRecordVO getMemberAccountBindRecordVOByParams(@Param("params")Map<String, Object> params);

    public MemberAccountBindRecordVO getMemberAccountBindRecordVO(@Param("id") Long id);

}
