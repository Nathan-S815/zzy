package com.nuwa.infrastructure.ticket.database.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MallAccessTokenConfig extends Model<MallAccessTokenConfig> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 客户I端Id
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    private String accountName;

    private String authCode;

    /**
     * 过期时间
     */
    private Date expiresTime;

    private String accessToken;

    private String refreshToken;

    /**
     * 平台ID
     */
    private String platId;


    public static final String ID = "id";

    public static final String CLIENT_ID = "client_id";

    public static final String CLIENT_SECRET = "<REDACTED>";

    public static final String ACCOUNT_NAME = "account_name";

    public static final String AUTH_CODE = "auth_code";

    public static final String EXPIRES_TIME = "expires_time";

    public static final String ACCESS_TOKEN = "access_token";

    public static final String REFRESH_TOKEN = "refresh_token";

    public static final String PLAT_ID = "plat_id";

}
