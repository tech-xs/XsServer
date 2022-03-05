package tech.xs.system.service.impl;

import org.springframework.stereotype.Service;
import tech.xs.system.dao.SysApiGroupDao;
import tech.xs.system.domain.entity.SysApiGroup;
import tech.xs.system.service.SysApiGroupService;

/**
 * Api分组
 *
 * @author 沈家文
 * @date 2021-12-23 18:12
 */
@Service
public class SysApiGroupServiceImpl extends BaseSysServiceImpl<SysApiGroupDao, SysApiGroup> implements SysApiGroupService {
}
