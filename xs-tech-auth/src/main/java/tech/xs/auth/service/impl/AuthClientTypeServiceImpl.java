package tech.xs.auth.service.impl;

import org.springframework.stereotype.Service;
import tech.xs.auth.domain.entity.AuthClientType;
import tech.xs.auth.dao.AuthClientTypeDao;
import tech.xs.auth.service.AuthClientTypeService;

/**
 * @author imsjw
 * Create Time: 2020/8/22
 */
@Service
public class AuthClientTypeServiceImpl extends BaseAuthServiceImpl<AuthClientTypeDao, AuthClientType> implements AuthClientTypeService {

}
