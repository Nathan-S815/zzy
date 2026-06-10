package com.nuwa.infrastructure.zeus.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * OssDomainConfig oss域名
 *
 * @author hy
 * @date 2020/11/11 10:02
 * @since 1.0.0
 */
@Component
@Data
public class OssDomainConfigEx {

    @Value("${oss.file.domain}")
    private String ossDomain;

    @Value("${upYun.endPoint}")
    private String upDomain;
}
