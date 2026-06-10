package com.nuwa.client.zeus.api.order.param;

import com.nuwa.framework.base.UserAware;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MaterialParam {

    private MultipartFile file;
    private Long typeId;
    private UserAware userAware;
}
