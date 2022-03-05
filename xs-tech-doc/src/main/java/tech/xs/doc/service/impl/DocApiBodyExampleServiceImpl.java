package tech.xs.doc.service.impl;

import org.springframework.stereotype.Service;
import tech.xs.doc.dao.DocApiBodyExampleDao;
import tech.xs.doc.domain.entity.DocApiBodyExample;
import tech.xs.doc.service.DocApiBodyExampleService;

/**
 * 文档接口内容示例Service实现类
 *
 * @author 沈家文
 * @date 2021/7/8 17:18
 */
@Service
public class DocApiBodyExampleServiceImpl extends BaseDocServiceImpl<DocApiBodyExampleDao, DocApiBodyExample> implements DocApiBodyExampleService {
}
