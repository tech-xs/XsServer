package tech.xs.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.constant.ParamCheckConstant;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.domain.model.PageResult;
import tech.xs.system.constant.SysParamCheckConstant;
import tech.xs.system.domain.bo.page.permission.*;
import tech.xs.system.domain.entity.SysPage;
import tech.xs.system.domain.entity.SysPagePermission;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * 页面中的权限Controller
 *
 * @author 沈家文
 * @date 2021/5/10 19:50
 */
@Validated
@RestController
@RequestMapping("/sys/page/permission")
public class SysPagePermissionController extends BaseSysController {

    @PutMapping("/add")
    public ApiResult add(@RequestBody @Valid AddPagePermissionBo bo) {
        SysPage page = sysPageService.getById(bo.getPageId());
        if (page == null) {
            return ApiResult.error(1000, "页面不存在");
        }
        if (sysPagePermissionService.exist(Wrappers.<SysPagePermission>lambdaQuery()
                .eq(SysPagePermission::getPageId, bo.getPageId())
                .eq(SysPagePermission::getPermissionCode, bo.getPermissionCode()))) {
            return ApiResult.error(1001, "权限编码已存在");
        }
        if (sysPagePermissionService.exist(Wrappers.<SysPagePermission>lambdaQuery()
                .eq(SysPagePermission::getPageId, bo.getPageId())
                .eq(SysPagePermission::getPermissionName, bo.getPermissionName()))) {
            return ApiResult.error(1002, "权限名称已存在");
        }
        SysPagePermission permission = new SysPagePermission();
        permission.setPageId(bo.getPageId());
        permission.setPermissionCode(bo.getPermissionCode());
        permission.setPermissionName(bo.getPermissionName());
        permission.setRemark(bo.getRemark());
        sysPagePermissionService.add(permission);
        return ApiResult.success(permission);
    }

    @PostMapping("/delete/details")
    public ApiResult deleteDetails(@RequestBody @Valid DeletePagePermissionBo bo) {
        sysPagePermissionService.deleteDetails(bo);
        return ApiResult.success();
    }

    @PostMapping("/modify/id")
    public ApiResult modifyById(@RequestBody @Valid ModifyPagePermissionBo bo) {
        SysPagePermission permission = sysPagePermissionService.getById(bo.getId());
        if (permission == null) {
            return ApiResult.error(1000, "权限不存在");
        }
        if (StringUtils.isNotBlank(bo.getPermissionCode())) {
            if (sysPagePermissionService.exist(Wrappers.<SysPagePermission>lambdaQuery()
                    .eq(SysPagePermission::getPermissionCode, bo.getPermissionCode())
                    .ne(BaseEntity::getId, bo.getId()))) {
                return ApiResult.error(1001, "权限编码已经存在");
            }
        }
        if (StringUtils.isNotBlank(bo.getPermissionName())) {
            if (sysPagePermissionService.exist(Wrappers.<SysPagePermission>lambdaQuery()
                    .eq(SysPagePermission::getPermissionName, bo.getPermissionName())
                    .ne(BaseEntity::getId, bo.getId()))) {
                return ApiResult.error(1002, "权限名称已经存在");
            }
        }
        SysPagePermission newPermission = new SysPagePermission();
        newPermission.setId(bo.getId());
        newPermission.setPermissionCode(bo.getPermissionCode());
        newPermission.setPermissionName(bo.getPermissionName());
        newPermission.setRemark(bo.getRemark());
        sysPagePermissionService.updateById(newPermission);
        return ApiResult.success();
    }

    @GetMapping("/list/all")
    public ApiResult listPage(@Valid ListAllPagePermissionBo bo) {
        return PageResult.success(sysPagePermissionService.list(Wrappers.<SysPagePermission>lambdaQuery()
                .eq(bo.getPageId() != null, SysPagePermission::getPageId, bo.getPageId())
                .orderByAsc(BaseEntity::getCreateTime)));
    }

    @GetMapping("/list/page")
    public ApiResult listPage(@Valid PagePermissionListPageBo bo) {
        Page<SysPagePermission> page = bo.getPage();
        page = sysPagePermissionService.page(page, Wrappers.<SysPagePermission>lambdaQuery()
                .eq(bo.getPageId() != null, SysPagePermission::getPageId, bo.getPageId())
                .like(StringUtils.isNotBlank(bo.getLikePermissionName()), SysPagePermission::getPermissionName, bo.getLikePermissionName())
                .like(StringUtils.isNotBlank(bo.getLikePermissionCode()), SysPagePermission::getPermissionCode, bo.getLikePermissionCode())
                .orderByAsc(BaseEntity::getCreateTime)
        );
        return PageResult.success(page);
    }

}
