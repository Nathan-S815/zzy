package com.nuwa.client.zeus.api.apppage;

import com.alibaba.cola.dto.SingleResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;


@Component
public class AppPageClientFallBack implements AppPageInfoClientI{

    @Override
    public SingleResponse getUrlById(@RequestParam Long id) {
        return SingleResponse.buildFailure("9027", "FallBack");
    }
}
