package tech.xs.auth.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;
import tech.xs.auth.constant.AuthGrantTypeConstant;

/**
 * 授权类型
 *
 * @author 沈家文
 * @date 2021-25-24 17:25
 * @see AuthGrantTypeConstant
 */
@Getter
@ToString
public enum GrantType {

    /**
     * 账号密码登陆
     */
    PASSWORD(AuthGrantTypeConstant.PASSWORD),
    /**
     * Token登陆
     */
    TOKEN(AuthGrantTypeConstant.TOKEN);

    /**
     * 授权类型code
     */
    @EnumValue
    private final int code;

    GrantType(int code) {
        this.code = code;
    }

}
