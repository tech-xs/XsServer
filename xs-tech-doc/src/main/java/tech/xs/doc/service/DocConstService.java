package tech.xs.doc.service;


import tech.xs.doc.domain.entity.DocConst;

import java.util.List;

/**
 * 常量文档
 *
 * @author 沈家文
 * @date 2021-58-17 15:58
 */
public interface DocConstService extends BaseDocService<DocConst> {

    /**
     * 查询常量详情信息
     *
     * @param constList 常量列表
     * @return 返回补充完成的常量信息
     */
    List<DocConst> listDetails(List<DocConst> constList);

}
