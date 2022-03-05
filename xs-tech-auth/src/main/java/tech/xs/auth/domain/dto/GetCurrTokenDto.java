package tech.xs.auth.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取当前token DTO
 *
 * @author 沈家文
 * @date 2021/7/8 17:15
 */
@ToString
@Getter
@Setter
public class GetCurrTokenDto {

    /**
     * tokenID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 客户端类型ID
     */
    private Long clientTypeId;

    /**
     * access token
     */
    private String accessToken;

    /**
     * refresh token
     */
    private String refreshToken;

    /**
     * access_token失效时间
     */
    private Long accessTokenInvalidTime;

    /**
     * refresh_token失效时间
     */
    private Long refreshTokenInvalidTime;

}
