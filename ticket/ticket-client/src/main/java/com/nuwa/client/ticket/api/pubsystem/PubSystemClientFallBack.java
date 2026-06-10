package com.nuwa.client.ticket.api.pubsystem;

import com.alibaba.cola.dto.SingleResponse;
import org.springframework.stereotype.Component;

/**
 * PubSystemClientFallBack
 *
 * @author hy
 * @date 2021/4/22 13:54
 * @since 1.0.0
 */
@Component
public class PubSystemClientFallBack implements PubSystemClientI {
    @Override
    public SingleResponse<?> takeAndUpdateVersionUploadStatus() {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> takeAndUpdateExpStatus() {
        return SingleResponse.buildFailure("9999", "FallBack");
    }
}
