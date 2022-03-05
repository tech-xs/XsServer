package ${package};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.constant.PageParamCheckConstant;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.domain.model.PageResult;
import ${javaEntityPackage}.${entityClassName};
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
<#if author??>
 * @author ${author}
</#if>
 * @date ${.now?string["yyyy/MM/dd HH:mm"]}
 */
@Validated
@RestController
@RequestMapping("${requestPath}")
public class ${className} extends ${extendsClassName} {

    @PutMapping("/add")
    public ApiResult add(
<#list columns as column>
            ${column.javaType} ${column.fieldName}<#if column_has_next>,</#if>
</#list>
    ) {
        ${entityClassName} ${useEntityName} = new ${entityClassName}();
<#list columns as column>
        ${useEntityName}.set${column.getSetFieldName}(${column.fieldName});
</#list>
        ${useServiceName}.save(${useEntityName});
        return ApiResult.success();
    }

    @PostMapping("/delete/idList")
    public ApiResult deleteByIdList(@NotEmpty List<Long> idList) {
        ${useServiceName}.remove(Wrappers.<${entityClassName}>lambdaQuery().in(BaseEntity::getId, idList));
        return ApiResult.success();
    }

    @PostMapping("/modify/id")
    public ApiResult modifyById(
            @NotNull Long id,
<#list columns as column>
            ${column.javaType} ${column.fieldName}<#if column_has_next>,</#if>
</#list>
    ) {
        if (${useServiceName}.getById(id) == null) {
            return ApiResult.error(1000, "数据不存在");
        }
        ${entityClassName} ${useEntityName} = new ${entityClassName}();
        ${useEntityName}.setId(id);
<#list columns as column>
        ${useEntityName}.set${column.getSetFieldName}(${column.fieldName});
</#list>
        ${useServiceName}.updateById(${useEntityName});
        return ApiResult.success();
    }

    @GetMapping("/list/page")
    public ApiResult listPage(
            @NotNull @Min(PageParamCheckConstant.MIN_INDEX) Long pageIndex,
            @NotNull @Min(PageParamCheckConstant.MIN_SIZE) @Max(PageParamCheckConstant.MAX_SIZE) Long pageSize,
<#list columns as column>
            ${column.javaType} ${column.fieldName}<#if column_has_next>,</#if>
</#list>
            ) {
        Page<${entityClassName}> page = new Page<>(pageIndex, pageSize);
        LambdaQueryWrapper<${entityClassName}> wrapper = Wrappers.<${entityClassName}>lambdaQuery();
<#list columns as column>
    <#if column.javaType == "String">
        if (StringUtils.isNotBlank(${column.fieldName})) {
            wrapper = wrapper.eq(${entityClassName}::get${column.getSetFieldName}, ${column.fieldName});
        }
    <#else>
        if (${column.fieldName} != null) {
            wrapper = wrapper.eq(${entityClassName}::get${column.getSetFieldName}, ${column.fieldName});
        }
    </#if>
</#list>
        wrapper = wrapper.orderByAsc(BaseEntity::getCreateTime);
        page = ${useServiceName}.page(page, wrapper);
        return PageResult.success(page);
    }

}