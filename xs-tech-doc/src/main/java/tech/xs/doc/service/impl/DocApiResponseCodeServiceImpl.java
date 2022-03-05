package tech.xs.doc.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import tech.xs.doc.dao.DocApiResponseCodeDao;
import tech.xs.doc.domain.entity.DocApiResponseCode;
import tech.xs.doc.service.DocApiResponseCodeService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文档接口响应码Service实现类
 *
 * @author 沈家文
 * @date 2021/7/8 17:16
 */
@Service
public class DocApiResponseCodeServiceImpl extends BaseDocServiceImpl<DocApiResponseCodeDao, DocApiResponseCode> implements DocApiResponseCodeService {

    @Resource
    private DocApiResponseCodeDao docApiResponseCodeDao;

    @Override
    public List<DocApiResponseCode> listGlobalResponseCode() {
        return docApiResponseCodeDao.selectList(Wrappers.<DocApiResponseCode>lambdaQuery()
                .isNull(DocApiResponseCode::getApiId)
                .orderByAsc(DocApiResponseCode::getCode));
    }
}
