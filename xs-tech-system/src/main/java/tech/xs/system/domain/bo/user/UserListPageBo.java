package tech.xs.system.domain.bo.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.framework.base.ListPageBo;
import tech.xs.system.constant.SysParamCheckConstant;
import tech.xs.system.domain.entity.SysUser;

@Getter
@Setter
@ToString
public class UserListPageBo extends ListPageBo<SysUser> {

    @Length(max = SysParamCheckConstant.SysUser.USER_NAME_MAX_LENGTH)
    private String likeUserName;
    @Length(max = SysParamCheckConstant.SysUser.PHONE_MAX_LENGTH)
    private String likePhone;
    @Length(max = SysParamCheckConstant.SysUser.EMAIL_MAX_LENGTH)
    private String likeEmail;

}
