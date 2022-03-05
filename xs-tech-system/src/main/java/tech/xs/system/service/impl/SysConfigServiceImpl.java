package tech.xs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import tech.xs.common.collections.CollectionUtils;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.constant.NoticeConstant;
import tech.xs.framework.core.notice.mail.AliYunMailEnterpriseConfig;
import tech.xs.framework.core.notice.mail.MailConfig;
import tech.xs.framework.core.notice.mail.NeteaseMailConfig;
import tech.xs.framework.core.notice.mail.QqMailConfig;
import tech.xs.framework.core.notice.sms.aliyun.AliYunSmsConfig;
import tech.xs.framework.core.storage.MinioConfig;
import tech.xs.framework.core.storage.StorageConfig;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.system.constant.SysConfigConstant;
import tech.xs.system.domain.bo.config.GetMailConfigBo;
import tech.xs.system.domain.bo.config.SetMailConfigBo;
import tech.xs.system.domain.entity.SysConfig;
import tech.xs.system.dao.SysConfigDao;
import tech.xs.system.service.SysConfigService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色Service实现类
 *
 * @author 沈家文
 * @date 2020/9/2
 */
@Service
public class SysConfigServiceImpl extends BaseSysServiceImpl<SysConfigDao, SysConfig> implements SysConfigService, AliYunSmsConfig, StorageConfig, MailConfig {

    @Resource
    private SysConfigDao sysConfigDao;

    @Override
    public List<SysConfig> getPublicInitWebConfig() {
        return sysConfigDao.selectList(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigType, SysConfigConstant.Type.PUBLIC_WEB_INIT_CONFIG));
    }

    @Override
    public ApiResult modifyMailConfig(SetMailConfigBo config) {
        String mailType = config.getMailType();
        switch (mailType) {
            case NoticeConstant.MailType.EMPTY: {
                break;
            }
            case NoticeConstant.MailType.ALI_YUN_ENTERPRISE: {
                if (StringUtils.isAnyBlank(config.getHost(), config.getUserName(), config.getPassword(), config.getFormName(), config.getMail())) {
                    return ApiResult.invalidParameter();
                }
                saveAliYunEnterpriseMailConfig(config);
                break;
            }
            case NoticeConstant.MailType.QQ: {
                if (StringUtils.isAnyBlank(config.getUserName(), config.getPassword())) {
                    return ApiResult.invalidParameter();
                }
                saveQqMailConfig(config);
                break;
            }
            case NoticeConstant.MailType.NETEASE: {
                if (StringUtils.isAnyBlank(config.getUserName(), config.getPassword())) {
                    return ApiResult.invalidParameter();
                }
                saveNeteaseMailConfig(config);
                break;
            }
            default: {
                return ApiResult.error(1000, "邮件服务器类型错误");
            }
        }
        sysConfigDao.update(new SysConfig(), Wrappers.<SysConfig>lambdaUpdate()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_TYPE)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.NOTICE_MAIL_TYPE)
                .set(SysConfig::getConfigValue, config.getMailType()));
        return ApiResult.success();
    }

    private void saveNeteaseMailConfig(SetMailConfigBo config) {
        sysConfigDao.update(new SysConfig(), Wrappers.<SysConfig>lambdaUpdate()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_NETEASE)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.NOTICE_MAIL_NETEASE_USER_NAME)
                .set(SysConfig::getConfigValue, config.getUserName()));
        sysConfigDao.update(new SysConfig(), Wrappers.<SysConfig>lambdaUpdate()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_NETEASE)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.NOTICE_MAIL_NETEASE_PASSWORD)
                .set(SysConfig::getConfigValue, config.getPassword()));
    }

    private void saveQqMailConfig(SetMailConfigBo config) {
        sysConfigDao.update(new SysConfig(), Wrappers.<SysConfig>lambdaUpdate()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_QQ)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.NOTICE_MAIL_QQ_USER_NAME)
                .set(SysConfig::getConfigValue, config.getUserName()));
        sysConfigDao.update(new SysConfig(), Wrappers.<SysConfig>lambdaUpdate()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_QQ)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.NOTICE_MAIL_QQ_PASSWORD)
                .set(SysConfig::getConfigValue, config.getPassword()));
    }

    private void saveAliYunEnterpriseMailConfig(SetMailConfigBo config) {
        sysConfigDao.update(new SysConfig(), Wrappers.<SysConfig>lambdaUpdate()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_ALIYUN_ENTERPRISE)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_HOST)
                .set(SysConfig::getConfigValue, config.getHost()));
        sysConfigDao.update(new SysConfig(), Wrappers.<SysConfig>lambdaUpdate()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_ALIYUN_ENTERPRISE)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_USER_NAME)
                .set(SysConfig::getConfigValue, config.getUserName()));
        sysConfigDao.update(new SysConfig(), Wrappers.<SysConfig>lambdaUpdate()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_ALIYUN_ENTERPRISE)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_PASSWORD)
                .set(SysConfig::getConfigValue, config.getPassword()));
        sysConfigDao.update(new SysConfig(), Wrappers.<SysConfig>lambdaUpdate()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_ALIYUN_ENTERPRISE)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_FORM_NAME)
                .set(SysConfig::getConfigValue, config.getFormName()));
        sysConfigDao.update(new SysConfig(), Wrappers.<SysConfig>lambdaUpdate()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_ALIYUN_ENTERPRISE)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_MAIL)
                .set(SysConfig::getConfigValue, config.getMail()));
    }

    @Override
    public GetMailConfigBo getMailConfig() {
        GetMailConfigBo res = new GetMailConfigBo();
        String mailType = getMailType();
        res.setMailType(mailType);
        switch (mailType) {
            case NoticeConstant.MailType.ALI_YUN_ENTERPRISE: {
                setAliYunEnterpriseMailConfig(res);
                return res;
            }
            case NoticeConstant.MailType.QQ: {
                setQqMailConfig(res);
                return res;
            }
            case NoticeConstant.MailType.NETEASE: {
                setNeteaseMailConfig(res);
                return res;
            }
            default: {
                res.setMailType(NoticeConstant.MailType.EMPTY);
                return res;
            }
        }
    }

    private void setNeteaseMailConfig(GetMailConfigBo configBo) {
        List<SysConfig> configList = sysConfigDao.selectList(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_NETEASE));
        for (SysConfig config : configList) {
            if (SysConfigConstant.ConfigKey.NOTICE_MAIL_NETEASE_USER_NAME.equals(config.getConfigKey())) {
                configBo.setUserName(config.getConfigValue());
            } else if (SysConfigConstant.ConfigKey.NOTICE_MAIL_NETEASE_PASSWORD.equals(config.getConfigKey())) {
                configBo.setPassword(config.getConfigValue());
            }
        }
    }

    private void setQqMailConfig(GetMailConfigBo configBo) {
        List<SysConfig> configList = sysConfigDao.selectList(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_QQ));
        for (SysConfig config : configList) {
            if (SysConfigConstant.ConfigKey.NOTICE_MAIL_QQ_USER_NAME.equals(config.getConfigKey())) {
                configBo.setUserName(config.getConfigValue());
            } else if (SysConfigConstant.ConfigKey.NOTICE_MAIL_QQ_PASSWORD.equals(config.getConfigKey())) {
                configBo.setPassword(config.getConfigValue());
            }
        }
    }

    private void setAliYunEnterpriseMailConfig(GetMailConfigBo configBo) {
        List<SysConfig> configList = sysConfigDao.selectList(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_ALIYUN_ENTERPRISE));
        for (SysConfig config : configList) {
            switch (config.getConfigKey()) {
                case SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_HOST: {
                    configBo.setHost(config.getConfigValue());
                    break;
                }
                case SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_USER_NAME: {
                    configBo.setUserName(config.getConfigValue());
                    break;
                }
                case SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_PASSWORD: {
                    configBo.setPassword(config.getConfigValue());
                    break;
                }
                case SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_FORM_NAME: {
                    configBo.setFormName(config.getConfigValue());
                    break;
                }
                case SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_MAIL: {
                    configBo.setMail(config.getConfigValue());
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    @Override
    public String getOneValueToString(String module, String key, String subKey) {
        LambdaQueryWrapper<SysConfig> wrapper = Wrappers.<SysConfig>lambdaQuery();
        if (module != null) {
            wrapper.eq(SysConfig::getConfigModule, module);
        }
        if (key != null) {
            wrapper.eq(SysConfig::getConfigKey, key);
        }
        if (subKey != null) {
            wrapper.eq(SysConfig::getConfigSubKey, subKey);
        }
        SysConfig config = sysConfigDao.selectOne(wrapper);
        if (config == null) {
            return null;
        }
        return config.getConfigValue();
    }

    @Override
    public Integer getOneValueToInt(String module, String key, String subKey) {
        String value = getOneValueToString(module, key, subKey);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return Integer.valueOf(value);
    }

    @Override
    public Long getOneValueToLong(String module, String key, String subKey) {
        String value = getOneValueToString(module, key, subKey);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return Long.valueOf(value);
    }

    @Override
    public String getSmsAccessKey() {
        SysConfig config = sysConfigDao.selectOne(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.ALI_YUN_ACCESS_KEY));
        if (config != null) {
            return config.getConfigValue();
        }
        return null;
    }

    @Override
    public String getSmsSecretKey() {
        SysConfig config = sysConfigDao.selectOne(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.ALI_YUN_SECRET_KEY));
        if (config != null) {
            return config.getConfigValue();
        }
        return null;
    }

    @Override
    public String getStorageType() {
        List<SysConfig> configList = sysConfigDao.selectList(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.STORAGE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.STORAGE_TYPE)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.STORAGE_TYPE));
        if (CollectionUtils.isEmpty(configList)) {
            return null;
        }
        SysConfig config = configList.get(0);
        if (config == null) {
            return null;
        }
        return config.getConfigValue();
    }

    @Override
    public MinioConfig getMinioConfig() {
        MinioConfig minioConfig = new MinioConfig();
        List<SysConfig> configList = sysConfigDao.selectList(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.STORAGE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.STORAGE_MINIO));
        for (SysConfig config : configList) {
            switch (config.getConfigKey()) {
                case SysConfigConstant.ConfigKey.STORAGE_MINIO_ACCESS_KEY: {
                    minioConfig.setAccessKey(config.getConfigValue());
                    break;
                }
                case SysConfigConstant.ConfigKey.STORAGE_MINIO_SECRET_KEY: {
                    minioConfig.setSecretKey(config.getConfigValue());
                    break;
                }
                case SysConfigConstant.ConfigKey.STORAGE_MINIO_ENDPOINT: {
                    minioConfig.setEndpoint(config.getConfigValue());
                    break;
                }
                case SysConfigConstant.ConfigKey.STORAGE_MINIO_BUCKET_NAME: {
                    minioConfig.setBucketName(config.getConfigValue());
                    break;
                }
                default: {
                    break;
                }
            }
        }
        return minioConfig;
    }

    @Override
    public String getMailType() {
        List<SysConfig> configList = sysConfigDao.selectList(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_TYPE)
                .eq(SysConfig::getConfigKey, SysConfigConstant.ConfigKey.NOTICE_MAIL_TYPE));
        if (CollectionUtils.isEmpty(configList)) {
            return null;
        }
        SysConfig config = configList.get(0);
        if (config == null) {
            return null;
        }
        return config.getConfigValue();
    }

    @Override
    public AliYunMailEnterpriseConfig getAliYunMailEnterpriseConfig() {
        AliYunMailEnterpriseConfig mailConfig = new AliYunMailEnterpriseConfig();
        List<SysConfig> configList = sysConfigDao.selectList(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_ALIYUN_ENTERPRISE));
        for (SysConfig config : configList) {
            switch (config.getConfigKey()) {
                case SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_HOST: {
                    mailConfig.setHost(config.getConfigValue());
                    break;
                }
                case SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_USER_NAME: {
                    mailConfig.setUserName(config.getConfigValue());
                    break;
                }
                case SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_PASSWORD: {
                    mailConfig.setPassword(config.getConfigValue());
                    break;
                }
                case SysConfigConstant.ConfigKey.NOTICE_MAIL_ALIYUN_ENTERPRISE_FORM_NAME: {
                    mailConfig.setFormName(config.getConfigValue());
                    break;
                }
                default: {
                    break;
                }
            }
        }
        return mailConfig;
    }

    @Override
    public QqMailConfig getQqMailConfig() {
        QqMailConfig mailConfig = new QqMailConfig();
        List<SysConfig> configList = sysConfigDao.selectList(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_QQ));
        for (SysConfig config : configList) {
            switch (config.getConfigKey()) {
                case SysConfigConstant.ConfigKey.NOTICE_MAIL_QQ_USER_NAME: {
                    mailConfig.setUserName(config.getConfigValue());
                    break;
                }
                case SysConfigConstant.ConfigKey.NOTICE_MAIL_QQ_PASSWORD: {
                    mailConfig.setPassword(config.getConfigValue());
                    break;
                }
                default: {
                    break;
                }
            }
        }
        return mailConfig;
    }

    @Override
    public NeteaseMailConfig getNeteaseMailConfig() {
        NeteaseMailConfig mailConfig = new NeteaseMailConfig();
        List<SysConfig> configList = sysConfigDao.selectList(Wrappers.<SysConfig>lambdaQuery()
                .eq(SysConfig::getConfigModule, SysConfigConstant.Module.NOTICE)
                .eq(SysConfig::getConfigName, SysConfigConstant.ConfigName.NOTICE_MAIL_NETEASE));
        for (SysConfig config : configList) {
            switch (config.getConfigKey()) {
                case SysConfigConstant.ConfigKey.NOTICE_MAIL_NETEASE_USER_NAME: {
                    mailConfig.setUserName(config.getConfigValue());
                    break;
                }
                case SysConfigConstant.ConfigKey.NOTICE_MAIL_NETEASE_PASSWORD: {
                    mailConfig.setPassword(config.getConfigValue());
                    break;
                }
                default: {
                    break;
                }
            }
        }
        return mailConfig;
    }

}
