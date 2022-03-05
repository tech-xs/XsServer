package tech.xs.doc.service.impl;

import org.springframework.stereotype.Service;
import tech.xs.doc.dao.DocApiParmDao;
import tech.xs.doc.domain.entity.DocApiParm;
import tech.xs.doc.service.DocApiParmService;

/**
 * 接口参数Service实现类
 *
 * @author 沈家文
 * @date 2021/5/25 17:33
 */
@Service
public class DocApiParmServiceImpl extends BaseDocServiceImpl<DocApiParmDao, DocApiParm> implements DocApiParmService {
}
