package com.nuwa.client.zeus.api.order;

import com.alibaba.cola.dto.SingleResponse;
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
public class MaterialClientFallBack implements MaterialClientI {
    @Override
    public SingleResponse<String> getMaterialByIds(@RequestParam List<Long> ids) {
        return SingleResponse.buildFailure("9026", "FallBack");
    }
}
