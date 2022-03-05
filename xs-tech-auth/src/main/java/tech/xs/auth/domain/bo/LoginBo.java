package tech.xs.auth.domain.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.auth.constant.AuthGrantTypeConstant;
import tech.xs.auth.constant.ClientTypeConstant;
import tech.xs.auth.constant.AuthParamCheckConstant;
import tech.xs.auth.enums.AccountType;
import tech.xs.framework.annotation.doc.Param;
import tech.xs.system.constant.SysParamCheckConstant;

import javax.validation.constraints.NotNull;


/**
 * 账号登陆
 *
 * @author 沈家文
 * @date 2021-39-27 11:39
 */
@Getter
@Setter
@ToString
public class LoginBo {

    @NotNull
    @Param(title = "认证授权类型", quote = {AuthGrantTypeConstant.class})
    private Integer grantType;

    @NotNull
    @Param(title = "客户端类型ID", quote = {ClientTypeConstant.class})
    private Long clientTypeId;

    @Param(title = "登陆账号", describe = "当登陆类型为账号密码时,此项为必填项")
    @Length(max = AuthParamCheckConstant.Authorization.ACCOUNT_MAX_LENGTH)
    private String account;

    @Param(title = "登陆密码", describe = {
            "当登陆类型为账号密码时,此项为必填项",
            "密码加密流程如下:\n\t(1)需要在原始密码后面添加固定的盐进行MD5加密\n\t(2)将加密后的密码进行RSA加密\n\t(3)将RSA加密后的数据进行Base64编码\n\t(4)加密的盐和RSA加密的公钥请联系相关负责人"
    })
    @Length(max = SysParamCheckConstant.SysUser.PASSWORD_MAX_LENGTH)
    private String password;

    @Param(title = "Token登陆的Token", describe = "登陆类型为Token登陆时,此项为必填项")
    @Length(max = AuthParamCheckConstant.Authorization.REFRESH_TOKEN_MAX_LENGTH)
    private String refreshToken;

    @Param(title = "账户类型", quote = {AccountType.class})
    private AccountType accountType;

}
