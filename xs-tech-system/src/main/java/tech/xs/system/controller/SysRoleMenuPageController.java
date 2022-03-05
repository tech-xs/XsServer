package tech.xs.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.system.domain.bo.role.menu.page.RoleMenuPageListAllBo;
import tech.xs.system.domain.entity.SysRoleMenuPage;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/sys/role/menu/page")
public class SysRoleMenuPageController extends BaseSysController {

    @GetMapping("/list/all")
    public ApiResult<List<SysRoleMenuPage>> list(@Valid RoleMenuPageListAllBo bo) {
        return ApiResult.success(sysRoleMenuPageService.list(Wrappers.<SysRoleMenuPage>lambdaQuery()
                .eq(bo.getRoleId() != null, SysRoleMenuPage::getRoleId, bo.getRoleId())));
    }


}
