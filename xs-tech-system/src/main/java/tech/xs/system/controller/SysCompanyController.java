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
import tech.xs.system.domain.entity.SysCompany;
import tech.xs.system.domain.entity.SysUser;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.List;

/**
 * 公司Controller
 *
 * @author 沈家文
 * @date 2021/6/10 16:45
 */
@Validated
@RestController
@RequestMapping("/sys/company")
public class SysCompanyController extends BaseSysController {

    @PutMapping("/add")
    public ApiResult<SysCompany> add(
            @NotBlank @Length(max = SysParamCheckConstant.SysCompany.SHORT_NAME_MAX_LENGTH) String shortName,
            @NotBlank @Length(max = SysParamCheckConstant.SysCompany.FULL_NAME_MAX_LENGTH) String fullName,
            String remark
    ) {
        SysCompany company = new SysCompany();
        company.setShortName(shortName);
        company.setFullName(fullName);
        company.setRemark(remark);
        sysCompanyService.add(company);
        return ApiResult.success(company);
    }

    @PostMapping("/delete/id")
    public ApiResult<Object> deleteById(@NotNull Long id) {
        long count = sysUserService.count(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getCompanyId, id));
        if (count > 0) {
            return ApiResult.error(1000, "公司已经被用户使用", id);
        }
        sysCompanyService.deleteById(id);
        return ApiResult.success();
    }

    @PostMapping("/delete/list/id")
    public ApiResult<Object> deleteByIdList(@NotEmpty List<Long> idList) {
        for (Long id : idList) {
            long count = sysUserService.count(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getCompanyId, id));
            if (count > 0) {
                return ApiResult.error(1000, "公司已经被用户使用", id);
            }
        }
        sysCompanyService.deleteByIdList(idList);
        return ApiResult.success();
    }

    @PostMapping("/modify/id")
    public ApiResult<Object> modifyById(
            @NotNull Long id,
            @NotBlank @Length(max = SysParamCheckConstant.SysCompany.SHORT_NAME_MAX_LENGTH) String shortName,
            @NotBlank @Length(max = SysParamCheckConstant.SysCompany.FULL_NAME_MAX_LENGTH) String fullName,
            String remark
    ) {
        SysCompany company = sysCompanyService.getById(id);
        if (company == null) {
            return ApiResult.error(1000, "公司不存在");
        }
        company = new SysCompany();
        company.setId(id);
        company.setShortName(shortName);
        company.setFullName(fullName);
        company.setRemark(remark);
        sysCompanyService.updateById(company);
        return ApiResult.success();
    }

    @GetMapping("/list")
    public ApiResult<List<SysCompany>> list() {
        return PageResult.success(sysCompanyService.list(Wrappers.<SysCompany>lambdaQuery().orderByDesc(BaseEntity::getCreateTime)));
    }

    @GetMapping("/list/page")
    public ApiResult<List<SysCompany>> listPage(
            @NotNull @Min(ParamCheckConstant.PAGE_MIN_INDEX) Long pageIndex,
            @NotNull @Min(ParamCheckConstant.PAGE_MIN_SIZE) @Max(ParamCheckConstant.PAGE_MAX_SIZE) Long pageSize,
            @Length(max = SysParamCheckConstant.SysCompany.SHORT_NAME_MAX_LENGTH) String shortName,
            @Length(max = SysParamCheckConstant.SysCompany.FULL_NAME_MAX_LENGTH) String fullName) {
        Page<SysCompany> page = new Page<>(pageIndex, pageSize);
        LambdaQueryWrapper<SysCompany> wrapper = Wrappers.<SysCompany>lambdaQuery()
                .orderByDesc(BaseEntity::getCreateTime);
        wrapper.like(StringUtils.isNotBlank(shortName), SysCompany::getShortName, shortName);
        wrapper.like(StringUtils.isNotBlank(fullName), SysCompany::getFullName, fullName);
        sysCompanyService.page(page, wrapper);
        return PageResult.success(page);
    }

    @GetMapping("/id")
    public ApiResult<SysCompany> getById(@NotNull Long id) {
        return ApiResult.success(sysCompanyService.getById(id));
    }

}
