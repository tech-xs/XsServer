package tech.xs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.xs.system.dao.SysOrganizationDao;
import tech.xs.system.domain.entity.SysOrganization;
import tech.xs.system.service.SysOrganizationService;

import java.util.List;

/**
 * 组织结构Service实现类
 *
 * @author 沈家文
 * @date 2021/01/26
 */
@Slf4j
@Service
public class SysOrganizationServiceImpl extends BaseSysServiceImpl<SysOrganizationDao, SysOrganization> implements SysOrganizationService {
    
}
