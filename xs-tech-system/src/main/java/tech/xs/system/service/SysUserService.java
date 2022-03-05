package tech.xs.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.system.domain.bo.user.DeleteUserDetailsBo;
import tech.xs.system.domain.bo.user.UserListPageBo;
import tech.xs.system.domain.entity.SysUser;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * 用户Service
 *
 * @author 沈家文
 * @date 2020/8/13
 */
public interface SysUserService extends BaseSysService<SysUser> {

    /**
     * 是否开启通过手机号注册账号功能
     *
     * @return
     */
    boolean isEnablePhoneRegister();

    /**
     * 是否开启通过邮箱注册账号功能
     *
     * @return
     */
    boolean isEnableEmailRegister();

    /**
     * 通过邮箱重置密码的业务中,获取有限验证码
     *
     * @param email
     */
    void getResetPasswordEmailVerificationCode(String email);

    /**
     * 通过邮箱重置密码
     *
     * @param email
     * @param password
     */
    void resetPasswordEmail(String email, String password);

    /**
     * 通过邮箱注册账号,中获取邮箱验证码
     *
     * @param email
     */
    void getRegisterEmailVerificationCode(String email);

    /**
     * 通过邮箱注册账号
     *
     * @param email
     * @param password
     */
    void registerEmail(String email, String password);

    /**
     * 通过手机号注册账号,获取手机验证码接口
     *
     * @param email
     */
    void getRegisterPhoneVerificationCode(String email);

    /**
     * 添加用户
     *
     * @param user     用户
     * @param password 密码(加密后的密码)
     * @param roleIds  角色id列表
     */
    void add(SysUser user, String password, List<Long> roleIds);

    /**
     * 根据用户列表删除用户全部书籍
     *
     * @param bo
     */
    void deleteDetails(DeleteUserDetailsBo bo);

    /**
     * 修改用户
     *
     * @param user    用户信息
     * @param roleIds 角色id列表
     */
    void modifyById(SysUser user, List<Long> roleIds);

    /**
     * 修改用户密码
     *
     * @param userId   用户id
     * @param password 密码(加密后密码)
     */
    void modifyLoginPassword(Long userId, String password);

    /**
     * 当前用户根据旧密码修改密码
     *
     * @param oldPassword 旧密码(加密后的密码)
     * @param newPassword 新密码(加密后的密码)
     * @return 默认返回成功
     */
    ApiResult<Object> modifyLoginPasswordByOldPassword(String oldPassword, String newPassword);

    /**
     * 根据ID修改用户头像
     *
     * @param userId     用户id
     * @param avatarFile 头像文件
     * @return 返回头像url
     * @throws Exception 文件上传异常
     */
    ApiResult<String> modifyAvatar(Long userId, MultipartFile avatarFile) throws Exception;

    /**
     * 分页查询用户
     *
     * @return 返回分页查询的结果集
     */
    Page<SysUser> listPage(UserListPageBo bo);

    /**
     * 加密登陆密码
     *
     * @param password 前端加密后的密码
     * @return 返回二次加密后的密码
     */
    String loginPasswordEncode(String password);

    /**
     * 对Rsa传输过来的加密密码进行RSA解密
     *
     * @param password 对传输过程加密后的密码进行解密
     * @return 如果为null 则解密失败
     */
    String loginPasswordDecode(String password);

    /**
     * 获取当前登录用户
     *
     * @return 返回当前用户信息
     */
    SysUser getCurrentUser();

}
