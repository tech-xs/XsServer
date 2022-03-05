package tech.xs.system.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.xs.common.collections.CollectionUtils;
import tech.xs.common.constant.Symbol;
import tech.xs.common.lang.StringUtils;
import tech.xs.common.util.VerificationCodeUtil;
import tech.xs.framework.constant.BooleanConstant;
import tech.xs.framework.core.cache.Cache;
import tech.xs.framework.core.notice.Notice;
import tech.xs.framework.core.notice.mail.Mail;
import tech.xs.framework.core.storage.Storage;
import tech.xs.system.constant.CacheConstant;
import tech.xs.system.constant.SysConfigConstant;
import tech.xs.system.constant.SysRoleConstant;
import tech.xs.system.constant.SysUserConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.context.XsContext;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.system.domain.bo.user.DeleteUserDetailsBo;
import tech.xs.system.domain.bo.user.UserListPageBo;
import tech.xs.system.domain.entity.SysConfig;
import tech.xs.system.domain.entity.SysRole;
import tech.xs.system.domain.entity.SysUser;
import tech.xs.system.dao.SysUserDao;
import tech.xs.system.service.SysUserService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户Service实现类
 *
 * @author 沈家文
 * @date 2020/8/13
 */
@Slf4j
@Service
public class SysUserServiceImpl extends BaseSysServiceImpl<SysUserDao, SysUser> implements SysUserService {

    private static final String LOGIN_RSA_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCKerKxdtDpSzkn\n" +
            "KxeErOpezpOdwU6RP3JG8CsNyqLsTYv28P0v3J1dhgCY8DNCdo7EDO/HhEc0wadx\n" +
            "xTnluGBa+B6blWz2mONKqbXL/3KwiipUxuvAGLomdKXvKNjckB7xxZw8fzwyy5Tx\n" +
            "jR+9MRtCTjPYqH6CMR46v/vE93GNR34YzWKWWpX+trGawYdDtG6Q2ib0+6O3iNql\n" +
            "Dg91VhgI8h+MsMtp8j0RCiXo6XgTf1EGlBB2Dci+Vi2dIX36WHdGhwQ//ZlALp5S\n" +
            "+MYdWcmGb2iowP5PWYhy4nS67V8duuJdlzv18ibhssfjWUUW76YqkkXdSMKNn38m\n" +
            "kompnUMvAgMBAAECggEAW1UBdjo/Hx8V2td483B4Q39LrMhJ4VJPNKpCOx5ixid/\n" +
            "IKdg1ahTwdMXoccKh9NZ5cQCgTylGg5/OXIy9WfdZSIwTb0H2LyNbDjyRlF8eyMl\n" +
            "ODTaWB/25lYY+tud7BrBzgPfP6dLNNVAru+89zLkH58kYsj+Zy+uGGF21Dm/EsYO\n" +
            "t7TRTPW4fHXGMoWHHG79YnrRvWhQS0Uzx6eEZpqAPElpAr++5MCLqcDvWvzkv7Ol\n" +
            "kDnu0LxaFIXvu8P4QtsKi2Y4OmSVE4N/leBb2SNN4sdApFZhzeHnVIP44QYOQlPn\n" +
            "zoz8+PqEIU8ICqw6jejUdXHtfKzMNBPpIeuYR/ukAQKBgQDnCeEYnMRzK9Czp9Km\n" +
            "D7aq9JQubndb0YToBT/jo0EAqQRniTDYp/FhKCvIK59mDMowTL7T0q/ZtgMf5cpr\n" +
            "3sjiXX/YzFexx/Gr+Wh7J/KvGKsipC9Y5Uvt5w/eZ9LJBkuNsD1oFDyfBNDbWyDG\n" +
            "l0vVzzWQ4v+BytkTv0erLT4zQQKBgQCZcMqYVS4ODtiFIBzUZgl02rhrI5Kmo7Jc\n" +
            "90x3EqtX9lqRtILUK55+UYGWYkrgoL24UouQoQbIgarTVSUDMIf3/kwut6inyfeP\n" +
            "+oR8FXwZTZCDWucuFVx2WzzInR6SD9dR0dpySec0BeVT0BKO7Vq8/P0TFbTNhl0P\n" +
            "fz1B9wSKbwKBgAN4CoEsGWcBSWjpJCiKI9v9QBxSXEhspOo26fapBNjVsNM9JBac\n" +
            "ruseh0nCHeqSVpbTECpuB2XlbbaU5K0p8yoy7TB8IPTyY5aJcCApQWOPo0Ip5OwY\n" +
            "xUMVLQ/Rz0Blb8z40HM3mpFoqRoiDCPvMZxMOCmsvtlcClZZkOHpE/4BAoGAB2HO\n" +
            "zW9jrNd4AUefO3TBRbb0ImtutcewYBqhuiTSy4OVrebiWHTUxj0DoAmQkqgJGKIY\n" +
            "BthIbEMYxdwV0MZ39C1u2tyJJ7Fm0K17zEB4XPtHVZp8/tGWRjdzgC9W9i3+7PfM\n" +
            "q6z0i6Mw+kxLaEbxE6CUSGPF9BFfzswPVWod9M8CgYAAnl/+Xe7T1OWGY024mo4f\n" +
            "cpx8cXRturVB6GhMxoRICdQT7CoeO05tp4MQ99Zm/rpfP+WVc47+fgQyJZAsOwum\n" +
            "yo6YZQvfXwd2y82M9onL3jeVpN0eE2dNtJLXs+0WvQzygGT1pZbFIYqr0dSISdxB\n" +
            "bDsiqr2bjd857v2IPcDY9w==";


    /**
     * 密码加密的盐
     */
    @Value("${auth.password.salt}")
    private String passwordEncoderSalt;

    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private Storage storage;

    @Resource
    private Cache cache;

    @Resource
    private Notice notice;

    @Override
    public boolean isEnablePhoneRegister() {
        return sysConfigService.exist(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigType, SysConfigConstant.Type.PUBLIC_WEB_INIT_CONFIG)
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.SYS)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.ACCOUNT_REGISTER_ENABLE)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.ACCOUNT_REGISTER_PHONE_ENABLE)
                .eq(SysConfig::getConfigValue, BooleanConstant.TRUE + ""));
    }

    @Override
    public boolean isEnableEmailRegister() {
        return sysConfigService.exist(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigType, SysConfigConstant.Type.PUBLIC_WEB_INIT_CONFIG)
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.SYS)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.ACCOUNT_REGISTER_ENABLE)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.ACCOUNT_REGISTER_EMAIL_ENABLE)
                .eq(SysConfig::getConfigValue, BooleanConstant.TRUE + ""));
    }

    @Override
    public void getResetPasswordEmailVerificationCode(String email) {
        String code = cache.getString(CacheConstant.PASSWORD_RESET_EMAIL_CODE + email);
        if (code != null) {
            return;
        }
        code = VerificationCodeUtil.getNumCode(6);
        cache.set(CacheConstant.PASSWORD_RESET_EMAIL_CODE + email, code, CacheConstant.PASSWORD_RESET_EMAIL_COD_TIME);
        cache.delete(CacheConstant.PASSWORD_RESET_EMAIL_CODE_ERROR_NUM + email);
        Mail mail = new Mail();
        mail.setTo(email);
        mail.setTitle("重置密码");
        mail.setContent("您重置密码的验证码为:[" + code + "],您的验证码5分钟内有效");
        try {
            notice.send(mail);
        } catch (Exception e) {
            log.error("邮件发送失败");
            e.printStackTrace();
        }
    }

    @Override
    public void resetPasswordEmail(String email, String password) {
        SysUser user = sysUserDao.selectOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getEmail, email));
        modifyLoginPassword(user.getId(), password);
    }

    @Override
    public void getRegisterEmailVerificationCode(String email) {
        String code = cache.getString(CacheConstant.ACCOUNT_REGISTER_EMAIL_CODE + email);
        if (code != null) {
            return;
        }
        code = VerificationCodeUtil.getNumCode(6);
        cache.set(CacheConstant.ACCOUNT_REGISTER_EMAIL_CODE + email, code, CacheConstant.ACCOUNT_REGISTER_EMAIL_COD_TIME);
        cache.delete(CacheConstant.ACCOUNT_REGISTER_EMAIL_CODE_ERROR_NUM + email);
        Mail mail = new Mail();
        mail.setTo(email);
        mail.setTitle("账号注册");
        mail.setContent("您的账号注册验证码为:[" + code + "],您的验证码5分钟内有效");
        try {
            notice.send(mail);
        } catch (Exception e) {
            log.error("邮件发送失败");
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerEmail(String email, String password) {
        SysUser user = new SysUser();
        user.setEmail(email);
        user.setUserName(IdUtil.simpleUUID());
        SysRole role = sysRoleService.getByCode(SysRoleConstant.Code.ORDINARY);
        List<Long> roleIds = new ArrayList<>();
        roleIds.add(role.getId());
        add(user, password, roleIds);
    }

    @Override
    public void getRegisterPhoneVerificationCode(String email) {
        String code = cache.get(CacheConstant.ACCOUNT_REGISTER_PHONE_CODE, String.class);
        if (code != null) {
            return;
        }
        code = VerificationCodeUtil.getNumCode(6);
        cache.set(CacheConstant.ACCOUNT_REGISTER_PHONE_CODE, code, CacheConstant.ACCOUNT_REGISTER_PHONE_COD_TIME);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysUser user, String password, List<Long> roleIds) {
        sysUserDao.insert(user);
        password = loginPasswordDecode(password);
        password = loginPasswordEncode(password);
        sysUserDao.updateLoginPassword(user.getId(), password);
        if (roleIds != null) {
            sysUserRoleService.add(user.getId(), roleIds);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDetails(DeleteUserDetailsBo bo) {
        sysUserRoleService.deleteByUserId(bo.getIdList());
        for (Long id : bo.getIdList()) {
            SysUser user = sysUserDao.selectById(id);
            if (user == null) {
                continue;
            }
            if (StringUtils.isBlank(user.getAvatarUrl())) {
                continue;
            }
            storage.delete(user.getAvatarUrl());
        }
        sysUserDao.delete(Wrappers.<SysUser>lambdaQuery().in(BaseEntity::getId, bo.getIdList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyById(SysUser user, List<Long> roleIds) {
        sysUserDao.updateById(user);
        if (roleIds != null) {
            sysUserRoleService.deleteByUserId(user.getId());
            sysUserRoleService.add(user.getId(), roleIds);
        }
    }

    @Override
    public void modifyLoginPassword(Long userId, String password) {
        password = loginPasswordDecode(password);
        password = loginPasswordEncode(password);
        sysUserDao.updateLoginPassword(userId, password);
    }

    @Override
    public ApiResult<Object> modifyLoginPasswordByOldPassword(String oldPassword, String newPassword) {
        Long currentUserId = XsContext.getUserId();
        SysUser user = sysUserDao.selectById(currentUserId);
        if (user == null) {
            return ApiResult.error(1000, "用户未登录");
        }
        oldPassword = loginPasswordDecode(oldPassword);
        newPassword = loginPasswordDecode(newPassword);

        oldPassword = loginPasswordEncode(oldPassword);
        if (!sysUserDao.checkLoginPassword(currentUserId, oldPassword)) {
            return ApiResult.error(1000, "当前密码错误");
        }
        newPassword = loginPasswordEncode(newPassword);
        sysUserDao.updateLoginPassword(currentUserId, newPassword);
        return ApiResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult<String> modifyAvatar(Long userId, MultipartFile avatarFile) throws Exception {
        String originalFilename = avatarFile.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            return ApiResult.error(1000, "文件不存在");
        }
        String extName = FileUtil.extName(originalFilename);
        if (StringUtils.isBlank(extName)) {
            return ApiResult.error(1001, "文件类型错误");
        }
        SysUser user = sysUserDao.selectById(userId);
        if (user == null) {
            return ApiResult.error(1002, "用户不存在");
        }
        String filePath = SysUserConstant.AVATAR_ROOT_URL + user.getId() + Symbol.SLASH + IdUtil.simpleUUID() + Symbol.POINT + extName;
        storage.upload(filePath, avatarFile.getInputStream());
        if (StringUtils.isNotBlank(user.getAvatarUrl())) {
            try {
                storage.delete(user.getAvatarUrl());
            } catch (Exception e) {
                log.error("头像文件删除失败: userId:" + userId + " filePath:" + user.getAvatarUrl());
                e.printStackTrace();
            }
        }
        SysUser updateUser = new SysUser();
        updateUser.setId(userId);
        updateUser.setAvatarUrl(filePath);
        sysUserDao.updateById(updateUser);
        return ApiResult.success(filePath);
    }

    @Override
    public Page<SysUser> listPage(UserListPageBo bo) {
        Page<SysUser> page = bo.getPage();
        sysUserDao.selectPage(page, Wrappers.<SysUser>lambdaQuery()
                .like(StringUtils.isNotBlank(bo.getLikeUserName()), SysUser::getUserName, bo.getLikeUserName())
                .like(StringUtils.isNotBlank(bo.getLikePhone()), SysUser::getPhone, bo.getLikePhone())
                .like(StringUtils.isNotBlank(bo.getLikeEmail()), SysUser::getEmail, bo.getLikeEmail())
                .orderByDesc(SysUser::getCreateTime));
        List<SysUser> userList = page.getRecords();
        if (CollectionUtils.isEmpty(userList)) {
            return page;
        }
        for (SysUser user : userList) {
            user.setRoleIdList(sysUserRoleService.listRoleIdByUserId(user.getId()));
        }
        return page;
    }

    @Override
    public String loginPasswordEncode(String password) {
        return DigestUtils.md5DigestAsHex((password + passwordEncoderSalt).getBytes());
    }

    @Override
    public String loginPasswordDecode(String password) {
        try {
            RSA rsa = new RSA(LOGIN_RSA_PRIVATE_KEY, null);
            return rsa.decryptStr(password, KeyType.PrivateKey);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SysUser getCurrentUser() {
        Long currentUserId = XsContext.getUserId();
        if (currentUserId == null) {
            return null;
        }
        return sysUserDao.selectById(currentUserId);
    }

}
