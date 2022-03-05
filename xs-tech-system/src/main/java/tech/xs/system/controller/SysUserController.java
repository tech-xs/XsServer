package tech.xs.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.annotation.doc.Api;
import tech.xs.framework.annotation.doc.ApiGroup;
import tech.xs.framework.context.XsContext;
import tech.xs.framework.enums.ResultEnum;
import tech.xs.framework.enums.SexEnum;
import tech.xs.system.constant.CacheConstant;
import tech.xs.system.domain.bo.user.*;
import tech.xs.system.domain.entity.SysCompany;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.constant.ParamCheckConstant;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.domain.model.PageResult;
import tech.xs.system.constant.SysParamCheckConstant;
import tech.xs.system.domain.entity.SysRole;
import tech.xs.system.domain.entity.SysUser;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.List;

/**
 * @author 沈家文
 * @date 2020/8/13
 */
@ApiGroup(name = "用户管理")
@Validated
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseSysController {


    @Api(name = "账号注册-获取邮箱验证码", describe = "通过邮箱注册账号的业务过程中,用于获取邮箱验证码的接口,验证邮箱的有效性")
    @PostMapping("/register/get/email/code")
    public ApiResult getRegisterEmailVerificationCode(@NotBlank @Length(max = SysParamCheckConstant.SysUser.EMAIL_MAX_LENGTH) String email) {
        if (!sysUserService.isEnableEmailRegister()) {
            return ApiResult.error(1001, "通过邮箱注册账号功能已经关闭");
        }
        if (sysUserService.exist(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getEmail, email))) {
            return ApiResult.error(1000, "邮箱已经被注册");
        }
        sysUserService.getRegisterEmailVerificationCode(email);
        return ApiResult.success();
    }

    @Api(name = "通过邮箱进行账号注册")
    @PostMapping("/register/email")
    public ApiResult registerEmail(@Valid @RequestBody RegisterEmailBo bo) {
        String email = bo.getEmail();
        String code = cache.getString(CacheConstant.ACCOUNT_REGISTER_EMAIL_CODE + email);
        if (code == null) {
            return ApiResult.error(1000, "验证码已过期");
        }
        if (!sysUserService.isEnableEmailRegister()) {
            return ApiResult.error(1004, "通过邮箱注册账号功能已经关闭");
        }
        if (!code.equals(bo.getVerificationCode())) {
            Integer errorNum = cache.getInteger(CacheConstant.ACCOUNT_REGISTER_EMAIL_CODE_ERROR_NUM + email);
            if (errorNum == null) {
                cache.set(CacheConstant.ACCOUNT_REGISTER_EMAIL_CODE_ERROR_NUM + email, 1, CacheConstant.ACCOUNT_REGISTER_EMAIL_COD_TIME);
                return ApiResult.error(1001, "验证码错误");
            }
            errorNum++;
            if (errorNum >= ParamCheckConstant.VERIFICATION_CODE_MAX_INPUT_NUM) {
                cache.delete(CacheConstant.ACCOUNT_REGISTER_EMAIL_CODE_ERROR_NUM + email);
                cache.delete(CacheConstant.ACCOUNT_REGISTER_EMAIL_CODE + email);
                return ApiResult.error(1002, "验证码输入次数达到上限,请重新获取验证码");
            }
            cache.set(CacheConstant.ACCOUNT_REGISTER_EMAIL_CODE_ERROR_NUM + email, errorNum, CacheConstant.ACCOUNT_REGISTER_EMAIL_COD_TIME);
            return ApiResult.error(1001, "验证码错误");
        }
        if (sysUserService.exist(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getEmail, bo.getEmail()))) {
            return ApiResult.error(1003, "邮箱已经被注册");
        }
        sysUserService.registerEmail(bo.getEmail(), bo.getPassword());
        cache.delete(CacheConstant.ACCOUNT_REGISTER_EMAIL_CODE_ERROR_NUM + email);
        cache.delete(CacheConstant.ACCOUNT_REGISTER_EMAIL_CODE + email);
        return ApiResult.success();
    }

    @Api(name = "重置密码-获取邮箱验证码", describe = "通过邮箱重置密码的业务中,获取邮箱验证码")
    @PostMapping("/password/reset/get/email/code")
    public ApiResult getResetPasswordEmailVerificationCode(@NotBlank @Length(max = SysParamCheckConstant.SysUser.EMAIL_MAX_LENGTH) String email) {
        if (!sysUserService.exist(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getEmail, email))) {
            return ApiResult.error(1000, "账号不存在");
        }
        sysUserService.getResetPasswordEmailVerificationCode(email);
        return ApiResult.success();
    }

    @Api(name = "账号注册-获取手机号验证码", describe = "通过手机号注册账号的业务过程中,用于获取手机号验证码的接口,验证手机号的有效性")
    @PostMapping("/register/get/phone/code")
    public ApiResult getRegisterPhoneVerificationCode(@NotNull @Length(max = ParamCheckConstant.PHONE_LENGTH, min = ParamCheckConstant.PHONE_LENGTH) String phone) {
        if (!sysUserService.isEnablePhoneRegister()) {
            return ApiResult.error(1000, "通过手机号注册账号功能已经关闭");
        }
        if (sysUserService.exist(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, phone))) {
            return ApiResult.error(1000, "手机号已经被注册");
        }
        sysUserService.getRegisterPhoneVerificationCode(phone);
        return ApiResult.success();
    }

    @Api(name = "通过邮箱重置密码")
    @PostMapping("/password/reset/email")
    public ApiResult resetPasswordEmail(@Valid @RequestBody PasswordResetEmailBo bo) {
        String email = bo.getEmail();
        String code = cache.getString(CacheConstant.PASSWORD_RESET_EMAIL_CODE + email);
        if (code == null) {
            return ApiResult.error(1000, "验证码已过期");
        }
        if (!code.equals(bo.getVerificationCode())) {
            Integer errorNum = cache.getInteger(CacheConstant.PASSWORD_RESET_EMAIL_CODE_ERROR_NUM + email);
            if (errorNum == null) {
                cache.set(CacheConstant.PASSWORD_RESET_EMAIL_CODE_ERROR_NUM + email, 1, CacheConstant.PASSWORD_RESET_EMAIL_COD_TIME);
                return ApiResult.error(1001, "验证码错误");
            }
            errorNum++;
            if (errorNum >= ParamCheckConstant.VERIFICATION_CODE_MAX_INPUT_NUM) {
                cache.delete(CacheConstant.PASSWORD_RESET_EMAIL_CODE_ERROR_NUM + email);
                cache.delete(CacheConstant.PASSWORD_RESET_EMAIL_CODE + email);
                return ApiResult.error(1002, "验证码输入次数达到上限,请重新获取验证码");
            }
            cache.set(CacheConstant.PASSWORD_RESET_EMAIL_CODE_ERROR_NUM + email, errorNum, CacheConstant.PASSWORD_RESET_EMAIL_COD_TIME);
            return ApiResult.error(1001, "验证码错误");
        }
        if (!sysUserService.exist(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getEmail, bo.getEmail()))) {
            return ApiResult.error(1003, "账号不存在");
        }
        sysUserService.resetPasswordEmail(email, bo.getPassword());
        cache.delete(CacheConstant.PASSWORD_RESET_EMAIL_CODE_ERROR_NUM + email);
        cache.delete(CacheConstant.PASSWORD_RESET_EMAIL_CODE + email);
        return ApiResult.success();
    }

    /**
     * 添加用户
     *
     * @return 返回添加完成的用户信息
     */
    @PutMapping("/add")
    public ApiResult<SysUser> add(@RequestBody @Valid AddUserBo bo) {
        if (sysUserService.exist(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, bo.getUserName()))) {
            return ApiResult.error(1000, "用户名已存在");
        }
        SysUser user = new SysUser();
        if (StringUtils.isNotBlank(bo.getPhone())) {
            if (sysUserService.exist(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, bo.getPhone()))) {
                return ApiResult.error(1001, "手机号已经存在");
            }
            user.setPhone(bo.getPhone());
        }
        if (StringUtils.isNotBlank(bo.getEmail())) {
            if (sysUserService.exist(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getEmail, bo.getEmail()))) {
                return ApiResult.error(1002, "邮箱已经存在");
            }
            user.setEmail(bo.getEmail());
        }
        if (CollectionUtils.isNotEmpty(bo.getRoleIdList())) {
            if (new HashSet<>(bo.getRoleIdList()).size() != bo.getRoleIdList().size()) {
                return ApiResult.error(1003, "存在重复的角色");
            }
            long count = sysRoleService.count(Wrappers.<SysRole>lambdaQuery().in(BaseEntity::getId, bo.getRoleIdList()));
            if (count != bo.getRoleIdList().size()) {
                return ApiResult.error(1004, "列表中存在未知角色");
            }
        }
        if (bo.getCompanyId() != null) {
            SysCompany company = sysCompanyService.getById(bo.getCompanyId());
            if (company == null) {
                return ApiResult.error(1005, "公司不存在");
            }
            user.setCompanyId(bo.getCompanyId());
        }

        user.setUserName(bo.getUserName());
        user.setNickName(bo.getNickName());
        user.setSex(bo.getSex());
        user.setAccountStatus(bo.getAccountStatus());
        sysUserService.add(user, bo.getPassword(), bo.getRoleIdList());
        return ApiResult.success(user);
    }

    @PostMapping("/delete/details")
    public ApiResult<Object> deleteDetails(@RequestBody @Valid DeleteUserDetailsBo bo) {
        sysUserService.deleteDetails(bo);
        return ApiResult.success();
    }

    @PostMapping("/modify/id")
    public ApiResult<Object> modifyById(@RequestBody @Valid ModifyUserBo bo) {
        SysUser user = sysUserService.getById(bo.getId());
        if (user == null) {
            return ApiResult.error(1000, "用户不存在");
        }
        SysUser newUser = new SysUser();
        if (StringUtils.isNotBlank(bo.getUserName())) {
            if (sysUserService.exist(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, bo.getUserName()).ne(BaseEntity::getId, bo.getId()))) {
                return ApiResult.error(1001, "用户名已存在");
            }
            newUser.setUserName(bo.getUserName());
        }

        if (StringUtils.isNotBlank(bo.getPhone())) {
            if (sysUserService.exist(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getPhone, bo.getPhone()).ne(BaseEntity::getId, bo.getId()))) {
                return ApiResult.error(1002, "手机号已经存在");
            }
            newUser.setPhone(bo.getPhone());
        }
        if (StringUtils.isNotBlank(bo.getEmail())) {
            if (sysUserService.exist(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getEmail, bo.getEmail()).ne(BaseEntity::getId, bo.getId()))) {
                return ApiResult.error(1002, "邮箱已经存在");
            }
            newUser.setEmail(bo.getEmail());
        }
        if (CollectionUtils.isNotEmpty(bo.getRoleIdList())) {
            if (new HashSet<>(bo.getRoleIdList()).size() != bo.getRoleIdList().size()) {
                return ApiResult.error(1003, "存在重复的角色");
            }
            long count = sysRoleService.count(Wrappers.<SysRole>lambdaQuery().in(BaseEntity::getId, bo.getRoleIdList()));
            if (count != bo.getRoleIdList().size()) {
                return ApiResult.error(1004, "列表中存在未知角色");
            }
        }
        if (bo.getCompanyId() != null) {
            SysCompany company = sysCompanyService.getById(bo.getCompanyId());
            if (company == null) {
                return ApiResult.error(1005, "公司不存在");
            }
            newUser.setCompanyId(bo.getCompanyId());
        }

        newUser.setId(user.getId());
        newUser.setNickName(bo.getNickName());
        newUser.setSex(bo.getSex());
        newUser.setAccountStatus(bo.getAccountStatus());
        sysUserService.modifyById(newUser, bo.getRoleIdList());
        return ApiResult.success();
    }

    /**
     * 当前登陆用户修改自身信息
     *
     * @return 默认返回成功
     */
    @PostMapping("/current/modify/id")
    public ApiResult<Object> currentModifyById(
            @Length(max = SysParamCheckConstant.SysUser.NICK_NAME_MAX_LENGTH) String nickName,
            SexEnum sex
    ) {
        Long currentUserId = XsContext.getUserId();
        if (currentUserId == null) {
            return ApiResult.success();
        }
        SysUser user = new SysUser();
        user.setId(currentUserId);
        user.setNickName(nickName);
        user.setSex(sex);
        sysUserService.updateById(user);
        return ApiResult.success();
    }

    @PostMapping("/modify/password/id")
    public ApiResult<Object> modifyPasswordById(@RequestBody @Valid ModifyUserPasswordBo bo) {
        sysUserService.modifyLoginPassword(bo.getId(), bo.getPassword());
        return ApiResult.success();
    }

    /**
     * 当前用户根据旧密码修改密码
     *
     * @param oldPassword 旧密码(加密后的密码)
     * @param newPassword 新密码(加密后的密码)
     * @return 默认返回成功
     */
    @PostMapping("/current/modify/password")
    public ApiResult<Object> modifyPasswordCurrUser(
            @NotBlank String oldPassword,
            @NotBlank String newPassword
    ) {
        return sysUserService.modifyLoginPasswordByOldPassword(oldPassword, newPassword);
    }

    /**
     * 修改用户头像
     *
     * @return 返回用户头像URL地址
     */
    @PostMapping(value = "/current/modify/avatar", headers = "content-type=multipart/form-data")
    public ApiResult<String> modifyCurrentAvatar(@RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile) throws Exception {
        Long currentUserId = XsContext.getUserId();
        if (currentUserId == null) {
            return ApiResult.error(ResultEnum.permissionDenied);
        }
        return sysUserService.modifyAvatar(currentUserId, avatarFile);
    }

    /**
     * 分页查询用户
     *
     * @return 返回分页查询结果
     */
    @GetMapping("/list/page")
    public ApiResult<List<SysUser>> listPage(@Valid UserListPageBo bo) {
        return PageResult.success(sysUserService.listPage(bo));
    }

    /**
     * 获取当前用户信息
     *
     * @return 返回当前用户信息
     */
    @Api(name = "获取当前登录用户信息")
    @GetMapping("/current")
    public ApiResult<SysUser> getCurrentUser() {
        return ApiResult.success(sysUserService.getCurrentUser());
    }

}
