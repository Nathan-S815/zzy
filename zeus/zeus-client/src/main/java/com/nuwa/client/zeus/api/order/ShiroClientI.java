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
public interface ShiroClientI {
    String API_PREFIX = "/shiro";

    @PostMapping(API_PREFIX + "/getElements")
    @ResponseBody
    public SingleResponse<List<String>> getElements(@RequestParam Long userId);

}
