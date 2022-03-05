package tech.xs.system.service.impl;

import tech.xs.system.dao.SysCompanyDao;
import tech.xs.system.domain.entity.SysCompany;
import tech.xs.system.service.SysCompanyService;
import org.springframework.stereotype.Service;

/**
 * 公司Service实现类
 *
 * @author 沈家文
 * @date 2021/6/10 16:44
 */
@Service
public class SysCompanyServiceImpl extends BaseSysServiceImpl<SysCompanyDao, SysCompany> implements SysCompanyService {
}
