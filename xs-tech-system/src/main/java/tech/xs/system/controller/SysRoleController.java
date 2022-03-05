package tech.xs.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.enums.BooleanEnum;
import tech.xs.system.constant.RoleMenuConstant;
import tech.xs.system.constant.RolePagePermissionConstant;
import tech.xs.system.constant.SysRoleConstant;
import tech.xs.system.domain.bo.role.AddRoleBo;
import tech.xs.system.domain.bo.role.DeleteRoleBo;
import tech.xs.system.domain.bo.role.ModifyRoleBo;
import tech.xs.system.domain.bo.role.RoleListPageBo;
import tech.xs.system.domain.entity.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.domain.model.PageResult;
import tech.xs.system.constant.SysParamCheckConstant;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Iterator;
import java.util.List;

/**
 * 角色Controller
 *
 * @author 沈家文
 * @date 2020/11/12 17:23
 */
@Validated
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseSysController {

    @PutMapping("/add")
    public ApiResult<SysRole> add(@RequestBody @Valid AddRoleBo bo) {
        if (sysRoleService.exist(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getCode, bo.getCode()))) {
            return ApiResult.error(1000, "编号已存在");
        }
        if (sysRoleService.exist(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getName, bo.getName()))) {
            return ApiResult.error(1001, "名称已存在");
        }
        SysRole role = new SysRole();
        role.setCode(bo.getCode());
        role.setName(bo.getName());
        role.setRemark(bo.getRemark());
        role.setStatus(bo.getStatus());
        sysRoleService.add(role);
        return ApiResult.success(role);
    }

    @PostMapping("/delete/details")
    public ApiResult<Object> deleteDetails(@RequestBody @Valid DeleteRoleBo bo) {
        for (Long id : bo.getIdList()) {
            if (sysRoleService.isUseRole(id)) {
                return ApiResult.error(1000, "角色已经被使用");
            }
        }
        sysRoleService.deleteDetails(bo);
        return ApiResult.success();
    }


    @PostMapping("/modify/id")
    public ApiResult<Object> modifyById(@RequestBody @Valid ModifyRoleBo bo) {
        SysRole dbRole = sysRoleService.getById(bo.getId());
        if (dbRole == null) {
            return ApiResult.error(1000, "角色不存在");
        }
        if (SysRoleConstant.Code.SUPER_ADMIN.equals(dbRole.getCode()) && !SysRoleConstant.Code.SUPER_ADMIN.equals(bo.getCode())) {
            return ApiResult.error(1001, "此角色编码禁止修改");
        }
        if (sysRoleService.exist(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getCode, bo.getCode()).ne(BaseEntity::getId, bo.getId()))) {
            return ApiResult.error(1002, "编号已存在");
        }
        if (sysRoleService.exist(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getName, bo.getName()).ne(BaseEntity::getId, bo.getId()))) {
            return ApiResult.error(1003, "名称已存在");
        }
        SysRole role = new SysRole();
        role.setId(bo.getId());
        role.setCode(bo.getCode());
        role.setName(bo.getName());
        role.setRemark(bo.getRemark());
        role.setStatus(bo.getStatus());
        sysRoleService.updateById(role);
        return ApiResult.success();
    }

    @GetMapping("/list/page")
    public ApiResult<List<SysRole>> listPage(@Valid RoleListPageBo bo) {
        Page<SysRole> page = bo.getPage();
        page = sysRoleService.page(page, Wrappers.<SysRole>lambdaQuery()
                .like((StringUtils.isNotBlank(bo.getLikeCode())), SysRole::getCode, bo.getLikeCode())
                .like((StringUtils.isNotBlank(bo.getLikeName())), SysRole::getName, bo.getLikeName())
                .orderByAsc(BaseEntity::getCreateTime));
        return PageResult.success(page);
    }

    @GetMapping("/list/all")
    public ApiResult<List<SysRole>> list() {
        return PageResult.success(sysRoleService.list(Wrappers.<SysRole>lambdaQuery().orderByDesc(BaseEntity::getCreateTime)));
    }

    @GetMapping("/id")
    public ApiResult<SysRole> getById(@NotNull Long id) {
        return ApiResult.success(sysRoleService.getById(id));
    }

}
