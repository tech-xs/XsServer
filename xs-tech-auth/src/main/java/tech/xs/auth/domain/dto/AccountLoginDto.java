package tech.xs.auth.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 账号密码登陆Dto
 *
 * @author 沈家文
 * @date 2021/5/28 13:52
 */
@Getter
@Setter
@ToString
public class AccountLoginDto {

    private Long userId;

    private String accessToken;

    private String refreshToken;

}
