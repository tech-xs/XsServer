package tech.xs.doc.service.impl;


import org.springframework.stereotype.Service;
import tech.xs.doc.dao.DocConstValueDao;
import tech.xs.doc.domain.entity.DocConstValue;
import tech.xs.doc.service.DocConstValueService;

/**
 * 常量值文档
 *
 * @author 沈家文
 * @date 2021-23-17 16:23
 */
@Service
public class DocConstValueServiceImpl extends BaseDocServiceImpl<DocConstValueDao, DocConstValue> implements DocConstValueService {
}
