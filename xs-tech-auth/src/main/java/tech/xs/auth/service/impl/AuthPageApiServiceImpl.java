package tech.xs.auth.service.impl;

import org.springframework.stereotype.Service;
import tech.xs.auth.dao.AuthPageApiDao;
import tech.xs.auth.domain.entity.AuthPageApi;
import tech.xs.auth.service.AuthPageApiService;

/**
 * 页面和资源关联Service实现类
 *
 * @author imsjw
 * Create Time: 2020/9/2
 */
@Service
public class AuthPageApiServiceImpl extends BaseAuthServiceImpl<AuthPageApiDao, AuthPageApi> implements AuthPageApiService {
}
