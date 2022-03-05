package tech.xs.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.constant.ParamCheckConstant;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.domain.model.PageResult;
import tech.xs.system.constant.SysParamCheckConstant;
import tech.xs.system.domain.entity.SysDict;
import tech.xs.system.domain.entity.SysDictValue;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @author 沈家文
 * @date 2020/11/9 16:23
 */
@Validated
@RestController
@RequestMapping("/sys/dict/value")
public class SysDictValueController extends BaseSysController {

    @PutMapping("/add")
    public ApiResult add(
            @NotNull Long dictId,
            @NotBlank @Length(max = SysParamCheckConstant.SysDictValue.VALUE_MAX_LENGTH) String value,
            @NotBlank @Length(max = SysParamCheckConstant.SysDictValue.NAME_MAX_LENGTH) String name,
            @NotNull @Min(SysParamCheckConstant.SysDictValue.SORT_MIN) Integer sort,
            Long fatherId,
            @Length(max = SysParamCheckConstant.SysDictValue.REMARK_MAX_LENGTH) String remark) {
        long count = sysDictValueService.count(Wrappers.<SysDictValue>lambdaQuery().eq(SysDictValue::getDictId, dictId).eq(SysDictValue::getValue, value));
        if (count > 0) {
            return ApiResult.error(1002, "值已存在");
        }
        if (fatherId != null) {
            count = sysDictValueService.count(Wrappers.<SysDictValue>lambdaQuery().eq(SysDictValue::getDictId, dictId).eq(SysDictValue::getId, fatherId));
            if (count <= 0) {
                return ApiResult.error(1003, "父节点不存在");
            }
        }
        SysDictValue dictValue = new SysDictValue();
        dictValue.setDictId(dictId);
        dictValue.setValue(value);
        dictValue.setName(name);
        dictValue.setSort(sort);
        dictValue.setRemark(remark);
        dictValue.setFatherId(fatherId);
        sysDictValueService.add(dictValue);
        return ApiResult.success();
    }

    @PostMapping("/delete/id")
    public ApiResult deleteById(@NotNull Long id) {
        long count = sysDictValueService.count(Wrappers.<SysDictValue>lambdaQuery().eq(SysDictValue::getFatherId, id));
        if (count > 0) {
            return ApiResult.error(1000, "删除失败,字典值具有子节点", id);
        }
        sysDictValueService.deleteById(id);
        return ApiResult.success();
    }

    @PostMapping("/delete/list/id")
    public ApiResult deleteByIdList(@NotEmpty List<Long> idList) {
        for (Long id : idList) {
            long count = sysDictValueService.count(Wrappers.<SysDictValue>lambdaQuery().eq(SysDictValue::getFatherId, id));
            if (count > 0) {
                return ApiResult.error(1000, "删除失败,字典值具有子节点", id);
            }
        }
        sysDictValueService.deleteByIdList(idList);
        return ApiResult.success();
    }

    @PostMapping("/modify/id")
    public ApiResult modifyById(
            @NotNull Long id,
            @Length(min = SysParamCheckConstant.SysDictValue.VALUE_MIN_LENGTH, max = SysParamCheckConstant.SysDictValue.VALUE_MAX_LENGTH) String value,
            @Length(min = SysParamCheckConstant.SysDictValue.NAME_MIN_LENGTH, max = SysParamCheckConstant.SysDictValue.NAME_MAX_LENGTH) String name,
            @Min(SysParamCheckConstant.SysDictValue.SORT_MIN) Integer sort,
            Long fatherId,
            @Length(max = SysParamCheckConstant.SysDictValue.REMARK_MAX_LENGTH) String remark) {
        SysDictValue dbValue = sysDictValueService.getById(id);
        if (dbValue == null) {
            return ApiResult.error(1000, "字典值不存在");
        }
        if (StringUtils.isNotBlank(value)) {
            long count = sysDictValueService.count(Wrappers.<SysDictValue>lambdaQuery().eq(SysDictValue::getValue, value).eq(SysDictValue::getDictId, dbValue.getDictId()).ne(BaseEntity::getId, id));
            if (count > 0) {
                return ApiResult.error(1001, "字典值已存在");
            }
        }
        if (fatherId != null) {
            long count = sysDictValueService.count(Wrappers.<SysDictValue>lambdaQuery()
                    .eq(SysDictValue::getDictId, dbValue.getDictId())
                    .eq(SysDictValue::getId, fatherId));
            if (count <= 0) {
                return ApiResult.error(1004, "父节点不存在");
            }
        }
        SysDictValue dictValue = new SysDictValue();
        dictValue.setId(id);
        dictValue.setValue(value);
        dictValue.setName(name);
        dictValue.setRemark(remark);
        dictValue.setSort(sort);
        dictValue.setFatherId(fatherId);
        sysDictValueService.updateById(dictValue);
        return ApiResult.success();
    }

    @GetMapping("/list/page")
    public ApiResult listPage(
            @Length(max = SysParamCheckConstant.SysDictValue.VALUE_MAX_LENGTH) String likeValue,
            @Length(max = SysParamCheckConstant.SysDictValue.NAME_MAX_LENGTH) String likeName,
            Long dictId,
            @NotNull @Min(ParamCheckConstant.PAGE_MIN_INDEX) Long pageIndex,
            @NotNull @Min(ParamCheckConstant.PAGE_MIN_SIZE) @Max(ParamCheckConstant.PAGE_MAX_SIZE) Long pageSize) {
        Page<SysDictValue> page = new Page<>(pageIndex, pageSize);
        LambdaQueryWrapper<SysDictValue> wrapper = Wrappers.lambdaQuery();
        if (dictId != null) {
            wrapper.eq(SysDictValue::getDictId, dictId);
        }
        if (StringUtils.isNotBlank(likeName)) {
            wrapper.eq(SysDictValue::getName, likeName);
        }
        if (StringUtils.isNotBlank(likeValue)) {
            wrapper.eq(SysDictValue::getValue, likeValue);
        }
        wrapper.orderByAsc(SysDictValue::getSort);
        page = sysDictValueService.page(page, wrapper);
        return PageResult.success(page);
    }

    @GetMapping("/list/dictCode")
    public ApiResult listByDictCode(
            @NotBlank @Length(max = SysParamCheckConstant.SysDict.CODE_MAX_LENGTH) String dictCode
    ) {
        SysDict dict = sysDictService.getOne(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, dictCode));
        List<SysDictValue> resData = sysDictValueService.list(Wrappers.<SysDictValue>lambdaQuery().eq(SysDictValue::getDictId, dict.getId()).orderByAsc(SysDictValue::getSort));
        return PageResult.success(resData);
    }

    @GetMapping("/tree/dictId")
    public ApiResult treeByDictId(@NotNull Long dictId) {
        List<SysDictValue> resData = sysDictValueService.tree(dictId);
        return PageResult.success(resData);
    }

    @GetMapping("/list/dictId")
    public ApiResult listByDictId(@NotNull Long dictId) {
        List<SysDictValue> data = sysDictValueService.list(Wrappers.<SysDictValue>lambdaQuery().eq(SysDictValue::getDictId, dictId));
        return PageResult.success(data);
    }

}
