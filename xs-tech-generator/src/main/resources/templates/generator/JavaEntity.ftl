package ${package};

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
<#if extendsClassPackage??>
    <#if extendsClassName??>
import ${extendsClassPackage}.${extendsClassName};
    </#if>
</#if>

/**
 * table name: ${tableName}
<#if tableDescribe??>
 * table describe: ${tableDescribe}
</#if>
 *
<#if author??>
 * @author ${author}
</#if>
 * @date ${.now?string["yyyy/MM/dd HH:mm"]}
 */
@Getter
@Setter
@ToString
@TableName("${tableName}")
<#if extendsClassName??>
public class ${className} extends ${extendsClassName} {
<#else>
public class ${className} {
</#if>
<#list columns as column>

    /**
    <#if column.fieldRemark??>
     * ${column.fieldRemark}
    </#if>
     */
    @TableField("${column.columnName}")
    private ${column.javaType} ${column.fieldName};
</#list>

}