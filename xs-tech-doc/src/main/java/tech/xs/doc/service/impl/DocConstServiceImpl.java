package tech.xs.doc.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import org.springframework.stereotype.Service;
import tech.xs.common.collections.CollectionUtils;
import tech.xs.doc.dao.DocConstDao;
import tech.xs.doc.domain.entity.DocConst;
import tech.xs.doc.domain.entity.DocConstValue;
import tech.xs.doc.service.DocConstService;

import java.util.List;

/**
 * 常量文档服务实现类
 *
 * @author 沈家文
 * @date 2021-59-17 15:59
 */
@Service
public class DocConstServiceImpl extends BaseDocServiceImpl<DocConstDao, DocConst> implements DocConstService {

    @Override
    public List<DocConst> listDetails(List<DocConst> constList) {
        if (CollectionUtils.isEmpty(constList)) {
            return constList;
        }
        for (DocConst docConst : constList) {
            docConst.setValueList(docConstValueService.list(Wrappers.<DocConstValue>lambdaQuery()
                    .eq(DocConstValue::getConstId, docConst.getId())));
        }
        return constList;
    }

}
