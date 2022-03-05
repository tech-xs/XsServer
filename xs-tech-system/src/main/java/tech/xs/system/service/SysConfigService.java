package tech.xs.system.service;


import tech.xs.framework.domain.model.ApiResult;
import tech.xs.system.domain.bo.config.GetMailConfigBo;
import tech.xs.system.domain.bo.config.SetMailConfigBo;
import tech.xs.system.domain.entity.SysConfig;

import java.util.List;

/**
 * 系统配置Service
 *
 * @author 沈家文
 * @date 2020/10/15
 */
public interface SysConfigService extends BaseSysService<SysConfig> {

    /**
     * 获取公开的网页初始化配置
     *
     * @return
     */
    List<SysConfig> getPublicInitWebConfig();

    /**
     * 修改邮件服务器配置
     *
     * @param config
     * @return
     */
    ApiResult modifyMailConfig(SetMailConfigBo config);

    /**
     * 获取邮箱配置
     * @return
     */
    GetMailConfigBo getMailConfig();

    /**
     * 获取配置中的值
     *
     * @param module
     * @param key
     * @param subKey
     * @return
     */
    String getOneValueToString(String module, String key, String subKey);

    /**
     * 获取配置中的值
     *
     * @param module
     * @param key
     * @param subKey
     * @return
     */
    Integer getOneValueToInt(String module, String key, String subKey);

    /**
     * 获取配置中的值
     *
     * @param module
     * @param key
     * @param subKey
     * @return
     */
    Long getOneValueToLong(String module, String key, String subKey);

}
