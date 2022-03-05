package tech.xs.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.constant.ParamCheckConstant;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.domain.model.PageResult;
import tech.xs.framework.enums.HttpMethodEnum;
import tech.xs.system.constant.SysParamCheckConstant;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.xs.system.domain.entity.SysApi;

import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.List;

/**
 * URI资源Controller
 *
 * @author 沈家文
 * @date 2021/7/8 17:11
 */
@Validated
@RestController
@RequestMapping("/sys/api")
public class SysApiController extends BaseSysController {

    @PutMapping("/add")
    public ApiResult<SysApi> add(
            @NotBlank @Length(max = SysParamCheckConstant.SysApi.NAME_MAX_LENGTH) String name,
            @NotBlank @Length(max = SysParamCheckConstant.SysApi.URI_MAX_LENGTH) String uri,
            @NotNull HttpMethodEnum method,
            @Length(max = SysParamCheckConstant.SysApi.REMARK_MAX_LENGTH) String remark) {
        long count;
        if (method != null) {
            count = sysApiService.count(Wrappers.<SysApi>lambdaQuery().eq(SysApi::getUri, uri).eq(SysApi::getMethod, method));
        } else {
            count = sysApiService.count(Wrappers.<SysApi>lambdaQuery().eq(SysApi::getUri, uri).isNull(SysApi::getMethod));
        }
        if (count > 0) {
            return ApiResult.error(1000, "接口已存在");
        }

        SysApi uriResource = new SysApi();
        uriResource.setName(name);
        uriResource.setUri(uri);
        uriResource.setRemark(remark);
        uriResource.setMethod(method);
        sysApiService.add(uriResource);
        return ApiResult.success(uriResource);
    }

    @PostMapping("/delete/list/id")
    public ApiResult<Object> deleteByIdList(@NotEmpty List<Long> idList) {
        sysApiService.deleteByIdList(idList);
        sysRoleApiService.clearCacheRoleUri();
        return ApiResult.success();
    }

    @PostMapping("/modify/id")
    public ApiResult<Object> modifyById(
            @NotNull Long id,
            @NotBlank @Length(max = SysParamCheckConstant.SysApi.URI_MAX_LENGTH) String uri,
            HttpMethodEnum method,
            @NotBlank @Length(max = SysParamCheckConstant.SysApi.NAME_MAX_LENGTH) String name,
            @Length(max = SysParamCheckConstant.SysApi.REMARK_MAX_LENGTH) String remark) {
        SysApi resource = sysApiService.getById(id);
        if (resource == null) {
            return ApiResult.error(1000, "接口不存在");
        }
        long count;
        if (method != null) {
            count = sysApiService.count(Wrappers.<SysApi>lambdaQuery()
                    .eq(SysApi::getUri, uri)
                    .eq(SysApi::getMethod, method)
                    .ne(BaseEntity::getId, id));
        } else {
            count = sysApiService.count(Wrappers.<SysApi>lambdaQuery()
                    .eq(SysApi::getUri, uri)
                    .isNull(SysApi::getMethod)
                    .ne(BaseEntity::getId, id));
        }
        if (count > 0) {
            return ApiResult.error(1000, "接口已存在");
        }

        SysApi uriResource = new SysApi();
        uriResource.setId(id);
        uriResource.setName(name);
        uriResource.setUri(uri);
        uriResource.setRemark(remark);
        uriResource.setMethod(method);
        sysApiService.updateById(uriResource);
        sysRoleApiService.clearCacheRoleUri();
        return ApiResult.success();
    }

    @GetMapping("/list/all")
    public ApiResult<List<SysApi>> listAll() {
        return PageResult.success(sysApiService.list());
    }

    @GetMapping("/list/page")
    public ApiResult<List<SysApi>> listPage(
            @NotNull @Min(ParamCheckConstant.PAGE_MIN_INDEX) Long pageIndex,
            @NotNull @Min(ParamCheckConstant.PAGE_MIN_SIZE) @Max(ParamCheckConstant.PAGE_MAX_SIZE) Long pageSize,
            @Length(max = SysParamCheckConstant.SysApi.NAME_MAX_LENGTH) String likeName,
            @Length(max = SysParamCheckConstant.SysApi.URI_MAX_LENGTH) String likeUri,
            Integer method) {
        Page<SysApi> page = new Page<>(pageIndex, pageSize);
        LambdaQueryWrapper<SysApi> wrapper = Wrappers.<SysApi>lambdaQuery()
                .like(StringUtils.isNotBlank(likeName), SysApi::getName, likeName)
                .like(StringUtils.isNotBlank(likeUri), SysApi::getUri, likeUri)
                .eq(method != null, SysApi::getMethod, method)
                .orderByAsc(Arrays.asList(SysApi::getUri, SysApi::getName, BaseEntity::getCreateTime));
        sysApiService.page(page, wrapper);
        return PageResult.success(page);
    }

    @GetMapping("/id")
    public ApiResult<SysApi> getById(@NotNull Long id) {
        return ApiResult.success(sysApiService.getById(id));
    }

}
