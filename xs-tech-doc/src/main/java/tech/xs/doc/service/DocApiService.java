package tech.xs.doc.service;

import tech.xs.doc.domain.entity.DocApi;
import tech.xs.system.domain.entity.SysApi;

import java.io.IOException;
import java.util.List;

/**
 * 接口文档Service
 *
 * @author 沈家文
 * @date 2021/7/8 17:13
 */
public interface DocApiService extends BaseDocService<DocApi> {

    /**
     * 根据接口ID查询文档详情
     *
     * @param resourceId 根据接口id获取文档详情
     * @return 返回文档详情
     */
    DocApi getDetailsByResourceId(Long resourceId);

    /**
     * 保存接口文档
     *
     * @param doc 接口文档
     */
    void save(DocApi doc);

    /**
     * 根据文档ID删除详情
     *
     * @param docId 文档ID
     */
    void deleteDetails(Long docId);

    /**
     * 生成文档和对应的资源
     */
    void generate() throws IOException, ClassNotFoundException, IllegalAccessException;

    /**
     * 生成文档
     */
    void generateDoc() throws IOException, ClassNotFoundException, IllegalAccessException;

    /**
     * 生成资源列表
     */
    void generateApi();

    /**
     * 通过spring框架获取到当前代码的url映射列表
     * 获取当前系统的接口列表
     *
     * @return 返回获取到的资源列表
     */
    List<SysApi> getCurrDocApi();

}
