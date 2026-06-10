package com.nuwa.infrastructure.ticket.database.member.service.impl;

import com.nuwa.infrastructure.ticket.database.member.entity.ContactsInfo;
import com.nuwa.infrastructure.ticket.database.member.mapper.ContactsInfoMapper;
import com.nuwa.infrastructure.ticket.database.member.service.ContactsInfoService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 联系人信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-03-30
 */
@Slf4j
@Service
public class ContactsInfoServiceImpl extends SuperServiceImpl<ContactsInfoMapper, ContactsInfo> implements ContactsInfoService {

    @Autowired
    private ContactsInfoMapper contactsInfoMapper;

}
