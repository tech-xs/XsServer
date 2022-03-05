package tech.xs.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.system.domain.bo.role.menu.page.permission.RoleMenuPagePermissionListAllBo;
import tech.xs.system.domain.entity.SysRoleMenuPagePermission;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/sys/role/menu/page/permission")
public class SysRoleMenuPagePermissionController extends BaseSysController {

    @GetMapping("/list/all")
    public ApiResult<List<SysRoleMenuPagePermission>> list(@Valid RoleMenuPagePermissionListAllBo bo) {
        return ApiResult.success(sysRoleMenuPagePermissionService.list(Wrappers.<SysRoleMenuPagePermission>lambdaQuery()
                .eq(bo.getRoleId() != null, SysRoleMenuPagePermission::getRoleId, bo.getRoleId())));
    }


}
