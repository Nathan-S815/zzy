package com.zzy.api.service.eventdepart;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.api.dto.EventAddParam;
import com.zzy.api.dto.EventListParam;
import com.zzy.db.entity.eventdepart.EventInfo;
import com.zzy.db.entity.eventdepart.EventMediaInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 事件任务表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface IEventMediaInfoService extends IService<EventMediaInfo> {

}
