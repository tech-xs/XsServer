package tech.xs.doc.service;

import tech.xs.doc.domain.entity.DocApiResponseCode;

import java.util.List;

/**
 * 文档接口项目码Service
 *
 * @author 沈家文
 * @date 2021/7/8 17:16
 */
public interface DocApiResponseCodeService extends BaseDocService<DocApiResponseCode> {

    /**
     * 获取全局通用响应code
     *
     * @return
     */
    List<DocApiResponseCode> listGlobalResponseCode();

}
