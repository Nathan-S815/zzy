package com.nuwa.client.ticket.api.pubsystem;

import com.alibaba.cola.dto.SingleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author hy
 */
@FeignClient(
        value = "ticket-service-${spring.profiles.active}",
        fallback = PubSystemClientFallBack.class
)
public interface PubSystemClientI {
    String API_PREFIX = "/pubSystem";

    /**
     * 获取最新构建的版本状态
     *
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/takeAndUpdateVersionUploadStatus")
    @ResponseBody
    public SingleResponse<?> takeAndUpdateVersionUploadStatus();

    @PostMapping(API_PREFIX + "/takeAndUpdateExpStatus")
    @ResponseBody
    public SingleResponse<?> takeAndUpdateExpStatus();

}
