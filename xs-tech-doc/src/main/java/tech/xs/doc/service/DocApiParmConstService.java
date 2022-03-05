package tech.xs.doc.service;


import tech.xs.doc.domain.entity.DocApiParmConst;
import tech.xs.doc.domain.entity.DocConst;

import java.util.List;

/**
 * 参数常量关联Service
 *
 * @author 沈家文
 * @date 2021-35-17 17:35
 */
public interface DocApiParmConstService extends BaseDocService<DocApiParmConst> {


    /**
     * 根据参数ID查询管理常量
     *
     * @param id 参数常量
     * @return 返回常量列表
     */
    List<DocConst> selectConstByParmId(Long id);

}
