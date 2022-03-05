package tech.xs.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.domain.model.PageResult;
import tech.xs.system.domain.bo.page.api.AddPageApiBo;
import tech.xs.system.domain.bo.page.api.DeletePageApiBo;
import tech.xs.system.domain.bo.page.api.ModifyPageApiBo;
import tech.xs.system.domain.bo.page.api.PageListApiBo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.xs.system.domain.entity.SysPageApi;
import tech.xs.system.domain.entity.SysPagePermission;

import javax.validation.Valid;

/**
 * 页面接口Controller
 *
 * @author 沈家文
 * @date 2021/5/19 9:56
 */
@Validated
@RestController
@RequestMapping("/sys/page/api")
public class SysPageApiController extends BaseSysController {

    @PutMapping("/add")
    public ApiResult add(@RequestBody @Valid AddPageApiBo bo) {
        if (!sysPageService.existById(bo.getPageId())) {
            return ApiResult.error(1000, "页面不存在");
        }
        if (!sysApiService.existById(bo.getApiId())) {
            return ApiResult.error(1001, "接口不存在");
        }
        if (bo.getPermissionId() != null && !sysPagePermissionService.existById(bo.getPermissionId())) {
            return ApiResult.error(1002, "权限不存在");
        }
        SysPageApi pageApi = new SysPageApi();
        pageApi.setPageId(bo.getPageId());
        pageApi.setApiId(bo.getApiId());
        pageApi.setPermissionId(bo.getPermissionId());
        pageApi.setRemark(bo.getRemark());
        sysPageApiService.add(pageApi);
        sysRoleApiService.clearCacheRoleUri();
        return ApiResult.success(pageApi);
    }

    @PostMapping("/delete/details")
    public ApiResult deleteDetails(@RequestBody @Valid DeletePageApiBo bo) {
        sysPageApiService.deleteByIdList(bo.getIdList());
        sysRoleApiService.clearCacheRoleUri();
        return ApiResult.success();
    }

    @PostMapping("/modify/id")
    public ApiResult modifyById(@RequestBody @Valid ModifyPageApiBo bo) {
        if (!sysPageApiService.existById(bo.getId())) {
            return ApiResult.error(1000, "页面接口不存在");
        }
        if (!sysPageService.existById(bo.getPageId())) {
            return ApiResult.error(1001, "页面不存在");
        }
        if (bo.getApiId() != null && !sysApiService.existById(bo.getApiId())) {
            return ApiResult.error(1002, "资源不存在");
        }
        if (bo.getPermissionId() != null && !sysPagePermissionService.exist(Wrappers.<SysPagePermission>lambdaQuery()
                .eq(SysPagePermission::getPageId, bo.getPageId())
                .eq(BaseEntity::getId, bo.getPermissionId()))) {
            return ApiResult.error(1003, "页面权限不存在");
        }
        if (bo.getPermissionId() == null) {
            if (sysPageApiService.exist(Wrappers.<SysPageApi>lambdaQuery()
                    .ne(BaseEntity::getId, bo.getId())
                    .isNull(SysPageApi::getPermissionId)
                    .eq(SysPageApi::getApiId, bo.getApiId()))) {
                return ApiResult.error(1004, "页面接口与权限绑定已经存在");
            }
        } else {
            if (sysPageApiService.exist(Wrappers.<SysPageApi>lambdaQuery()
                    .ne(BaseEntity::getId, bo.getId())
                    .eq(SysPageApi::getPermissionId, bo.getPermissionId())
                    .eq(SysPageApi::getApiId, bo.getApiId()))) {
                return ApiResult.error(1005, "页面接口已经存在");
            }
        }

        SysPageApi savePageResource = new SysPageApi();
        savePageResource.setId(bo.getId());
        savePageResource.setPageId(bo.getPageId());
        savePageResource.setApiId(bo.getApiId());
        savePageResource.setPermissionId(bo.getPermissionId());
        savePageResource.setRemark(bo.getRemark());
        sysPageApiService.updateByIdAndSetPermissionId(savePageResource);
        return ApiResult.success();
    }

    @GetMapping("/list/page")
    public ApiResult listPage(@Valid PageListApiBo bo) {
        Page<SysPageApi> page = bo.getPage();
        sysPageApiService.page(page, Wrappers.<SysPageApi>lambdaQuery()
                .eq(bo.getPageId() != null, SysPageApi::getPageId, bo.getPageId())
                .eq(bo.getApiId() != null, SysPageApi::getApiId, bo.getApiId())
                .eq(bo.getPermissionId() != null, SysPageApi::getPermissionId, bo.getPermissionId())
                .orderByDesc(BaseEntity::getCreateTime));
        return PageResult.success(page);
    }

}
