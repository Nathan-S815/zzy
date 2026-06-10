package com.zzy.api.service.base.impl;

import com.zzy.api.service.base.IInfoInformationMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.base.InfoInformationMessageMapper;
import com.zzy.db.entity.base.InfoInformationMessage;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 欢迎词设置 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Service
public class InfoInformationMessageServiceImpl extends ServiceImpl<InfoInformationMessageMapper, InfoInformationMessage> implements IInfoInformationMessageService {

}
