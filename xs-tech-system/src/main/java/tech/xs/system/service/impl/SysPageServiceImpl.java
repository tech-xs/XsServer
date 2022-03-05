package tech.xs.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.xs.common.collections.CollectionUtils;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.domain.model.PageResult;
import tech.xs.system.domain.bo.page.DeletePageDetailsBo;
import tech.xs.system.domain.bo.page.ModifyPageBo;
import tech.xs.system.domain.bo.page.PageListPageBo;
import tech.xs.system.domain.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.xs.framework.base.BaseEntity;
import tech.xs.system.dao.SysPageDao;
import tech.xs.system.service.SysPageService;

import javax.annotation.Resource;
import java.util.*;

/**
 * 页面Service实现类
 *
 * @author 沈家文
 * @date 2020/9/2
 */
@Service
public class SysPageServiceImpl extends BaseSysServiceImpl<SysPageDao, SysPage> implements SysPageService {

    @Resource
    private SysPageDao sysPageDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDetails(DeletePageDetailsBo bo) {
        sysPagePermissionService.delete(Wrappers.<SysPagePermission>lambdaQuery().in(SysPagePermission::getPageId, bo.getIdList()));
        sysPageApiService.delete(Wrappers.<SysPageApi>lambdaQuery().in(SysPageApi::getPageId, bo.getIdList()));
        sysPageDao.delete(Wrappers.<SysPage>lambdaQuery().in(BaseEntity::getId, bo.getIdList()));
        sysRoleMenuPagePermissionService.delete(Wrappers.<SysRoleMenuPagePermission>lambdaQuery().in(SysRoleMenuPagePermission::getPageId, bo.getIdList()));
        sysRoleMenuPageService.delete(Wrappers.<SysRoleMenuPage>lambdaQuery().in(SysRoleMenuPage::getPageId, bo.getIdList()));
        sysPageDao.update(new SysPage(), Wrappers.<SysPage>lambdaUpdate()
                .set(SysPage::getFatherId, null)
                .in(SysPage::getFatherId, bo.getIdList()));
        sysRoleApiService.clearCacheRoleUri();
    }

    @Override
    public ApiResult listPage(PageListPageBo bo) {
        Page<SysPage> page = bo.getPage();
        sysPageService.page(page, Wrappers.<SysPage>lambdaQuery()
                .like(StringUtils.isNotBlank(bo.getLikeName()), SysPage::getName, bo.getLikeName())
                .like(StringUtils.isNotBlank(bo.getLikeUri()), SysPage::getUri, bo.getLikeUri())
                .orderByAsc(SysPage::getUri));
        return PageResult.success(page);
    }

    @Override
    @Transactional
    public void updateById(ModifyPageBo bo) {
        SysPage dbPage = sysPageDao.selectById(bo.getId());
        // 情况1: 之前有父菜单, 修改之后没有父菜单
        // 情况2: 之前父菜单和现在父菜单不是同一个
        // 以上两种情况都需要删除该页面及其子页面对应所有的权限
        if ((dbPage.getFatherId() != null && bo.getFatherId() == null)
                || (dbPage.getFatherId() != null && bo.getFatherId() != null && !dbPage.getFatherId().equals(bo.getFatherId()))) {
            List<SysPage> pageList = listDepthChild(bo.getId());
            HashSet<Long> ids = new HashSet<>();
            for (SysPage item : pageList) {
                ids.add(item.getId());
            }
            ids.add(bo.getId());
            sysRoleMenuPageService.delete(Wrappers.<SysRoleMenuPage>lambdaQuery().in(SysRoleMenuPage::getPageId, ids));
            sysRoleMenuPagePermissionService.delete(Wrappers.<SysRoleMenuPagePermission>lambdaQuery().in(SysRoleMenuPagePermission::getPageId, ids));
        } else if (dbPage.getFatherId() == null && bo.getFatherId() != null) {
            // 之前没有父菜单,现在有父菜单了
            sysMenuService.delete(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getPageId, bo.getId()));
            sysRoleMenuPageService.delete(Wrappers.<SysRoleMenuPage>lambdaQuery().eq(SysRoleMenuPage::getPageId, bo.getId()));
            sysRoleMenuPagePermissionService.delete(Wrappers.<SysRoleMenuPagePermission>lambdaQuery().eq(SysRoleMenuPagePermission::getPageId, bo.getId()));
        }
        sysPageService.update(new SysPage(), Wrappers.<SysPage>lambdaUpdate()
                .eq(BaseEntity::getId, bo.getId())
                .set(SysPage::getUri, bo.getUri())
                .set(SysPage::getName, bo.getName())
                .set(SysPage::getRemark, bo.getRemark())
                .set(SysPage::getFatherId, bo.getFatherId()));
        sysRoleApiService.clearCacheRoleUri();
    }

    @Override
    public List<SysPage> listDepthChild(Collection<Long> pageIds) {
        List<SysPage> pageList = sysPageDao.selectList(Wrappers.<SysPage>lambdaQuery()
                .in(SysPage::getFatherId, pageIds));
        if (CollectionUtils.isEmpty(pageList)) {
            return pageList;
        }
        Set<Long> idSet = new HashSet<>();
        for (SysPage page : pageList) {
            idSet.add(page.getId());
        }
        pageList.addAll(listDepthChild(idSet));
        return pageList;
    }

    @Override
    public List<SysPage> listDepthChild(Long pageId) {
        ArrayList<Long> pageIdList = new ArrayList<>();
        pageIdList.add(pageId);
        return listDepthChild(pageIdList);
    }

    @Override
    public List<SysPage> tree() {
        List<SysPage> tree = new ArrayList<>();
        List<SysPage> pageList = sysPageDao.selectList(Wrappers.<SysPage>lambdaQuery());
        for (SysPage page : pageList) {
            if (page.getFatherId() == null) {
                tree.add(page);
            }
            List<SysPage> childList = new ArrayList<>();
            for (SysPage child : pageList) {
                if (child.getFatherId() == null) {
                    continue;
                }
                if (child.getFatherId().equals(page.getId())) {
                    childList.add(child);
                }
            }
            if (CollectionUtils.isNotEmpty(childList)) {
                page.setChildList(childList);
            }
        }
        return tree;
    }


}
