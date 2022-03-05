package tech.xs.system.service;

import tech.xs.framework.domain.model.ApiResult;
import tech.xs.system.domain.bo.page.DeletePageDetailsBo;
import tech.xs.system.domain.bo.page.ModifyPageBo;
import tech.xs.system.domain.bo.page.PageListPageBo;
import tech.xs.system.domain.entity.SysPage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 页面Service
 *
 * @author 沈家文
 * @date 2020/9/2
 */
public interface SysPageService extends BaseSysService<SysPage> {

    /**
     * 删除页面以及页面包含的内容
     *
     * @param bo
     */
    void deleteDetails(DeletePageDetailsBo bo);

    /**
     * 分页查询页面
     *
     * @param bo
     * @return
     */
    ApiResult listPage(PageListPageBo bo);

    /**
     * 根据ID修改页面
     *
     * @param bo
     */
    void updateById(ModifyPageBo bo);

    /**
     * 深度查询页面对应的子页面
     *
     * @param pageIdList
     * @return
     */
    List<SysPage> listDepthChild(Collection<Long> pageIdList);

    /**
     * 深度查询页面对应的子页面
     *
     * @param pageId
     * @return
     */
    List<SysPage> listDepthChild(Long pageId);

    /**
     * 查询页面数
     *
     * @return
     */
    List<SysPage> tree();

}
