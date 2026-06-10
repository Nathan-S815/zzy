package com.nuwa.client.zeus.api.order;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.api.order.MaterialClientI;
import com.nuwa.client.zeus.api.order.ShiroClientI;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * MaterialClientFallBack
 *
 * @author hy
 * @date 2021/4/22 13:54
 * @since 1.0.0
 */
@Component
public class ShiroClientFallBack implements ShiroClientI {
    @Override
    public SingleResponse<List<String>> getElements(@RequestParam Long userId) {
        return SingleResponse.buildFailure("9026", "FallBack");
    }
}
