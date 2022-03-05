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
import tech.xs.system.domain.bo.dict.GetDictDetailsBo;
import tech.xs.system.domain.entity.SysDict;
import tech.xs.system.enmus.DictStructureTypeEnum;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * 字段Controller
 *
 * @author 沈家文
 * @date 2020/10/29 17:00
 */
@Validated
@RestController
@RequestMapping("/sys/dict")
public class SysDictController extends BaseSysController {

    @PutMapping("/add")
    public ApiResult<SysDict> add(
            @NotBlank @Length(max = SysParamCheckConstant.SysDict.CODE_MAX_LENGTH) String code,
            @NotBlank @Length(max = SysParamCheckConstant.SysDict.NAME_MAX_LENGTH) String name,
            @NotNull DictStructureTypeEnum dataType,
            @Length(max = SysParamCheckConstant.SysDict.REMARK_MAX_LENGTH) String remark) {
        long count = sysDictService.count(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code));
        if (count > 0) {
            return ApiResult.error(1000, "编号已存在");
        }
        count = sysDictService.count(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getName, name));
        if (count > 0) {
            return ApiResult.error(1001, "名称已存在");
        }
        SysDict dict = new SysDict();
        dict.setCode(code);
        dict.setName(name);
        dict.setRemark(remark);
        dict.setStructureType(dataType);
        sysDictService.add(dict);
        return ApiResult.success(dict);
    }

    @PostMapping("/delete/id")
    public ApiResult<Object> deleteById(@NotNull Long id) {
        sysDictService.deleteById(id);
        return ApiResult.success();
    }

    @PostMapping("/delete/list/id")
    public ApiResult<Object> deleteByIdList(@NotEmpty List<Long> idList) {
        sysDictService.deleteByIdList(idList);
        return ApiResult.success();
    }

    @PostMapping("/modify/id")
    public ApiResult<Object> modifyById(
            @NotNull Long id,
            @Length(min = SysParamCheckConstant.SysDict.CODE_MIN_LENGTH, max = SysParamCheckConstant.SysDict.CODE_MAX_LENGTH) String code,
            @Length(min = SysParamCheckConstant.SysDict.NAME_MIN_LENGTH, max = SysParamCheckConstant.SysDict.NAME_MAX_LENGTH) String name,
            DictStructureTypeEnum dataType,
            @Length(max = SysParamCheckConstant.SysDict.REMARK_MAX_LENGTH) String remark) {
        SysDict dict = sysDictService.getById(id);
        if (dict == null) {
            return ApiResult.error(1000, "字典不存在");
        }
        if (StringUtils.isNotBlank(code)) {
            long count = sysDictService.count(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, code).ne(BaseEntity::getId, id));
            if (count > 0) {
                return ApiResult.error(1001, "编号已存在");
            }
        }
        if (StringUtils.isNotBlank(name)) {
            long count = sysDictService.count(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getName, name).ne(BaseEntity::getId, id));
            if (count > 0) {
                return ApiResult.error(1002, "名称已存在");
            }
        }

        SysDict newDict = new SysDict();
        newDict.setId(id);
        newDict.setCode(code);
        newDict.setName(name);
        newDict.setStructureType(dataType);
        newDict.setRemark(remark);
        sysDictService.updateById(newDict);
        return ApiResult.success();
    }

    @GetMapping("/list/page")
    public ApiResult<List<SysDict>> listPage(
            @NotNull @Min(ParamCheckConstant.PAGE_MIN_INDEX) Long pageIndex,
            @NotNull @Min(ParamCheckConstant.PAGE_MIN_SIZE) @Max(ParamCheckConstant.PAGE_MAX_SIZE) Long pageSize,
            @Length(max = SysParamCheckConstant.SysDict.CODE_MAX_LENGTH) String likeCode,
            @Length(max = SysParamCheckConstant.SysDict.NAME_MAX_LENGTH) String likeName
    ) {
        Page<SysDict> page = new Page<>(pageIndex, pageSize);
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.<SysDict>lambdaQuery()
                .like(StringUtils.isNotBlank(likeName), SysDict::getName, likeName)
                .like(StringUtils.isNotBlank(likeCode), SysDict::getCode, likeCode)
                .orderByDesc(BaseEntity::getCreateTime);
        page = sysDictService.page(page, wrapper);
        return PageResult.success(page);
    }

    @GetMapping("/id")
    public ApiResult<SysDict> getById(@NotNull Long id) {
        return ApiResult.success(sysDictService.getById(id));
    }


    /**
     * 查询字典详情
     *
     * @return
     */
    @GetMapping("/details")
    public ApiResult<SysDict> getDetails(@Valid GetDictDetailsBo bo) {
        return ApiResult.success(sysDictService.getDetails(bo));
    }

}
