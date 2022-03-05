package tech.xs.doc.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.xs.common.collections.CollectionUtils;
import tech.xs.doc.dao.DocApiBodyDao;
import tech.xs.doc.domain.entity.DocApiBody;
import tech.xs.doc.domain.entity.DocApiBodyExample;
import tech.xs.doc.service.DocApiBodyService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 文档接口内容Service实现类
 *
 * @author 沈家文
 * @date 2021/7/8 17:18
 */
@Service
public class DocApiBodyServiceImpl extends BaseDocServiceImpl<DocApiBodyDao, DocApiBody> implements DocApiBodyService {

    @Resource
    private DocApiBodyDao docApiBodyDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        docApiBodyExampleService.delete(Wrappers.<DocApiBodyExample>lambdaQuery()
                .eq(DocApiBodyExample::getBodyId, id));
        docApiBodyDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIdList(Collection<Long> idList) {
        for (Long id : idList) {
            deleteById(id);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Wrapper<DocApiBody> queryWrapper) {
        List<DocApiBody> bodyList = docApiBodyDao.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(bodyList)) {
            for (DocApiBody body : bodyList) {
                deleteById(body.getId());
            }
        }
    }
}
