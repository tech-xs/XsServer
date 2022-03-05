package tech.xs.auth.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;
import tech.xs.auth.constant.AccountTypeConstant;
import tech.xs.framework.annotation.doc.Quote;
import tech.xs.framework.annotation.doc.QuoteValue;
import tech.xs.framework.base.BaseEnum;

/**
 * 登陆时的账户类型
 *
 * @author 沈家文
 * @date 2021-42-24 17:42
 */
@ToString
@Quote(title = "登陆账号类型")
public enum AccountType implements BaseEnum {

    /**
     * 用户名
     */
    @QuoteValue(title = "用户名")
    USER_NAME(AccountTypeConstant.USER_NAME),

    /**
     * 手机号
     */
    @QuoteValue(title = "手机号")
    PHONE(AccountTypeConstant.PHONE),

    /**
     * 邮箱
     */
    @QuoteValue(title = "邮箱")
    EMAIL(AccountTypeConstant.EMAIL);

    /**
     * 账户类型code
     */
    @EnumValue
    private final int code;

    AccountType(int code) {
        this.code = code;
    }

    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }

    @JsonCreator
    public static AccountType getByCode(Integer code) {
        for (AccountType item : AccountType.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
