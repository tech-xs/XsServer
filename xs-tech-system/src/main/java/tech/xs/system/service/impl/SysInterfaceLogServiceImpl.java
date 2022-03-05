package tech.xs.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.xs.system.dao.SysInterfaceLogDao;
import tech.xs.system.domain.entity.SysInterfaceLog;
import tech.xs.system.service.SysInterfaceLogService;

/**
 * 第三方接口调用日志Service实现类
 *
 * @author 沈家文
 * @date 2021/04/22 14:34
 */
@Slf4j
@Service
public class SysInterfaceLogServiceImpl extends BaseSysServiceImpl<SysInterfaceLogDao, SysInterfaceLog> implements SysInterfaceLogService {


}
