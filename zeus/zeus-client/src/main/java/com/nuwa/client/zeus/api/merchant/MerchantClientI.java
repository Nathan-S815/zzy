package com.nuwa.client.zeus.api.merchant;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.api.merchant.vo.ClientAppVO;
import com.nuwa.client.zeus.api.merchant.vo.MerchantSimpleVO;
import com.nuwa.client.zeus.api.order.MaterialClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;


/**
 * @author hy
 */
@FeignClient(
        value = "zeus-client-provider",
        fallback = MaterialClientFallBack.class
)
public interface MerchantClientI {
    String API_PREFIX = "/merchant";

    /**
     * 批量获取商户信息
     *
     * @param ids 商户id
     * @return List<MerchantSimpleVO>
     */
    @PostMapping(API_PREFIX + "/getMerchantListByIds")
    @ResponseBody
    public SingleResponse<List<MerchantSimpleVO>> getMerchantListByIds(@RequestParam Collection<Long> ids);


    @PostMapping(API_PREFIX + "/getAppListByIds")
    @ResponseBody
    public SingleResponse<List<ClientAppVO>> getAppListByIds(@RequestParam Collection<Long> ids);

}
