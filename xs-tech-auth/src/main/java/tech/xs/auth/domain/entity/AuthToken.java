package tech.xs.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.xs.framework.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 授权token存储的表
 *
 * @author imsjw
 * Create Time: 2020/8/5
 */
@Getter
@Setter
@ToString
@TableName("auth_token")
public class AuthToken extends BaseEntity {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 客户端类型ID
     */
    @TableField("client_type_id")
    private Long clientTypeId;

    /**
     * access token
     */
    @TableField("access_token")
    private String accessToken;

    /**
     * refresh token
     */
    @TableField("refresh_token")
    private String refreshToken;

    /**
     * access_token失效时间
     */
    @TableField("access_token_invalid_time")
    private Long accessTokenInvalidTime;

    /**
     * refresh_token失效时间
     */
    @TableField("refresh_token_invalid_time")
    private Long refreshTokenInvalidTime;

    @Override
    public Object clone() throws CloneNotSupportedException {
        AuthToken res = new AuthToken();
        clone(res);
        res.setUserId(this.getUserId());
        res.setClientTypeId(this.getClientTypeId());
        res.setAccessToken(this.getAccessToken());
        res.setRefreshToken(this.getRefreshToken());
        res.setAccessTokenInvalidTime(this.getAccessTokenInvalidTime());
        res.setRefreshTokenInvalidTime(this.getRefreshTokenInvalidTime());
        return res;
    }

}

