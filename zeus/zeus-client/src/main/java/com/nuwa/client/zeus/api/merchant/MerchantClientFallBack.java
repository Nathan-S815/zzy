package com.nuwa.client.zeus.api.merchant;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.api.merchant.vo.ClientAppVO;
import com.nuwa.client.zeus.api.merchant.vo.MerchantSimpleVO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * MerchantClientFallBack
 *
 * @author hy
 * @date 2021/4/22 13:54
 * @since 1.0.0
 */
@Component
public class MerchantClientFallBack implements MerchantClientI {

    @Override
    public SingleResponse<List<MerchantSimpleVO>> getMerchantListByIds(Collection<Long> ids) {
        return SingleResponse.buildFailure("9026", "FallBack");
    }

    @Override
    public SingleResponse<List<ClientAppVO>> getAppListByIds(Collection<Long> ids) {
        return SingleResponse.buildFailure("9026", "FallBack");
    }
}
