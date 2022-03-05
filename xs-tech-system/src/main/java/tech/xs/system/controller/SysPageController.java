package tech.xs.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.domain.model.PageResult;
import tech.xs.system.domain.bo.page.AddPageBo;
import tech.xs.system.domain.bo.page.DeletePageDetailsBo;
import tech.xs.system.domain.bo.page.ModifyPageBo;
import tech.xs.system.domain.bo.page.PageListPageBo;
import tech.xs.system.domain.entity.SysMenu;
import tech.xs.system.domain.entity.SysPage;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;


/**
 * 页面Controller
 *
 * @author 沈家文
 * @date 2020/11/26 11:28
 */
@Validated
@RestController
@RequestMapping("/sys/page")
public class SysPageController extends BaseSysController {

    /**
     * 新增页面
     *
     * @return 添加成功 返回页面信息
     */
    @PutMapping("/add")
    public ApiResult<SysPage> add(@RequestBody @Valid AddPageBo bo) {
        if (sysPageService.exist(Wrappers.<SysPage>lambdaQuery().eq(SysPage::getUri, bo.getUri()))) {
            return ApiResult.error(1000, "URL已经存在");
        }
        if (bo.getFatherId() != null && !sysPageService.existById(bo.getFatherId())) {
            return ApiResult.error(1001, "父页面不存在");
        }
        SysPage page = new SysPage();
        page.setName(bo.getName());
        page.setUri(bo.getUri());
        page.setFatherId(bo.getFatherId());
        page.setRemark(bo.getRemark());
        sysPageService.add(page);
        sysRoleApiService.clearCacheRoleUri();
        return ApiResult.success(page);
    }

    /**
     * 根据ID删除页面
     *
     * @return
     */
    @PostMapping("/delete/details")
    public ApiResult deleteDetails(@RequestBody @Valid DeletePageDetailsBo bo) {
        List<Long> idList = bo.getIdList();
        for (Long id : idList) {
            long count = sysMenuService.count(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getPageId, id));
            if (count > 0) {
                return ApiResult.error(1000, "页面已经绑定菜单", id);
            }
        }
        sysPageService.deleteDetails(bo);
        return ApiResult.success();
    }

    /**
     * 根据ID修改页面
     */
    @PostMapping("/modify/id")
    public ApiResult modifyById(@RequestBody @Valid ModifyPageBo bo) {
        if (bo.getId().equals(bo.getFatherId())) {
            return ApiResult.error(1000, "父页面不能是自身");
        }
        SysPage page = sysPageService.getById(bo.getId());
        if (page == null) {
            return ApiResult.error(1001, "页面不存在");
        }
        if (bo.getFatherId() != null) {
            SysPage father = sysPageService.getOne(Wrappers.<SysPage>lambdaUpdate()
                    .eq(SysPage::getId, bo.getFatherId()));
            if (father == null) {
                return ApiResult.error(1002, "父页面不存在");
            }
            while (father != null) {
                if (father.getFatherId() == null) {
                    break;
                }
                if (bo.getId().equals(father.getFatherId())) {
                    return ApiResult.error(1003, "页面存在递归");
                }
                father = sysPageService.getOne(Wrappers.<SysPage>lambdaUpdate()
                        .eq(SysPage::getId, father.getFatherId()));
            }
        }
        if (StringUtils.isNotBlank(bo.getUri()) && sysPageService.exist(Wrappers.<SysPage>lambdaQuery().eq(SysPage::getUri, bo.getUri()).ne(BaseEntity::getId, bo.getId()))) {
            return ApiResult.error(1004, "页面路径已经存在");
        }
        sysPageService.updateById(bo);
        return ApiResult.success();
    }

    @GetMapping("/list/all")
    public ApiResult listAll() {
        return PageResult.success(sysPageService.list(Wrappers.<SysPage>lambdaQuery().orderByAsc(SysPage::getUri)));
    }

    @GetMapping("/list/all/root")
    public ApiResult listAllRoot() {
        return PageResult.success(sysPageService.list(Wrappers.<SysPage>lambdaQuery()
                .isNull(SysPage::getFatherId)
                .orderByAsc(SysPage::getUri)));
    }

    @GetMapping("/list/page")
    public ApiResult listPage(@Valid PageListPageBo bo) {
        return sysPageService.listPage(bo);
    }

    @GetMapping("/id")
    public ApiResult getById(@NotNull Long id) {
        return ApiResult.success(sysPageService.getById(id));
    }

    @GetMapping("/list/ids")
    public ApiResult listByIds(@RequestParam("ids") @NotEmpty List<Long> ids) {
        return ApiResult.success(sysPageService.listByIds(ids));
    }

}
