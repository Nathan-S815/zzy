package com.nuwa.client.zeus.api.apppage;

import com.alibaba.cola.dto.SingleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@FeignClient(
        value = "zeus-client-provider",
        fallback = AppPageClientFallBack.class
)
public interface AppPageInfoClientI {

    String API_PREFIX = "/appPageInfo";

    @PostMapping(API_PREFIX + "/getUrlById")
    @ResponseBody
    public SingleResponse getUrlById(@RequestParam Long id);
}
