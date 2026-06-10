package com.nuwa.client.zeus.api.order;

import com.alibaba.cola.dto.SingleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@FeignClient(
        value = "zeus-client-provider",
        fallback = MaterialClientFallBack.class
)
public interface MaterialClientI {
    String API_PREFIX = "/material";

    @PostMapping(API_PREFIX + "/getMaterialByIds")
    @ResponseBody
    public SingleResponse getMaterialByIds(@RequestParam List<Long> ids);

}
