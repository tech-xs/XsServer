package tech.xs.doc.service.impl;

import org.springframework.stereotype.Service;
import tech.xs.doc.dao.DocApiParmExampleDao;
import tech.xs.doc.domain.entity.DocApiParmExample;
import tech.xs.doc.service.DocApiParmExampleService;

/**
 * 接口参数示例Service实现类
 *
 * @author 沈家文
 * @date 2021/5/25 17:33
 */
@Service
public class DocApiParmExampleServiceImpl extends BaseDocServiceImpl<DocApiParmExampleDao, DocApiParmExample> implements DocApiParmExampleService {
}
