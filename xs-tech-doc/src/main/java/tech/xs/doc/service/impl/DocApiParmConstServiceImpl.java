package tech.xs.doc.service.impl;

import org.springframework.stereotype.Service;
import tech.xs.doc.dao.DocApiParmConstDao;
import tech.xs.doc.domain.entity.DocApiParmConst;
import tech.xs.doc.domain.entity.DocConst;
import tech.xs.doc.service.DocApiParmConstService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 参数常量关联Service
 *
 * @author 沈家文
 * @date 2021-36-17 17:36
 */
@Service
public class DocApiParmConstServiceImpl extends BaseDocServiceImpl<DocApiParmConstDao, DocApiParmConst> implements DocApiParmConstService {

    @Resource
    private DocApiParmConstDao docApiParmConstDao;

    @Override
    public List<DocConst> selectConstByParmId(Long id) {
        return docApiParmConstDao.selectConstByParmId(id);
    }

}
