package tech.xs.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.constant.ParamCheckConstant;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.domain.model.PageResult;
import tech.xs.system.domain.entity.SysOrganization;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 沈家文
 * @date 2021/01/26
 */
@Validated
@RestController
@RequestMapping("/sys/organization")
public class SysOrganizationController extends BaseSysController {

    @PutMapping("/add")
    public ApiResult add(
            String organizationName,
            Long fatherId
    ) {
        SysOrganization sysOrganization = new SysOrganization();
        sysOrganization.setOrganizationName(organizationName);
        sysOrganization.setFatherId(fatherId);
        sysOrganizationService.add(sysOrganization);
        return ApiResult.success();
    }

    @PostMapping("/delete/list/id")
    public ApiResult deleteByIdList(@NotEmpty List<Long> idList) {
        sysOrganizationService.delete(Wrappers.<SysOrganization>lambdaQuery().in(BaseEntity::getId, idList));
        return ApiResult.success();
    }

    @PostMapping("/modify/id")
    public ApiResult modifyById(
            @NotNull Long id,
            String organizationName,
            Long fatherId
    ) {
        if (sysOrganizationService.getById(id) == null) {
            return ApiResult.error(1000, "数据不存在");
        }
        SysOrganization sysOrganization = new SysOrganization();
        sysOrganization.setId(id);
        sysOrganization.setOrganizationName(organizationName);
        sysOrganization.setFatherId(fatherId);
        sysOrganizationService.updateById(sysOrganization);
        return ApiResult.success();
    }

    @GetMapping("/list/page")
    public ApiResult listPage(
            @NotNull @Min(ParamCheckConstant.PAGE_MIN_INDEX) Long pageIndex,
            @NotNull @Min(ParamCheckConstant.PAGE_MIN_SIZE) @Max(ParamCheckConstant.PAGE_MAX_SIZE) Long pageSize,
            String organizationName,
            Long fatherId
    ) {
        Page<SysOrganization> page = new Page<>(pageIndex, pageSize);
        LambdaQueryWrapper<SysOrganization> wrapper = Wrappers.<SysOrganization>lambdaQuery();
        if (StringUtils.isNotBlank(organizationName)) {
            wrapper = wrapper.eq(SysOrganization::getOrganizationName, organizationName);
        }
        if (fatherId != null) {
            wrapper = wrapper.eq(SysOrganization::getFatherId, fatherId);
        }
        wrapper = wrapper.orderByAsc(BaseEntity::getCreateTime);
        page = sysOrganizationService.page(page, wrapper);
        return PageResult.success(page);
    }

}
