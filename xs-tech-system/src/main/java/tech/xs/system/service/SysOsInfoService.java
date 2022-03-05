package tech.xs.system.service;

import tech.xs.system.domain.entity.SysOsInfo;

/**
 * 系统信息服务
 *
 * @author 沈家文
 * @date 2021/7/1 16:30
 */
public interface SysOsInfoService {

    /**
     * 获取当前系统信息
     *
     * @return
     */
    SysOsInfo current();

}
