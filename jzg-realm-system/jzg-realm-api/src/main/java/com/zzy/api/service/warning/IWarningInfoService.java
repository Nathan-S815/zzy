package com.zzy.api.service.warning;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.db.entity.warning.NzFileModel;
import com.zzy.db.entity.warning.WarningInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-27
 */
public interface IWarningInfoService extends IService<WarningInfo> {

    public boolean sendMessage(String address, String addTime, String describe,String longitude,String latitude, List<NzFileModel> fileList);

    public boolean saveMessage(String address, String addTime, String describe,String longitude,String latitude, List<NzFileModel> fileList);

}
