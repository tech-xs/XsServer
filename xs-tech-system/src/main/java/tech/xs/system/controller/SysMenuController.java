package tech.xs.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.domain.model.PageResult;
import tech.xs.system.constant.SysParamCheckConstant;
import tech.xs.system.domain.bo.menu.AddMenuBo;
import tech.xs.system.domain.bo.menu.DeleteMenuBo;
import tech.xs.system.domain.bo.menu.ModifyMenuBo;
import tech.xs.system.domain.entity.SysMenu;
import tech.xs.system.domain.entity.SysPage;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 菜单Controller
 *
 * @author 沈家文
 * @date 2020/11/18 17:52
 */
@Validated
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseSysController {

    @PutMapping("/add")
    public ApiResult add(@RequestBody @Valid AddMenuBo bo) {
        if (bo.getFatherId() != null && !sysMenuService.exist(Wrappers.<SysMenu>lambdaQuery().eq(BaseEntity::getId, bo.getFatherId()))) {
            return ApiResult.error(1001, "父菜单不存在");
        }
        if (sysMenuService.exist(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getName, bo.getName())
                .eq(bo.getFatherId() != null, SysMenu::getFatherId, bo.getFatherId())
                .isNull(bo.getFatherId() == null, SysMenu::getFatherId))) {
            return ApiResult.error(1002, "名称已存在");
        }
        if (sysMenuService.exist(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getSort, bo.getSort())
                .eq(bo.getFatherId() != null, SysMenu::getFatherId, bo.getFatherId())
                .isNull(bo.getFatherId() == null, SysMenu::getFatherId))) {
            return ApiResult.error(1003, "排序值已存在");
        }
        if (bo.getPageId() != null && !sysPageService.existById(bo.getPageId())) {
            return ApiResult.error(1004, "页面不存在");
        }
        if (sysMenuService.exist(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getCode, bo.getCode())
                .eq(bo.getFatherId() != null, SysMenu::getFatherId, bo.getFatherId())
                .isNull(bo.getFatherId() == null, SysMenu::getFatherId))) {
            return ApiResult.error(1005, "编码已存在");
        }
        SysMenu menu = new SysMenu();
        menu.setName(bo.getName());
        menu.setSort(bo.getSort());
        menu.setFatherId(bo.getFatherId());
        menu.setIcon(bo.getIcon());
        menu.setCode(bo.getCode());
        menu.setPageId(bo.getPageId());
        sysMenuService.add(menu);
        return ApiResult.success(menu);
    }

    @PostMapping("/delete/details")
    public ApiResult deleteDetails(@RequestBody @Valid DeleteMenuBo bo) {
        if (sysMenuService.exist(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getFatherId, bo.getId()))) {
            return ApiResult.error(1000, "菜单具有子菜单");
        }
        sysMenuService.deleteDetails(bo);
        return ApiResult.success();
    }

    @PostMapping("/modify/id")
    public ApiResult modifyById(@RequestBody @Valid ModifyMenuBo bo) {
        if (!sysMenuService.existById(bo.getId())) {
            return ApiResult.error(1000, "菜单不存在");
        }
        if (bo.getPageId() != null) {
            if (!sysPageService.existById(bo.getPageId())) {
                return ApiResult.error(1006, "页面不存在");
            }
        }
        if (bo.getId().equals(bo.getFatherId())) {
            return ApiResult.error(1001, "父菜单不能是自身");
        }
        if (bo.getFatherId() != null && !sysMenuService.exist(Wrappers.<SysMenu>lambdaQuery().eq(BaseEntity::getId, bo.getFatherId()))) {
            return ApiResult.error(1002, "父菜单不存在");
        }
        if (sysMenuService.isChild(bo.getId(), bo.getFatherId())) {
            return ApiResult.error(1003, "父节点不能是自身的子节点");
        }
        if (sysMenuService.exist(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getName, bo.getName())
                .isNull(bo.getFatherId() == null, SysMenu::getFatherId)
                .eq(bo.getFatherId() != null, SysMenu::getFatherId, bo.getFatherId())
                .ne(BaseEntity::getId, bo.getId()))) {
            return ApiResult.error(1004, "名称已存在");
        }
        if (sysMenuService.exist(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getSort, bo.getSort())
                .isNull(bo.getFatherId() == null, SysMenu::getFatherId)
                .eq(bo.getFatherId() != null, SysMenu::getFatherId, bo.getFatherId())
                .ne(BaseEntity::getId, bo.getId()))) {
            return ApiResult.error(1005, "排序值已存在");
        }
        if (sysMenuService.exist(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getCode, bo.getCode())
                .isNull(bo.getFatherId() == null, SysMenu::getFatherId)
                .eq(bo.getFatherId() != null, SysMenu::getFatherId, bo.getFatherId())
                .ne(BaseEntity::getId, bo.getId()))) {
            return ApiResult.error(1006, "编码已存在");
        }
        sysMenuService.updateById(bo);
        return ApiResult.success();
    }

    @GetMapping("/list/all")
    public ApiResult<List<SysMenu>> listAll() {
        return PageResult.success(sysMenuService.list(Wrappers.<SysMenu>lambdaQuery().orderByAsc(SysMenu::getSort)));
    }


}
